package de.hallowelt.svelte.lsp.lang

import com.intellij.lang.html.HTMLLanguage
import com.intellij.openapi.fileTypes.LanguageFileType

/**
 * Svelte-Dateien sind HTML-artig (Markup + `<script>` + `<style>`), daher ist die Sprache
 * ein HTML-Dialekt. Die Svelte-spezifische Semantik (Runes, Typen, Diagnostik) liefert der
 * Sprachserver; hier geht es nur um native Dateityp-Erkennung und Hervorhebung.
 */
class SvelteHtmlLanguage private constructor() : HTMLLanguage(HTMLLanguage.INSTANCE, "SvelteHTML") {
    override fun getDisplayName(): String = "Svelte"
    override fun isCaseSensitive(): Boolean = true
    override fun getAssociatedFileType(): LanguageFileType = SvelteHtmlFileType

    companion object {
        @JvmField
        val INSTANCE = SvelteHtmlLanguage()
    }
}
