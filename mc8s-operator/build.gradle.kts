buildscript {
    repositories {
        gradlePluginPortal()
    }

    dependencies {
        classpath("gradle.plugin.com.github.johnrengelman:shadow:7.1.2")
    }
}

plugins {
    id("java")
    id("application")
    id("com.github.johnrengelman.shadow").version("7.1.2")
}

group = "com.mc8s"
version = "1.0-SNAPSHOT"

tasks.build {
    dependsOn(tasks.shadowJar)
}

application {
    mainClass.set("com.mc8s.operator.crd.MinecraftServerOperator")
}

tasks.shadowJar {
    mergeServiceFiles()
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javaoperatorsdk:operator-framework:4.1.1")
    implementation("io.fabric8:crd-generator-apt:6.2.0")
    testImplementation("org.apache.logging.log4j:log4j-slf4j-impl:2.20.0")
    annotationProcessor("io.fabric8:crd-generator-apt:6.2.0")
}
