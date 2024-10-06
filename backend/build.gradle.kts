plugins {
    id("org.springframework.boot") version "3.3.1"
    kotlin("jvm") version "2.0.0"  // 최신 Kotlin 버전으로 업데이트
    `java-library`
    `maven-publish`
    kotlin("plugin.spring") version "2.0.0"
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("javax.persistence:javax.persistence-api:2.2")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.3.1")
    implementation("org.springframework.boot:spring-boot-starter-jdbc:3.3.1")
    implementation("org.springframework.boot:spring-boot-starter-security:3.3.1")
    implementation("org.springframework.boot:spring-boot-starter-web:3.3.1")
    implementation("org.springframework.boot:spring-boot-starter-web-services:3.3.1")
    implementation("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    implementation("org.modelmapper:modelmapper:3.1.0")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.0")
    implementation("com.nimbusds:nimbus-jose-jwt:9.37.2")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.3.1")
    implementation("org.apache.commons:commons-math3:3.6.1")
    runtimeOnly("org.springframework.boot:spring-boot-devtools:3.3.1")
    runtimeOnly("com.h2database:h2:2.1.214")

    testImplementation("org.springframework.boot:spring-boot-starter-test:3.3.1")
    testImplementation("org.springframework.security:spring-security-test:6.1.0")
}

group = "edu.pnu"
version = "0.0.1"
description = "project"
java.sourceCompatibility = JavaVersion.VERSION_21


publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(listOf("--release", "21"))
}


tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}
// Spring Boot 플러그인 설정
springBoot {
    mainClass.set("edu.pnu.ProjectApplication")
}