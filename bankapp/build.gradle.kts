plugins {
    id ("org.springframework.boot")
}

val lombokVersion: String by project
val mapstructVersion: String by project
val lombokMapstructBindingVersion: String by project
val liquibaseVersion: String by project

dependencies {
    implementation ("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    annotationProcessor ("org.mapstruct:mapstruct-processor:$mapstructVersion")

    implementation("org.liquibase:liquibase-core:$liquibaseVersion")

    implementation("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor ("org.projectlombok:lombok-mapstruct-binding:${lombokMapstructBindingVersion}")

    implementation("org.aspectj:aspectjweaver")
    runtimeOnly("com.h2database:h2")

    implementation(files("lib/starters-plain.jar"))
    implementation(project(mapOf("path" to ":starters")))
}