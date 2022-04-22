plugins {
    kotlin("jvm") version Versions.kotlinVersion
    kotlin("plugin.spring") version Versions.kotlinVersion
    kotlin("plugin.noarg") version Versions.kotlinVersion apply false
    kotlin("plugin.allopen") version Versions.kotlinVersion apply false
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-spring")

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
