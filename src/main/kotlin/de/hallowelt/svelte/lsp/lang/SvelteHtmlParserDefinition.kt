package de.hallowelt.svelte.lsp.lang

import com.intellij.lang.html.HTMLParserDefinition
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType

/**
 * Nutzt die native HTML-Zerlegung der Plattform (inklusive Einbettung von `<script>` als
 * JS/TS und `<style>` als CSS). Svelte-Vorlagenausdruecke wie `{#if}` oder `{@render}`
 * bleiben Textinhalt - fuer die Svelte-Semantik ist der Sprachserver zustaendig.
 */
class SvelteHtmlParserDefinition : HTMLParserDefinition() {
    override fun getFileNodeType(): IFileElementType = FILE

    override fun createFile(viewProvider: FileViewProvider): PsiFile = SvelteHtmlFile(viewProvider)

    companion object {
        val FILE = IFileElementType("SVELTE_HTML_FILE", SvelteHtmlLanguage.INSTANCE)
    }
}
