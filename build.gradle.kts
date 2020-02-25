import org.gradle.internal.impldep.bsh.commands.dir
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.11"
}

group = "com.conceptic"
version = "1.0.0"

repositories {
    mavenCentral()
    jcenter()
}

val koinVersion = "2.0.1"
val log4jVersion = "2.12.1"
dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile(kotlin("reflect"))

    implementation(fileTree("libs"))

    compile("org.apache.logging.log4j", "log4j-api", log4jVersion)
    compile("org.apache.logging.log4j", "log4j-core", log4jVersion)

    implementation("org.koin", "koin-core", koinVersion)
    implementation("com.google.code.gson", "gson", "2.8.6")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}