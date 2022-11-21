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
    id("com.github.johnrengelman.shadow").version("7.1.2")
}

group = "com.mc8s"
version = "1.0-SNAPSHOT"

tasks.shadowJar {
    mergeServiceFiles()
    mustRunAfter("build")
}

repositories {
    mavenCentral()
    maven {
        setUrl("https://oss.sonatype.org/content/repositories/snapshots")
    }
}

dependencies {
    implementation(project(":mc8s-classfilter"))
    implementation(project(":mc8s-kubernetes-bridge"))
    implementation("com.google.inject:guice:5.1.0")
    implementation("com.google.guava:guava:31.1-jre")
    implementation("io.fabric8:kubernetes-httpclient-okhttp:6.2.0")
    implementation("io.fabric8:crd-generator-apt:6.2.0")
    implementation("net.md-5:bungeecord-api:1.19-R0.1-SNAPSHOT")
}