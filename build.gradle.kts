import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("jvm") version Versions.kotlinVersion
    kotlin("kapt") version Versions.kotlinVersion
    kotlin("plugin.spring") version Versions.kotlinVersion
    kotlin("plugin.noarg") version Versions.kotlinVersion apply false
    kotlin("plugin.allopen") version Versions.kotlinVersion apply false

    id("org.springframework.boot") version Versions.springBootVersion
    id("io.spring.dependency-management") version Versions.springDependencyManagementVersion
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    group = "io.kata"
    version = "0.0.1-SNAPSHOT"

    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "kotlin-spring")


    java.sourceCompatibility = JavaVersion.VERSION_11
    java.targetCompatibility = JavaVersion.VERSION_11

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    dependencies {
        implementation(kotlin("stdlib"))
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
        testImplementation("io.strikt:strikt-core:0.34.1")
    }
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = true

description = "Gradle Tutorial Basic"
version = "1.0"

group = "org.example"


tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.register<Zip>("zip") {
    from("src")
}

tasks.register("hello") {
    doLast {
        println("Hello World!")
    }
}
