package de.hallowelt.svelte.lsp.lang

import com.intellij.icons.AllIcons
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object SvelteHtmlFileType : LanguageFileType(SvelteHtmlLanguage.INSTANCE) {
    override fun getName(): String = "Svelte"
    override fun getDescription(): String = "Svelte-Komponente"
    override fun getDefaultExtension(): String = "svelte"
    override fun getIcon(): Icon = AllIcons.FileTypes.Html
}
