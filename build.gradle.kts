plugins {
    `java-gradle-plugin`
    id("org.jetbrains.kotlin.jvm") version "1.3.61"
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "0.10.1"
}

repositories {
    jcenter()
    google()
}

version = "0.1.0"
group = "com.autonomousapps"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

// Add a source set for the functional test suite. This must come _above_ the `dependencies` block.
val functionalTestSourceSet = sourceSets.create("functionalTest") {
    compileClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
    runtimeClasspath += output + compileClasspath
}

configurations.getByName("functionalTestImplementation")
    .extendsFrom(configurations.getByName("testImplementation"))

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    compileOnly("com.android.tools.build:gradle:3.5.3") {
        because("Auto-wiring into Android projects")
    }

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

gradlePlugin {
    val trumpInsultingPlugin by plugins.creating {
        id = "com.autonomousapps.trumpinsulter"
        implementationClass = "com.autonomousapps.trumpinsulter.TrumpInsulterPlugin"
    }
}

// For publishing to the Gradle Plugin Portal
// https://plugins.gradle.org/docs/publish-plugin
pluginBundle {
    website = "https://github.com/autonomousapps/trump-insulter-plugin"
    vcsUrl = "https://github.com/autonomousapps/trump-insulter-plugin"

    description = "A plugin to insult Donald Trump, apparently the president of the United States"

    (plugins) {
        "trumpInsultingPlugin" {
            displayName = "Trump Insulter"
            tags = listOf("insults", "sanity")
        }
    }
}

gradlePlugin.testSourceSets(functionalTestSourceSet)
tasks.withType<PluginUnderTestMetadata>().configureEach {
    pluginClasspath.from(configurations.compileOnly)
}

val test by tasks.getting(Test::class)

val functionalTest by tasks.creating(Test::class) {
    description = "Runs the functional tests."
    group = "verification"

    testClassesDirs = functionalTestSourceSet.output.classesDirs
    classpath = functionalTestSourceSet.runtimeClasspath

    mustRunAfter(test)
}

val check by tasks.getting(Task::class) {
    dependsOn(functionalTest)
}
