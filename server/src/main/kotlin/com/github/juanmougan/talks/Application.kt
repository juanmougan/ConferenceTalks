package com.github.juanmougan.talks

import Greeting
import SERVER_PORT
import com.github.juanmougan.talks.services.TalkService
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.content.streamProvider
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receiveMultipart
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject
import java.io.File

fun main() {
    val prodModule = module {
        single<TalkService> { TalkService() }
    }
    startKoin {
        modules(prodModule)
    }
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
    val talkService: TalkService by inject(TalkService::class.java)
    routing {
        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }
        get("/talks") {
            call.respond(talkService.getAll())
        }
        get("/talks/{id}") {
            val id = call.parameters["id"]?.toLong() ?: 0L
            call.respond(
                talkService.getById(id) ?: throw RuntimeException("Talk not found with ID: $id")
            )
        }
        post("/talks/bulk") {
            val multipartData = call.receiveMultipart()

//            var fileName = ""
            var tmpFile: File? = null
            multipartData.forEachPart { part ->
                when (part) {
                    is PartData.FileItem -> {
//                        fileName = part.originalFileName as String
                        val fileBytes = part.streamProvider().readBytes()
                        withContext(Dispatchers.IO) {
//                            File("uploads/$fileName").writeBytes(fileBytes)
                            tmpFile = File.createTempFile(
                                "talks",
                                ".csv",
                                null
                            )
                                .also { it.writeBytes(fileBytes) } //talks=prefix, .csv=suffix, null=directory
                        }
                    }

                    else -> {}
                }
                part.dispose()
            }

            call.respond(talkService.bulkCreate(tmpFile!!))
        }
    }
}
