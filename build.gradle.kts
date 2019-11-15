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
dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile(kotlin("reflect"))

    implementation("org.koin", "koin-core", koinVersion)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}