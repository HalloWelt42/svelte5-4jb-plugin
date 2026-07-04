package de.hallowelt.svelte.lsp.lang

import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.intellij.psi.impl.source.html.HtmlFileImpl

class SvelteHtmlFile(viewProvider: FileViewProvider) :
    HtmlFileImpl(viewProvider, SvelteHtmlParserDefinition.FILE) {

    override fun getFileType(): FileType = SvelteHtmlFileType

    override fun toString(): String = "SvelteHtmlFile:$name"
}
