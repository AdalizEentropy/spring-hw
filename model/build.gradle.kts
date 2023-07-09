import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id ("org.springframework.boot")
}

val lombokVersion: String by project

dependencies {
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    implementation("org.projectlombok:lombok:$lombokVersion")
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}