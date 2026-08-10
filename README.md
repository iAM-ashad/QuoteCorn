# QuoteCorn 👑

> **"CAPTURE WHAT MOVES YOU"**

QuoteCorn is a high-fashion, high-performance **Kotlin Multiplatform (KMP)** application designed for Android and iOS. Built with Compose Multiplatform, Room KMP database, and an editorial bottom sheet canvas, QuoteCorn offers 9 imperial typographic theme presets tailored for timeless quotes.

---

## 🏛️ Project Structure

- [`/shared`](./shared/src) — Contains the shared KMP domain logic, Room database, ViewModels, UI Composables, and 9 Theme Presets.
  - [`commonMain`](./shared/src/commonMain/kotlin) — Multiplatform Compose UI screens (`GalleryScreen`, `QuoteDetailScreen`, `QuoteEditorScreen`) and Room DB models.
  - [`androidMain`](./shared/src/androidMain/kotlin) & [`iosMain`](./shared/src/iosMain/kotlin) — Platform-specific implementations (e.g. image export).
- [`/androidApp`](./androidApp) — Android app entry point with `androidx.core:core-splashscreen` native splash integration.
- [`/iosApp`](./iosApp/iosApp) — iOS app entry point and SwiftUI wrapper.

---

## 🚀 Running the App

### Android
```bash
./gradlew :androidApp:assembleDebug
```

### iOS
Open `iosApp/iosApp.xcworkspace` (or `iosApp.xcodeproj`) in Xcode and run on simulator or device.

---

## 🧪 Testing

- Unit tests: `./gradlew test`
- Playwright E2E QA suite: `npx playwright test`

---

*Powered by Kotlin Multiplatform & Compose Multiplatform.*
