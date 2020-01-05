package com.autonomousapps.trumpinsulter

import com.android.build.gradle.AppExtension
import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.kotlin.dsl.the

@Suppress("unused")
class TrumpInsulterPlugin: Plugin<Project> {
    override fun apply(project: Project): Unit = project.run {
        pluginManager.withPlugin("com.android.application") {
            the<AppExtension>().applicationVariants.all {
                val insultTask = tasks.register("insult${name.capitalize()}") {
                    doLast {
                        println("Donald Trump is such a huge tool")
                    }
                }

                packageApplicationProvider.configure {
                    finalizedBy(insultTask)
                }
            }
        }
    }
}
