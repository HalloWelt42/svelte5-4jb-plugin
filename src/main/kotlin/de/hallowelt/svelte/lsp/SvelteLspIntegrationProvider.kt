package de.hallowelt.svelte.lsp

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.LspIntegrationProvider

internal const val SVELTE_EXTENSION = "svelte"

/**
 * Startet den Svelte-Sprachserver, sobald eine .svelte-Datei geoeffnet wird.
 */
internal class SvelteLspIntegrationProvider : LspIntegrationProvider {
    override fun fileOpened(
        project: Project,
        file: VirtualFile,
        clientStarter: LspIntegrationProvider.LspClientStarter,
    ) {
        if (file.extension == SVELTE_EXTENSION) {
            clientStarter.ensureClientStarted(SvelteLspClientDescriptor(project))
        }
    }
}
