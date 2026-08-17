# Android Studio Setup Guide

## 1. Setup Android Studio

1. Download Android Studio from [developer.android.com](https://developer.android.com/studio)
2. Run the installer and follow the setup wizard
3. Choose "Standard" installation (recommended)
4. Install Android SDK, Emulator, and build tools automatically
5. Complete initial setup wizard

## 2. Create an Empty Activity Project

1. Open Android Studio → **File → New → New Project**
2. Select **Empty Activity** template
3. Configure the project:
   - **Name**: Your app name
   - **Package name**: `com.example.yourapp`
   - **Save location**: Choose your folder
   - **Language**: Kotlin (recommended) or Java
   - **Minimum API level**: API 21 or higher
4. Click **Finish**

## 3. Download Android Emulator

1. Open **Tools → Device Manager**
2. Click **Create Device**
3. Select a device (e.g., Medium Phone)
4. Choose **System Image** (e.g., Android 13)
5. Download the selected image
6. Configure emulator settings and click **Finish**

## 4. Build & Run on Emulator

1. Start the emulator: **Device Manager → Click play icon**
2. Wait for emulator to fully boot
3. Build the app: **Build → Make Project** (or Ctrl+F9)
4. Run the app: **Run → Run 'app'** (or Shift+F10)
5. Select the running emulator and click **OK**

## 5. Empty Activity Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/yourapp/
│   │   │   └── MainActivity.kt      (Main entry point)
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   └── activity_main.xml    (UI layout)
│   │   │   ├── values/
│   │   │   │   └── strings.xml          (App strings)
│   │   │   └── drawable/                (Images/icons)
│   │   └── AndroidManifest.xml      (App configuration)
│   └── test/                        (Unit tests)
├── build.gradle                     (App dependencies)
└── proguard-rules.pro              (Obfuscation rules)
```

