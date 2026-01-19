plugins {
    id("java")
}

group = "fr.gardahaut"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(files("./libs/HytaleServer.jar"))
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<Copy>("copyModJar") {
    dependsOn(tasks.named("jar"))           // faster than "build" for dev
    from(layout.buildDirectory.file("libs/Test1-1.0-SNAPSHOT.jar"))
    into(layout.projectDirectory.dir("server/mods"))
}