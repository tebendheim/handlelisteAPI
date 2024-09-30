import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.0.10"
    id("org.springframework.boot") version "3.1.3"
    id("io.spring.dependency-management") version "1.1.2"
    kotlin("plugin.spring") version "1.9.10"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://jitpack.io")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("io.mockk:mockk:1.13.12") // Check for the latest version
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    implementation("io.ktor:ktor-client-core:2.3.0")
    implementation("io.ktor:ktor-client-cio:2.3.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.0")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.security:spring-security-oauth2-jose")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.security:spring-security-oauth2-client")
    implementation ("io.github.cdimascio:java-dotenv:5.2.2")
}

tasks.withType<JavaCompile> {
    sourceCompatibility = "19" //
    targetCompatibility = "19" // Use Java 19
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "19" // Use Java 19
}

application {
    mainClass.set("org.example.Application")
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes(
            "Main-Class" to "org.example.Application" // Replace with your main class (fully qualified class name)
        )
    }
    // Ensure all Kotlin compiled classes are included in the JAR
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}


tasks.test {
    useJUnitPlatform()
}
tasks.withType<Test> {
    jvmArgs("-XX:+EnableDynamicAgentLoading")
    jvmArgs("-Xshare:off")
}