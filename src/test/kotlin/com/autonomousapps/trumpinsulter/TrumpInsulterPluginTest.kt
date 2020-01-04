package com.autonomousapps.trumpinsulter

import org.gradle.testfixtures.ProjectBuilder
import kotlin.test.Test
import kotlin.test.assertNotNull

class TrumpInsulterPluginTest {
    @Test fun `plugin registers task`() {
        // Create a test project and apply the plugin
        val project = ProjectBuilder.builder().build()
        project.plugins.apply("com.autonomousapps.trumpinsulter")

        // Verify the result
        assertNotNull(project.tasks.findByName("insult"))
    }
}
