package de.hallowelt.svelte.lsp

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.lsp.api.ProjectWideLspClientDescriptor

/**
 * Beschreibt, wie der Svelte-Sprachserver fuer ein Projekt gestartet wird:
 * `node .../svelte-language-server/bin/server.js --stdio`.
 */
internal class SvelteLspClientDescriptor(project: Project) :
    ProjectWideLspClientDescriptor(project, "Svelte") {

    override fun isSupportedFile(file: VirtualFile): Boolean =
        file.extension == SVELTE_EXTENSION

    override fun createCommandLine(): GeneralCommandLine {
        val node = SvelteLspPaths.resolveNodeExecutable()
            ?: throw IllegalStateException(
                "Node.js wurde nicht gefunden. Bitte Node.js installieren und sicherstellen, " +
                    "dass es im PATH liegt."
            )
        val serverScript = SvelteLspPaths.resolveServerScript()
            ?: throw IllegalStateException(
                "Der gebuendelte Svelte-Sprachserver wurde in der Plugin-Installation nicht gefunden."
            )
        return GeneralCommandLine(node.toString(), serverScript.toString(), "--stdio").apply {
            withWorkDirectory(project.basePath)
            withCharset(Charsets.UTF_8)
        }
    }
}
