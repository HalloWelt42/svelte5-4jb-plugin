package de.hallowelt.svelte

import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.testFramework.fixtures.BasePlatformTestCase

/**
 * Prueft, dass gueltiger Svelte-5-Code NICHT faelschlich als fehlerhaft markiert wird -
 * inklusive '>' und '<' in Mustache-Ausdruecken (Pfeilfunktion, Vergleich), an denen
 * eine reine HTML-Zerlegung scheitert.
 */
class SvelteHighlightingTest : BasePlatformTestCase() {

    fun testValidSvelte5HasNoErrorHighlights() {
        myFixture.configureByText("App.svelte", CODE)
        val errors = myFixture.doHighlighting(HighlightSeverity.ERROR)
        val rendered = errors.joinToString("\n") { "  @${it.startOffset}-${it.endOffset}: ${it.description}" }
        assertEmpty("Unerwartete Fehler-Markierungen auf gueltigem Svelte-5-Code:\n$rendered", errors)
    }

    companion object {
        private val CODE = """
            <script lang="ts">
              let count = ${'$'}state(0);
              let doubled = ${'$'}derived(count * 2);
              let { name = 'Welt' }: { name?: string } = ${'$'}props();
              function inc(): void { count += 1; }
              const positive = () => count > 0;
            </script>

            {#snippet row(label: string, value: number)}
              <tr><td>{label}</td><td>{value}</td></tr>
            {/snippet}

            <h1>Hallo {name}</h1>
            <button onclick={() => inc()} class:active={count > 0}>Zaehler: {count}</button>

            <table>{@render row('Doppelt', doubled)}</table>

            {#if count > 5}
              <p>gross</p>
            {:else}
              <p>klein</p>
            {/if}

            <style>
              h1 { color: rebeccapurple; }
            </style>
        """.trimIndent()
    }
}
