
val kotlin_version: String by project
val logback_version: String by project
val ktor_version: String by project

plugins {
    kotlin("jvm") version "2.0.21"
    id("io.ktor.plugin") version "3.0.0"
    kotlin("plugin.serialization") version "1.8.10"
}

group = "com.example"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-default-headers-jvm")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml")
    testImplementation("io.ktor:ktor-server-test-host-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    implementation("io.ktor:ktor-server-freemarker:$ktor_version")
    implementation("org.freemarker:freemarker:2.3.31")
    implementation("io.ktor:ktor-server-freemarker:2.0.0")
    implementation("mysql:mysql-connector-java:8.0.33")  // MySQL driver
    implementation("org.jetbrains.exposed:exposed-core:0.41.1") // Exposed Core
    implementation("org.jetbrains.exposed:exposed-dao:0.41.1")  // Exposed DAO
    implementation("org.jetbrains.exposed:exposed-jdbc:0.41.1") // Exposed JDBC
    implementation("com.zaxxer:HikariCP:5.0.1") // HikariCP for connection pooling
    testImplementation("io.ktor:ktor-server-tests-jvm:2.3.4") // Ktor testing engine
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.9.10") // Kotlin test with JUnit
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0") // JUnit5 API
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0") // JUnit5 engine

}

