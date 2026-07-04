package de.hallowelt.svelte.lsp.lang

import com.intellij.codeInspection.DefaultXmlSuppressionProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.xml.XmlAttribute
import com.intellij.psi.xml.XmlAttributeValue

/**
 * Unterdrueckt HTML-/XML-Pruefungen, die Svelte-Syntax faelschlich als Fehler markieren
 * wuerden: Direktiven (`bind:`, `on:`, `use:`, ...), Event-Attribute (`onclick`),
 * Komponenten-Tags (Grossbuchstabe / `svelte:*`) und Attributwerte mit `{...}`.
 * Die inhaltliche Pruefung uebernimmt der Svelte-Sprachserver.
 */
class SvelteHtmlInspectionSuppressor : DefaultXmlSuppressionProvider() {

    private val directivePrefixes =
        setOf("on", "bind", "use", "transition", "in", "out", "animate", "class", "style", "let")

    override fun isProviderAvailable(file: PsiFile): Boolean = file is SvelteHtmlFile

    override fun isSuppressedFor(element: PsiElement, inspectionId: String): Boolean {
        when (inspectionId) {
            "XmlUnboundNsPrefix",
            "HtmlUnknownTarget",
            "HtmlUnknownAnchorTarget",
            "XmlInvalidId",
            "XmlDuplicatedId",
            "RequiredAttributes",
            "CheckTagEmptyBody",
            "CheckEmptyScriptTag" -> return true

            "HtmlUnknownAttribute" -> {
                val attr = PsiTreeUtil.getParentOfType(element, XmlAttribute::class.java, false)
                    ?: element as? XmlAttribute
                    ?: return false
                val prefix = attr.namespacePrefix
                return prefix in directivePrefixes || attr.name.startsWith("on") || attr.name.contains(":")
            }

            "HtmlUnknownTag" -> {
                val text = element.text ?: return false
                return text.firstOrNull()?.isUpperCase() == true || text.startsWith("svelte:")
            }

            "HtmlWrongAttributeValue" -> {
                val value = element as? XmlAttributeValue
                    ?: PsiTreeUtil.getParentOfType(element, XmlAttributeValue::class.java, false)
                return value != null && value.text.contains("{")
            }
        }
        return false
    }
}
