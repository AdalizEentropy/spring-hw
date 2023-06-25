import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES
import org.gradle.plugins.ide.idea.model.IdeaLanguageLevel

plugins {
    idea
    id ("io.spring.dependency-management")
    id("org.springframework.boot") apply false
    id("com.diffplug.spotless")  apply false
    id("name.remal.sonarlint") apply false
}

idea {
    project {
        languageLevel = IdeaLanguageLevel(17)
    }
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}


allprojects {
    group = "ru.nspk"

    repositories {
        maven {
            name = "NSPK Nexus 3 Public"
            url = uri("https://nexus.mir/repository/maven-proxy-group")
        }
        mavenLocal()
    }

    val testcontainersBomVersion: String by project

    apply(plugin = "io.spring.dependency-management")
    dependencyManagement {
        dependencies {
            imports {
                mavenBom(BOM_COORDINATES)
                mavenBom("org.testcontainers:testcontainers-bom:$testcontainersBomVersion")
            }
        }
    }
    configurations.all {
        resolutionStrategy {
            failOnVersionConflict()

            force("org.sonarsource.analyzer-commons:sonar-analyzer-commons:2.4.0.1317")
            force("com.google.code.findbugs:jsr305:3.0.2")
            force("org.sonarsource.sslr:sslr-core:1.24.0.633")
            force("org.eclipse.platform:org.eclipse.osgi:3.18.300")
        }
    }
}

subprojects {
    plugins.apply(JavaPlugin::class.java)
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    apply(plugin = "name.remal.sonarlint")

    apply<com.diffplug.gradle.spotless.SpotlessPlugin>()
    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        java {
            googleJavaFormat("1.16.0").aosp()
        }
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.addAll(listOf("-Xlint:all,-serial,-processing", "-Werror"))
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging.showExceptions = true
        reports {
            junitXml.required.set(true)
            html.required.set(true)
        }
    }
}

tasks {
    val managedVersions by registering {
        doLast {
            project.extensions.getByType<DependencyManagementExtension>()
                .managedVersions
                .toSortedMap()
                .map { "${it.key}:${it.value}" }
                .forEach(::println)
        }
    }
}