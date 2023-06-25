plugins {
    id ("org.springframework.boot")
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
    testImplementation("org.springframework.boot:spring-boot-starter-test")

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
}