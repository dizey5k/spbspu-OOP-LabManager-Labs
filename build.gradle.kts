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

sourceSets {
    main {
        java {
            srcDirs(
                "src/main/java",
                "Labs/Autumn/Lab1/src/main/java",
                "Labs/Autumn/Lab2/src/main/java",
                "Labs/Autumn/Lab3/src/main/java",
                "Labs/Autumn/Lab4/src/main/java",
                "Labs/Autumn/Lab5/src/main/java",
                "Labs/Spring/Lab1/src/main/java",
                "Labs/Spring/Lab2/src/main/java"
            )
        }
        resources {
            srcDirs("src/main/resources")
        }
    }
}

tasks.named<ProcessResources>("processResources") {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks.register("collectServiceFiles") {
    doLast {
        val servicesDir = file("src/main/resources/META-INF/services")
        servicesDir.mkdirs()

        val serviceFile = file("${servicesDir.absolutePath}/LabManager.RunnableLab")
        serviceFile.delete()

        val implementations = mutableSetOf<String>()

        implementations.add("Autumn.Lab1.Lab1")
        implementations.add("Autumn.Lab2.Lab2")
        implementations.add("Autumn.Lab3.Lab3")
        implementations.add("Autumn.Lab4.Lab4")
        implementations.add("Autumn.Lab5.Lab5")
        implementations.add("Spring.Lab1.Lab1")
        implementations.add("Spring.Lab2.Lab2")

        println("Manually added implementations: $implementations")

        serviceFile.writeText(implementations.joinToString("\n"))
        println("Generated service file with ${implementations.size} implementations")
    }
}

tasks.named("compileJava") {
    dependsOn("collectServiceFiles")
}

tasks.register("showStructure") {
    doLast {
        println("=== Project Structure ===")
        println("Java source directories:")
        sourceSets.main.get().java.srcDirs.forEach { dir ->
            if (dir.exists()) {
                println("  - $dir (exists: ${dir.exists()})")
                if (dir.exists()) {
                    fileTree(dir).matching {
                        include("**/*.java")
                    }.forEach { file ->
                        println("    - ${file.relativeTo(dir)}")
                    }
                }
            }
        }

        println("\nService files:")
        fileTree("src/main/resources").matching {
            include("META-INF/services/**")
        }.forEach { file ->
            println("  - ${file.relativeTo(file("."))} (exists: ${file.exists()})")
            if (file.isFile && file.exists()) {
                println("    Content:")
                file.readLines().forEach { line ->
                    println("      $line")
                }
            }
        }
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}