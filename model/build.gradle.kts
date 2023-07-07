import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id ("org.springframework.boot")
}

val lombokVersion: String by project
val mapstructVersion: String by project
val lombokMapstructBindingVersion: String by project

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")

    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    annotationProcessor ("org.mapstruct:mapstruct-processor:$mapstructVersion")

    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    implementation("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor ("org.projectlombok:lombok-mapstruct-binding:${lombokMapstructBindingVersion}")
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}