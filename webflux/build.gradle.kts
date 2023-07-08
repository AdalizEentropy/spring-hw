plugins {
    id ("org.springframework.boot")
}

val lombokVersion: String by project
val mapstructVersion: String by project
val lombokMapstructBindingVersion: String by project
val mysqlConnectorVersion: String by project
val mysqlR2dbcVersion: String by project

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    annotationProcessor ("org.mapstruct:mapstruct-processor:$mapstructVersion")

    implementation("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor ("org.projectlombok:lombok-mapstruct-binding:${lombokMapstructBindingVersion}")

    runtimeOnly("mysql:mysql-connector-java:${mysqlConnectorVersion}")
    runtimeOnly("dev.miku:r2dbc-mysql:${mysqlR2dbcVersion}")

    testImplementation ("org.testcontainers:testcontainers")
    testImplementation ("org.testcontainers:mysql")
    testRuntimeOnly("dev.miku:r2dbc-mysql:${mysqlR2dbcVersion}")

    implementation(project(mapOf("path" to ":model")))
}
