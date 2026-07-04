package de.hallowelt.svelte.lsp

import com.intellij.execution.configurations.PathEnvironmentVariableUtil
import com.intellij.ide.plugins.PluginManagerCore
import com.intellij.openapi.extensions.PluginId
import java.nio.file.Files
import java.nio.file.Path

/**
 * Loest zur Laufzeit die Pfade auf, die der Sprachserver-Start braucht:
 * die Plugin-Installation, das gebuendelte server.js und die node-Executable.
 */
internal object SvelteLspPaths {
    private const val PLUGIN_ID = "de.hallowelt.svelte.lang"

    fun pluginPath(): Path? =
        PluginManagerCore.getPlugin(PluginId.getId(PLUGIN_ID))?.pluginPath

    /** Das mitgelieferte server.js innerhalb der Plugin-Installation. */
    fun resolveServerScript(): Path? {
        val base = pluginPath() ?: return null
        val candidate = base
            .resolve("language-server")
            .resolve("node_modules")
            .resolve("svelte-language-server")
            .resolve("bin")
            .resolve("server.js")
        return candidate.takeIf { Files.isRegularFile(it) }
    }

    /**
     * Findet die node-Executable. Von der GUI gestartete IDEs haben auf macOS oft einen
     * gekuerzten PATH, daher zusaetzlich einige uebliche Installationsorte pruefen.
     */
    fun resolveNodeExecutable(): Path? {
        PathEnvironmentVariableUtil.findInPath("node")?.let { return it.toPath() }

        val home = System.getProperty("user.home")
        val candidates = listOf(
            "/opt/homebrew/bin/node",
            "/usr/local/bin/node",
            "/usr/bin/node",
            "$home/.nvm/current/bin/node",
            "$home/.local/bin/node",
        )
        return candidates.map { Path.of(it) }.firstOrNull { Files.isExecutable(it) }
    }
}
