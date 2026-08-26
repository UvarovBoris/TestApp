# AGENTS.md

Guidance for AI agents working in this repository.

## Project Overview

- Kotlin Android app built with Jetpack Compose (Material 3).
- Package namespace / applicationId: `com.uvarov.testapp`.
- Single module `:app` (see `settings.gradle.kts`). Adds more modules under `include(":name")` in `settings.gradle.kts`.
- Version catalog is the single source of truth for dependencies: `gradle/libs.versions.toml`. Do not hardcode versions in `build.gradle.kts`.

## Key Stack

| Area | Choice |
|---|---|
| Language | Kotlin 2.2.10 |
| UI | Jetpack Compose + Material 3 (BOM `2026.02.01`) |
| Build | AGP 9.2.1, Kotlin Compose plugin |
| Min / Target / Compile SDK | minSdk 24 / targetSdk 36 / compileSdk 37 |
| Java | source/target 11 |

## Commands

Run all Gradle commands via the wrapper (`./gradlew` on macOS/Linux, `.\gradlew.bat` on Windows). Never call `gradle` directly.

- Build (debug APK): `.\gradlew.bat assembleDebug`
- Install on connected device: `.\gradlew.bat installDebug`
- Unit tests (JVM): `.\gradlew.bat test`
- Instrumented tests (needs emulator/device): `.\gradlew.bat connectedAndroidTest`
- Compile check (fast, no APK): `.\gradlew.bat compileDebugKotlin`
- Lint: `.\gradlew.bat lintDebug`
- Clean: `.\gradlew.bat clean`

Note: `lintDebug` is slow; prefer `compileDebugKotlin` and `test` for quick feedback.

## Project Structure

```
app/src/main/java/com/uvarov/testapp/
├── MainActivity.kt          # Single activity, Compose entry point
└── ui/
    └── theme/               # Color.kt, Theme.kt, Type.kt (Compose theme)
app/src/main/res/            # Android resources (values, drawable, xml, mipmap)
app/src/test/                # JVM unit tests
app/src/androidTest/         # Instrumented tests
```

Convention (Compose, unidirectional data flow):
- `data/` — models, repositories, mappers
- `domain/` — use cases (if business logic warrants it)
- `ui/<feature>/` — per-feature: `XxxScreen.kt`, `XxxViewModel.kt`, `XxxUiState.kt`
- `di/` — DI setup if a DI framework is added

## Coding Conventions

- Follow Kotlin official style (`kotlin.code.style=official`); 4-space indentation.
- Compose: use `@Composable` naming (e.g. `GreetingScreen`, `ItemCard`), hoist state, keep composables stateless where possible. Expose `@Preview` composables for UI.
- State: prefer `ViewModel` + immutable `data class` UI state + `StateFlow`/`collectAsStateWithLifecycle`.
- Do NOT add `@Composable` imports/symbols that duplicate existing BOM-managed versions.
- Strings: hardcoded text in composables is acceptable for the default project; use `R.string` resources when localizing.
- Use `Modifier` as the first optional parameter of custom composables (`modifier: Modifier = Modifier`).

## Adding Dependencies

1. Add the artifact under `[libraries]` in `gradle/libs.versions.toml` (add a `[versions]` entry if a new version is needed).
2. Reference it via `libs.<name>` in `app/build.gradle.kts`.
3. Keep BOM-managed Compose artifacts (ui, material3, etc.) without explicit versions — they inherit from the BOM.

## Testing

- Unit tests: `app/src/test/java/com/uvarov/testapp/` (JUnit 4).
- Compose UI tests: `app/src/androidTest/` (ui-test-junit4 + BOM already configured).
- Instrumented runner: `androidx.test.runner.AndroidJUnitRunner`.

## Gotchas

- `local.properties` holds SDK path and is gitignored — never commit it.
- `gradlew` / `gradlew.bat` / `gradle/wrapper` are checked in; do not regenerate the wrapper.
- Release build has minification disabled; `proguard-rules.pro` is present but currently empty.
