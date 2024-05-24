plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinPluginSerialization)
    application
}

group = "com.github.juanmougan.talks"
version = "1.0.0"
application {
    mainClass.set("com.github.juanmougan.talks.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    // TODO do not hardcode version here
    implementation("io.ktor:ktor-server-content-negotiation:2.3.11")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.11")
    implementation(platform("io.insert-koin:koin-bom:3.5.6"))
    implementation("io.insert-koin:koin-core")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.9.3") // for JVM platform
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)
}