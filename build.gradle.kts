@file:Suppress("SpellCheckingInspection")

import org.ajoberstar.grgit.Commit
import org.ajoberstar.grgit.Grgit
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    val kotlinVersion = "1.4.10"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("kapt") version kotlinVersion

    // ----------------------- spring ------------------------
    id("org.springframework.boot") version "2.3.4.RELEASE"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"

    // ----------------------- jacoco ------------------------
    jacoco

    // ----------------------- ktlint ------------------------
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"

    // ----------------------- jib ------------------------
    id("com.google.cloud.tools.jib") version "2.6.0"
    id("org.ajoberstar.grgit") version "4.1.0"
}

val local = Properties().apply {
    rootProject.file("local.properties")
        .takeIf { it.exists() }
        ?.inputStream()
        ?.use { this.load(it) }
}

group = "com.artemkaxboy"
version = System.getenv("RELEASE_VERSION") ?: project.properties["applicationVersion"] ?: "local"

sourceSets {
    test {
        java {
            srcDirs("src/test/kotlin", "src/test/kotlinIntegration")
        }
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {

    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // ----------------------- database ------------------------
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.postgresql:postgresql")
    // implementation("org.flywaydb:flyway-core")

    // ----------------------- tests ------------------------
    testRuntimeOnly("com.h2database:h2")
    testImplementation("io.mockk:mockk:1.10.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }

    // ----------------------- spring webflux ------------------------
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    // ----------------------- swagger ------------------------
    // https://github.com/springdoc/springdoc-openapi
    implementation("org.springdoc:springdoc-openapi-webflux-ui:1.4.3")

    // ----------------------- logging ------------------------
    // https://www.baeldung.com/javax-validation
    implementation("org.hibernate.validator:hibernate-validator")
    // implementation("org.glassfish:javax.el:3.0.0")

    // ----------------------- artemkaxboy corelib ------------------------
    implementation("com.github.artemkaxboy:CoreLib:v0.1.4")

    // ----------------------- logging ------------------------
    implementation("io.github.microutils:kotlin-logging:1.7.10")

    // ----------------------- model mapping ------------------------
    // entity dto mapper    https://habr.com/ru/post/438808/
    // implementation("org.modelmapper:modelmapper:2.3.8")
    // locally fixed https://github.com/modelmapper/modelmapper/issues/553
    implementation(files("libs/modelmapper-2.3.9-SNAPSHOT.jar"))

    // ----------------------- annotation proccessing ------------------------
    kapt("org.springframework.boot:spring-boot-configuration-processor")
}

tasks {

    withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    withType<KotlinCompile> { // works for both compileKotlin and compileTestKotlin
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    withType<com.google.cloud.tools.jib.gradle.BuildImageTask> {
        project.extra["minorVersion"] = "$version".replace("^(\\d+\\.\\d+).*$".toRegex(), "$1")
        project.extra["majorVersion"] = "$version".replace("^(\\d+).*$".toRegex(), "$1")

        // https://stackoverflow.com/questions/55749856/gradle-dsl-method-not-found-versioncode
        val commit: Commit = Grgit.open { currentDir = projectDir }.head()

        project.extra["commitTime"] = "${commit.dateTime}"
        project.extra["commitHash"] = commit.id.take(8) // short commit id contains 8 chars
    }

    // https://medium.com/@arunvelsriram/jacoco-configuration-using-gradles-kotlin-dsl-67a8870b1c68
    jacocoTestReport {
        dependsOn("test")

        reports {
            xml.isEnabled = true
            csv.isEnabled = false
            html.isEnabled = true
            html.destination = file("$buildDir/reports/coverage")
        }
    }
}

jib {
    val author: String by project
    val sourceUrl: String by project

    val minorVersion: String by project
    val majorVersion: String by project

    val commitTime: String by project
    val commitHash: String by project

    from {
        // https://console.cloud.google.com/gcr/images/distroless/GLOBAL/java?gcrImageListsize=30
        // image = "gcr.io/distroless/java:11-nonroot"
        image = "gcr.io/distroless/java@sha256:40671acefa51d12e33f547fc4950b6de430c905e61ca821d9c16ab5133ede762"
    }

    to {
        image = "ghcr.io/artemkaxboy/spring-data-showroom"

        tags = setOf("$version", minorVersion, majorVersion)

        auth {
            username = System.getenv("GITHUB_ACTOR") ?: local.getProperty("GITHUB_ACTOR")
            password = System.getenv("CONTAINER_REGISTRY_TOKEN") ?: local.getProperty("CONTAINER_REGISTRY_TOKEN")
        }
    }

    container {
        user = "999:999"
        creationTime = commitTime
        ports = listOf("8080")

        environment = mapOf(
            "IMAGE_VERSION" to "$version"
        )

        labels = mapOf(
            "maintainer" to author,
            "org.opencontainers.image.created" to commitTime,
            "org.opencontainers.image.authors" to author,
            "org.opencontainers.image.url" to sourceUrl,
            "org.opencontainers.image.documentation" to sourceUrl,
            "org.opencontainers.image.source" to sourceUrl,
            "org.opencontainers.image.version" to "$version",
            "org.opencontainers.image.revision" to commitHash,
            "org.opencontainers.image.vendor" to author,
            "org.opencontainers.image.title" to name
        )
    }
}
