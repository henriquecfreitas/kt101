import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.2.0.RELEASE"
	id("io.spring.dependency-management") version "1.0.8.RELEASE"
	kotlin("jvm") version "1.3.50"
	kotlin("plugin.spring") version "1.3.50"
}

group = "com.odhen"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	//Spring Boot
	implementation("org.springframework.boot:spring-boot-starter:2.2.0.RELEASE")
	implementation("org.springframework.boot:spring-boot-starter-web:2.2.0.RELEASE")
	implementation("org.springframework.boot:spring-boot-starter-security:2.2.0.RELEASE")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.2.0.RELEASE")
	//JWT
	implementation("io.jsonwebtoken:jjwt:0.7.0")
	//MySQL
	implementation("mysql:mysql-connector-java:8.0.18")
	//SQL Server
	implementation(files("src/main/libs/sqljdbc4-4.0.jar"))
	//SQLite
	implementation("org.xerial", "sqlite-jdbc", "3.25.2")
	//GraphQL
	implementation("com.graphql-java:graphql-spring-boot-starter:5.0.2")
	implementation("com.graphql-java:graphql-java-tools:5.2.4")
	implementation("com.graphql-java:graphiql-spring-boot-starter:5.0.2")
	//Testes
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}
