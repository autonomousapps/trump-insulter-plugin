package com.autonomousapps.trumpinsulter

import org.gradle.api.Project
import org.gradle.api.Plugin

class TrumpInsulterPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("insult") {
            doLast {
                println("Donald Trump is such a huge tool")
            }
        }
    }
}
