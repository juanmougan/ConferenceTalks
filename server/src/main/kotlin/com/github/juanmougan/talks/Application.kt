package com.github.juanmougan.talks

import Greeting
import SERVER_PORT
import com.github.juanmougan.talks.services.TalkService
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.serialization.json.Json
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject

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
    }
}
