plugins {
    `java-library`
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.19"
    id("xyz.jpenilla.run-paper") version "3.0.2" // Adds runServer and runMojangMappedServer tasks for testing
    id("com.gradleup.shadow") version "9.2.0"
}

group = "me.alexxxychep"
version = "1.0"

java {
    // Configure the java toolchain. This allows gradle to auto-provision JDK 21 on systems that only have JDK 11 installed for example.
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

// For 1.20.4 or below, or when you care about supporting Spigot on >=1.20.5:
/*
paperweight.reobfArtifactConfiguration = io.papermc.paperweight.userdev.ReobfArtifactConfiguration.REOBF_PRODUCTION

tasks.assemble {
  dependsOn(tasks.reobfJar)
}
 */

repositories {
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle("1.21.10-R0.1-SNAPSHOT")
    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("com.google.inject:guice:7.0.0")
    implementation("com.google.inject.extensions:guice-assistedinject:7.0.0")
    implementation("javax.inject:javax.inject:1")
}


tasks {
    compileJava {
        options.release = 21
    }
    shadowJar {

        relocate("com.zaxxer.hikari", "me.alexxxychep.libs.hikari")
        relocate("com.mysql", "me.alexxxychep.libs.mysql")
        relocate("com.google.inject", "me.alexxxychep.libs.inject")
        relocate("com.google.common", "me.alexxxychep.libs.guava")
        relocate("javax.inject", "me.alexxxychep.libs.javax.inject")

        exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")

        mergeServiceFiles()

        archiveClassifier.set("")
    }

    assemble {
        dependsOn(reobfJar)
    }

    reobfJar {
        inputJar.set(shadowJar.flatMap { it.archiveFile })
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
}

tasks.test {
    useJUnitPlatform()
}

