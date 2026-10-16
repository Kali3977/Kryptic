# KRIPTIC Android project

This is an Android Studio project wrapping the KRIPTIC mobile demo in a native Android WebView.

## Build an APK
1. Install Android Studio and the Android SDK Platform 35 (Android 10 / API 29 or newer supported by this project).
2. Open this `KRIPTIC-Android` folder in Android Studio.
3. Allow Gradle sync to finish.
4. Select **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
5. When the build finishes, use **Locate**. The debug APK is normally at:
   `app/build/outputs/apk/debug/app-debug.apk`
6. Transfer that APK to Android phones, open it, and allow installation from that source if Android asks.

## What this version does
- Uses the KRIPTIC sample risk dashboard, app risk explorer, investigator, permission simulator, privacy blast-radius view, and report preview.
- Saves a text demo report to Downloads through the Android bridge.

## Important limitation
This build is a prototype with fictional sample app profiles and illustrative scores. It does **not** inspect installed apps or detect malware. It is not yet the full on-device auditor implementation.
