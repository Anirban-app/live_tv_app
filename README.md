# Live TV App

This repository contains a Java-based Android app scaffold for a dual UI Live TV streaming application: Mobile (touch UI) and Android TV / Google TV (D-pad remote UI).

## Architecture

- **MVVM architecture** with `ViewModel` and `LiveData`.
- Package-by-feature structure:
  - `ui.splash`
  - `ui.mobile.home`
  - `ui.tv.home`
  - `ui.player`
  - `ui.settings`
  - `data.api`, `data.model`, `data.repo`
  - `security`
  - `util`

## Key features generated

- `SplashActivity` with 3-second boot delay and runtime UI mode detection using `UiModeManager`.
- `MainActivity` as mobile home with `ViewPager2` banner and `RecyclerView` channel list.
- `TvHomeActivity` as TV home with focusable grid layout and remote-ready UI effects.
- `PlayerActivity` with ExoPlayer playback, remote D-pad channel zapping, and a temporary channel overlay.
- `SettingsFragment` with theme toggle, TV boot-to-player switch, and an in-place `LoginDialogFragment`.
- Security layer skeleton using `AES-256-CBC` + `HMAC-SHA256` encryption helpers.
- Retrofit / OkHttp repository setup for encrypted API handshakes.

## Gradle Dependencies

- AndroidX AppCompat, Lifecycle, RecyclerView, ViewPager2
- Leanback for Android TV UI support
- Material Components
- Retrofit2 / Gson / OkHttp3
- ExoPlayer
- Glide

## Build instructions

This project requires a local Android SDK installation.

1. Install the Android SDK and set one of these:
   - `ANDROID_HOME`
   - `ANDROID_SDK_ROOT`

2. Or create `local.properties` at the repo root:

```properties
sdk.dir=/path/to/Android/Sdk
```

3. Build the app:

```bash
cd /workspaces/live_tv_app
gradle :app:assembleDebug
```

## Notes

- The project currently uses a mocked channel list for UI wiring.
- The security layer is implemented as runtime helpers; for production, move the master API key into Android Keystore or native `.so`.
- The Retrofit and repository layer uses AES/HMAC helper classes to support in-memory encrypted handshake flows.

## Next steps

- Add actual API endpoints and server-side encrypted responses.
- Replace mocked channel data with repository-based fetch logic.
- Add actual native key storage and Keystore integration.
