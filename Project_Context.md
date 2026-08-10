# Project Context: Quotely (KMP Quotes App)

## 1. High-Level Vision & Strategy
- **Purpose**: Ultra-aesthetic, typography-first Kotlin Multiplatform app dedicated to saving, viewing, and styling thought-provoking quotes.
- **Key Differentiator**: High visual fidelity, distraction-free focus mode, editorial typography presets, high-res image export.
- **Platforms**: Android & iOS (100% UI in `commonMain` via Compose Multiplatform).
- **Environment**: Antigravity 2.0 Desktop Workspace (Multi-Agent Engine).

## 2. Tech Stack & Dependencies
- **Language**: Kotlin 2.0+
- **UI Framework**: Compose Multiplatform
- **Architecture**: Single-Activity MVVM (`androidx.lifecycle.ViewModel` + `StateFlow`)
- **Database**: Room KMP (`@Database`, `@Dao`, `@Entity`)
- **Typography**: Resource Fonts / Custom Google Fonts (`FontFamily`)
- **Persistence**: Room KMP & Multiplatform-Settings

## 3. Directory Structure Standards
shared/src/commonMain/kotlin/com/app/quotely/
├── data/           # Room Database, Entities, DAOs, Repositories
├── domain/         # Quote, Tag, ThemePreset, FontPreset Models
├── ui/             # Composables, Theme, ViewModels, UI State
│   ├── theme/      # Color.kt, Type.kt, ThemePreset.kt, QuotelyTheme.kt
│   ├── gallery/    # GalleryScreen & ViewModel
│   ├── detail/     # QuoteDetailScreen & ViewModel
│   └── editor/     # CreateQuoteScreen & ViewModel
└── App.kt          # Compose Entrypoint & Navigation

## 4. Strict Code Conventions
- **State Management**: Screens MUST use a single immutable `StateFlow<UiState>` exposed by a `ViewModel`.
- **Stateless Composables**: Composables receive state objects and emit event callbacks.
- **Platform Separation**: Pure Kotlin in `commonMain`. Zero imports of `android.*` or `UIKit.*`.
- **Database Mapping**: Room Entities must map to pure Domain Models before reaching the UI layer.

## 5. Active Task Backlog
- [x] Phase 0: MVP Scope & Stack Definition
- [x] Phase 1: Stitch MCP Integration & Theme Token Extraction
- [x] Phase 2: Theme Engine Implementation inside `ui/theme/`
- [x] Phase 3: Vertical Slice 1 — Room KMP Database & DAO Setup
- [x] Phase 4: Vertical Slice 2 — Quote Capture Screen & Live Preview Editor
- [x] Phase 5: Vertical Slice 3 — Masonry Gallery Feed with Tag Filtering
- [x] Phase 6: Vertical Slice 4 — Track-Stopper Fullscreen Focus & Multi-Format Image Export