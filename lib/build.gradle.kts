/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Groovy library project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.5/userguide/building_java_projects.html in the Gradle documentation.
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
    // Apply the groovy Plugin to add support for Groovy.
    groovy

    // Apply the java-library plugin for API and implementation separation.
    `java-library`
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use the latest Groovy version for building this library
    implementation(libs.groovy.all)
    

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation(libs.guava)

    // flux should be api
    api("io.projectreactor:reactor-core:3.6.1")

    // This dependency is exported to consumers, that is to say found on their compile classpath.
    api(libs.commons.math3)
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use Spock test framework
            useSpock("2.2-groovy-3.0")
        }
    }
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
