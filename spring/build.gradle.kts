plugins {
    kotlin("plugin.spring") version Versions.kotlinVersion
    id("org.springframework.boot") version Versions.springBootVersion
    id("io.spring.dependency-management") version Versions.springDependencyManagementVersion
}

dependencies {
    implementation(project(":common"))
    api("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter")
}
