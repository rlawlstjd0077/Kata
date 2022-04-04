plugins {
    kotlin("jvm") version "1.5.10"
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "kotlin")
}

description = "Gradle Tutorial Basic"
version = "1.0"

group = "org.example"


dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

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
