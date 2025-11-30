plugins {
    java
    application
}

application {
    mainClass.set("LabManager.LabManager")
}

repositories {
    mavenCentral()
}

//TODO: lab4, lab5
sourceSets {
    main {
        java {
            srcDirs("src/main/java", "Labs/Lab1/src/main/java", "Labs/Lab2/src/main/java", "Labs/Lab3/src/main/java", "Labs/Lab4/src/main/java")
        }
        resources {
            srcDirs("src/main/resources")
        }
    }
}

tasks.named<ProcessResources>("processResources") {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks.register("showStructure") {
    doLast {
        println("=== Project Structure ===")
        println("Java sources:")
        sourceSets.main.get().java.srcDirs.forEach { dir ->
            println("  - $dir")
        }
        println("Resource directories:")
        sourceSets.main.get().resources.srcDirs.forEach { dir ->
            println("  - $dir")
        }
        println("Files in project:")
        fileTree(".").matching {
            include("**/*.java", "**/META-INF/**", "**/build.gradle*")
        }.forEach { file ->
            println("  - ${file.relativeTo(file("."))}")
        }
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

// .\gradlew run --console=plain
tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}