import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.github.node-gradle.node") version "2.2.3"
    kotlin("jvm") version "1.5.21"
    kotlin("plugin.spring") version "1.5.21"
}

group = "kr.clean"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.0")
    implementation("org.jsoup:jsoup:1.12.1")
    implementation("org.apache.commons:commons-lang3:3.8.1")
    implementation("org.apache.httpcomponents:httpclient:4.5.13")
    implementation("javax.xml.bind:jaxb-api")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("mysql:mysql-connector-java")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("commons-io:commons-io:2.6")
    implementation("org.json:json:20190722")
}

sourceSets {
    main {
        resources {
            srcDir("$projectDir/src/main/kotlin")
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

node {
    download = true
    version = "12.13.1"
    npmVersion = "6.9.0"
    yarnVersion = "1.17.3"
    nodeModulesDir = project.file("$projectDir/src/frontend")
    workDir = project.file("${project.buildDir}/nodejs")
    npmWorkDir = project.file("${project.buildDir}/npm")
    yarnWorkDir = project.file("${project.buildDir}/yarn")
}

task<com.moowork.gradle.node.npm.NpmTask>("installNpm") {
    setArgs(listOf("install"))
}

task<com.moowork.gradle.node.npm.NpmTask>("buildVue") {
    setArgs(listOf("run", "build"))
}

tasks.getByName("buildVue").dependsOn("installNpm")
tasks.getByName("processResources").dependsOn("buildVue")