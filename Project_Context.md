# Project Context: QuoteCorn (KMP Quotes App)

## 1. High-Level Vision & Strategy
- **App Name**: `QuoteCorn`
- **Tagline**: `"CAPTURE WHAT MOVES YOU"`
- **Purpose**: Ultra-aesthetic, typography-first Kotlin Multiplatform app dedicated to saving, viewing, and styling thought-provoking quotes.
- **Key Differentiators**: High visual fidelity, 9 curated typography theme presets, ambient vertical gradient engine, high-fashion Bottom Sheet options panel, styled brand dialogs, and multi-ratio PNG image export.
- **Platforms**: Android & iOS (100% UI in `commonMain` via Compose Multiplatform).
- **Environment**: Antigravity 2.0 Desktop Workspace (Multi-Agent Engine).

## 2. Tech Stack & Dependencies
- **Language**: Kotlin 2.0+
- **UI Framework**: Compose Multiplatform (Material 3)
- **Architecture**: Single-Activity MVVM (`androidx.lifecycle.ViewModel` + `StateFlow`)
- **Database**: Room KMP (`@Database`, `@Dao`, `@Entity`) & SQLite Bundled Driver
- **Typography**: Curated Font Families (`Playfair Display`, `Cormorant Garamond`, `Cinzel`, `Bodoni Moda`, `Lora`, `Fraunces`, `Prata`, `Instrument Serif`, `Newsreader`)
- **QA Automation**: Playwright E2E Test Suite (`tests/e2e/quotely.spec.ts`) & Kotlin Test (`commonTest`)

## 3. Directory Structure Standards
```
shared/src/commonMain/kotlin/com/app/quotely/
├── data/           # Room Database, Entities, DAOs, Repositories
├── domain/         # Quote, Tag, ThemePreset Models
├── ui/             # Composables, Theme, ViewModels, UI State
│   ├── theme/      # Color.kt, Type.kt, ThemePreset.kt, QuotelyTheme.kt
│   ├── components/ # QuotelySnackbar, DeleteConfirmationDialog, ImageExporter
│   ├── gallery/    # GalleryScreen, GalleryViewModel, QuoteGridCard
│   ├── detail/     # QuoteDetailScreen, QuoteDetailViewModel, DetailControlToolbar (Bottom Sheet)
│   └── editor/     # CreateQuoteScreen, CreateQuoteViewModel, LiveQuotePreviewCard
└── App.kt          # Compose Entrypoint & Navigation
```

## 4. Curated 9-Theme Typography & Color Specifications
1. **Creator's Choice** (*The High-Fashion Monolith*): `Playfair Display` (600 SemiBold) + `Inter` (500 Medium, All-Caps, `0.15.em`). Pitch Black (`#000000`) + Warm Gold (`#D4AF37`).
2. **Aurelian Monolith** (*The Imperial Crown*): `Cormorant Garamond` (600 SemiBold / Italic) + `Plus Jakarta Sans` (500 Medium, `0.1.em`). Imperial Black (`#12100B`) + Burnished Gold (`#E6C280`).
3. **Midnight Obsidian** (*The Cybernetic Roman*): `Cinzel` (500 Medium) + `Space Mono` (400 Regular). Pure Black (`#000000`) + Electric Cyan (`#70C0F0`).
4. **Royal Emerald** (*The Aristocratic Court*): `Bodoni Moda` (600 SemiBold) + `EB Garamond` (500 Medium, Italic). Deep Emerald (`#0B1B15`) + Champagne Gold (`#F3E5AB`).
5. **Nordic Twilight** (*The Minimalist Sanctum*): `Lora` (500 Medium / Italic) + `Outfit` (400 Regular, All-Caps). Frost Charcoal (`#1A1E24`) + Ice Cyan (`#B4D5E0`).
6. **Bespoke Espresso** (*The Leather Library*): `Fraunces` (600 SemiBold) + `Inconsolata` (500 Medium). Dark Espresso (`#1B1412`) + Soft Cream (`#F5EBE6`).
7. **Crimson Dynasty** (*The Velvet Opera*): `Prata` (400 Regular) + `Satoshi` (500 Medium, All-Caps, `0.12.em`). Velvet Burgundy (`#1F0A0E`) + Rose Gold (`#F4C2C2`).
8. **Serene Sanctuary** (*The Mindfulness Studio*): `Instrument Serif` (400 Regular / Italic) + `DM Sans` (500 Medium). Deep Sage (`#131A17`) + Soft Pearl (`#E2E8E4`).
9. **Editorial Parchment** (*The New York Monograph*): `Newsreader` (500 Medium) + `Source Sans 3` (600 SemiBold, All-Caps). Aged Ink (`#1C1917`) + Parchment Gold (`#E8DCC4`).

## 5. Active Task Backlog & Milestones
- [x] Phase 0: MVP Scope & Stack Definition
- [x] Phase 1: Stitch MCP Integration & Theme Token Extraction
- [x] Phase 2: Theme Engine & 9 Curated Typography Specifications
- [x] Phase 3: Vertical Slice 1 — Room KMP Database & DAO Setup
- [x] Phase 4: Vertical Slice 2 — Quote Capture Screen & Live Preview Editor
- [x] Phase 5: Vertical Slice 3 — Masonry Gallery Feed with Tag Filtering & Clear Search
- [x] Phase 6: Vertical Slice 4 — Track-Stopper Fullscreen Focus, Bottom Sheet Panel & Image Export
- [x] Phase 7: UI Polish — Brand Monolith Header, Styled Delete Confirmation & QuotelySnackbar
- [x] Phase 8: Automated QA — Playwright E2E Test Suite (4/4 Passed) & Unit Test Suite

## 6. Release Backlog Checklist

### Android Packaging (.aab)
- [ ] Configure signing credentials in `androidApp/build.gradle.kts` (`release` signing block with keystore).
- [ ] Set `versionCode` and `versionName` in `build.gradle.kts` (e.g. `versionCode = 1`, `versionName = "1.0.0"`).
- [ ] Execute `.\gradlew.bat :androidApp:bundleRelease` to generate release Android App Bundle (`.aab`).
- [ ] Run ProGuard / R8 shrinker verification on release APK/AAB build.

### iOS App Packaging (.ipa / Xcode Archive)
- [ ] Open `iosApp/iosApp.xcodeproj` in Xcode on macOS build runner.
- [ ] Set App Bundle Identifier (`com.app.quotecorn`) & Signing Team.
- [ ] Verify framework linkage (`Shared.framework` generated via `:shared:embedAndSignAppleFrameworkForXcode`).
- [ ] Build & Archive for iOS App Store submission via Xcode Organizer.