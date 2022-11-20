plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javaoperatorsdk:operator-framework:4.1.1")
    implementation("io.fabric8:crd-generator-apt:6.2.0")
    annotationProcessor("io.fabric8:crd-generator-apt:6.2.0")
}
