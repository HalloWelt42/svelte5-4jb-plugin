# svelte5-4jb-plugin

Native Svelte-5-Unterstuetzung fuer JetBrains-IDEs (PhpStorm, WebStorm,
IntelliJ IDEA Ultimate, PyCharm Professional).

## Funktionen

- Nativer `.svelte`-Dateityp (HTML-Dialekt) mit eigenem, `{...}`-bewusstem Lexer:
  Svelte-Vorlagensyntax (Runes, `{#snippet}`, `{@render}`, Bloecke, Direktiven) wird
  korrekt zerlegt und **nicht faelschlich als Fehler markiert** - auch nicht bei
  Ausdruecken mit `>` wie `onclick={() => handler()}`.
- Hervorhebung fuer Markup, `<script lang="ts">` (JavaScript/TypeScript) und
  `<style>` (CSS) ueber die native Engine der IDE.
- Code-Faltung, Formatierung, Klammer-Zuordnung, Emmet, Teil-Vervollstaendigung und
  Navigation.

Noch nicht enthalten: projektweite Typpruefung ueber svelte2tsx (Sprachserver),
spezielle Keyword-Faerbung, Auto-Import beim Einfuegen, Debugger-Integration.

## Voraussetzungen

- Eine JavaScript-faehige JetBrains-IDE, Build 261 (2026.1) oder neuer.
- Zum Selbstbauen genuegt die in der IDE gebuendelte JBR als JVM; das JDK-21-Toolchain
  wird per Gradle automatisch bereitgestellt.

## Bauen

```bash
JAVA_HOME="$HOME/Applications/PhpStorm.app/Contents/jbr/Contents/Home" ./gradlew buildPlugin
```

Ergebnis: `build/distributions/svelte5-4jb-plugin-<version>.zip`. Standardmaessig wird
gegen die lokal installierte PhpStorm-Instanz gebaut (`platformLocalPath` in
`gradle.properties`); fuer eine andere IDE:

```bash
./gradlew buildPlugin -PplatformLocalPath=/Applications/WebStorm.app
```

## Installieren

Einstellungen > Plugins > Zahnrad > "Plugin von Datenträger installieren..." > das Zip
auswaehlen. Andere Svelte-Plugins vorher deaktivieren (sie beanspruchen denselben
Dateityp). IDE neu starten.

## Herkunft und Lizenz

Dieses Projekt verwendet die Svelte-Sprachschicht des Projekts
[tomblachut/svelte-intellij](https://github.com/tomblachut/svelte-intellij) wieder
(spaeter von JetBrains gepflegt), veroeffentlicht unter der MIT-Lizenz,
Copyright (c) 2019 Tomasz Błachut. Sie wurde an aktuelle JetBrains-Plattform-Builds
angepasst; der gebuendelte Sprachserver und diverse Randfunktionen des Originals
wurden entfernt. Siehe `LICENSE` und `NOTICE`.

Lizenz: MIT.
