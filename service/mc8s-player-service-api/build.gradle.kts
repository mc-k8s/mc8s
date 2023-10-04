import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm").version("1.9.10")
}

group = "com.mc8s"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.openfeign:feign-core:12.1")
    implementation("io.github.openfeign:feign-jackson:12.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}