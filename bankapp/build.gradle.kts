plugins {
    id ("org.springframework.boot")
    id("com.google.cloud.tools.jib")
}

jib {
    container.creationTime.set("USE_CURRENT_TIMESTAMP")
    from.image = "bellsoft/liberica-openjdk-alpine-musl:17.0.2-9"
    to {
        image = "bankapp-image"
        tags = setOf(project.version.toString())
    }
}

val lombokVersion: String by project
val mapstructVersion: String by project
val lombokMapstructBindingVersion: String by project
val liquibaseVersion: String by project
val mysqlConnectorVersion: String by project

dependencies {
    implementation ("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")

    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    annotationProcessor ("org.mapstruct:mapstruct-processor:$mapstructVersion")

    implementation("org.liquibase:liquibase-core:$liquibaseVersion")
    implementation("org.aspectj:aspectjweaver")
    runtimeOnly("mysql:mysql-connector-java:${mysqlConnectorVersion}")

    implementation("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor ("org.projectlombok:lombok-mapstruct-binding:${lombokMapstructBindingVersion}")

    testImplementation ("org.testcontainers:testcontainers")
    testImplementation ("org.testcontainers:mysql")

    implementation(files("lib/starters-plain.jar"))
    implementation(project(mapOf("path" to ":starters")))
    implementation(project(mapOf("path" to ":model")))
}