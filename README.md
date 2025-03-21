[![Android Library Publish to JitPack](https://github.com/AbdulRehman-Pro/AlertBanner/actions/workflows/publish.yml/badge.svg)](https://github.com/AbdulRehman-Pro/AlertBanner/actions/workflows/publish.yml)
[![](https://jitpack.io/v/AbdulRehman-Pro/AlertBanner.svg)](https://jitpack.io/#AbdulRehman-Pro/AlertBanner)

![Android Studio](https://img.shields.io/badge/android%20studio-346ac1?style=for-the-badge&logo=android%20studio&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white)


# 🚀 AlertBanner - Android Custom Alert Banner

A customizable Android banner component for displaying alerts with smooth animations.

## 📦 Installation

### Step 1: Add JitPack repository to your project

Add the following in your `settings.gradle.kts` at the end of `repositories`:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

### Step 2: Add the dependency

```kotlin
dependencies {
    implementation("com.github.AbdulRehman-Pro:AlertBanner:Tag")
    implementation("com.github.AbdulRehman-Pro:AlertBanner:{{LATEST_VERSION}}")
}
```

---

## 🎨 Custom Attributes

You can customize the `AlertBanner` using XML attributes:

| Attribute Name         | Description                                      | Type      | Default |
|------------------------|--------------------------------------------------|-----------|---------|
| `bannerBackgroundColor` | Sets the background color of the banner        | `color`   | `FFF44336` |
| `bannerSurfaceColor`    | Sets the text and icon color                    | `color`   | `#FFFFFFFF` |
| `bannerFontFamily`      | Sets the custom font for the text               | `reference` | Default system font |
| `bannerLeadingIcon`     | Sets the icon on the left side                  | `reference` | Default leading icon |
| `bannerTrailingIcon`    | Sets the close icon on the right side           | `reference` | Default trailing icon |
| `bannerAnimate`         | Controls animation type (`top-to-bottom` or `bottom-to-top`) | `enum` | `top-to-bottom` |
| `bannerTextSize`        | Sets the text size in `sp`                      | `dimension` | `12sp` |
| `bannerIconsSize`       | Sets the icon size in `dp`                      | `dimension` | `16dp` |

---

## 🛠 Usage

### 1️⃣ Add in XML

```xml
<com.rehman.view.AlertBanner
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:bannerBackgroundColor="@color/red"
    app:bannerSurfaceColor="@color/white"
    app:bannerFontFamily="@font/custom_font"
    app:bannerLeadingIcon="@drawable/ic_warning"
    app:bannerTrailingIcon="@drawable/ic_close"
    app:bannerAnimate="bottom-to-top"
    app:bannerTextSize="16sp"
    app:bannerIconsSize="24dp" />
```

---

## 🕒 Duration Options in AlertBanner  

The **AlertBanner** supports three duration modes for displaying alerts. These determine how long the banner remains visible before auto-dismissing.  

| Duration Type       | Description | Auto-dismiss Time |
|---------------------|-------------|-------------------|
| **LENGTH_SHORT**  | Displays the banner for a short duration. Ideal for quick info messages. | **3 seconds** (3000ms) ⏳ |
| **LENGTH_LONG**   | Keeps the banner visible for a longer time, suitable for warnings or important updates. | **5 seconds** (5000ms) ⏳ |
| **LENGTH_INDEFINITE** | Keeps the banner visible indefinitely until the user manually dismisses it by clicking the close button. | ❌ No auto-dismiss, requires user action |

### 🛠 Example Usage in Code  

```kotlin
// Show alert banner for a short duration (3s)
alertBanner.showBanner(
    alertMessage = "✅ Item added to cart!",
    duration = AlertBanner.LENGTH_SHORT
)

// Show alert banner for a long duration (5s)
alertBanner.showBanner(
    alertMessage = "⚠️ Low battery! Charge your device.",
    duration = AlertBanner.LENGTH_LONG
)

// Show alert banner indefinitely (manual dismiss required)
alertBanner.showBanner(
    alertMessage = "❌ Error! Connection failed.",
    duration = AlertBanner.LENGTH_INDEFINITE
)
```

---

### 🛠️ `onDismiss` Callback Behavior in **AlertBanner**  

The **`onDismiss`** callback in **AlertBanner** is always executed when the banner is dismissed—whether it happens manually (via the close button) or automatically (after a set duration). However, it is **optional**—if no callback is provided, the banner simply closes without executing additional logic.

---

## 📌 **How `onDismiss` Works**
- If an `onDismiss` callback **is provided**, it **always triggers** when the banner is dismissed.
- If an `onDismiss` callback **is not provided**, the banner will **just close silently** without any extra actions.
- The callback runs whether the banner **auto-dismisses** (`LENGTH_SHORT` or `LENGTH_LONG`) or the user manually closes it.

---

## 🔧 **Examples of Different Behaviors**

### ✅ **Banner Without `onDismiss` (Just Closes)**
```kotlin
alertBanner.showBanner(
    alertMessage = "✅ Item added to cart!",
    duration = AlertBanner.LENGTH_SHORT
)
```
👉 The banner will disappear after **3 seconds** without executing any additional code.

---

### 🔄 **Banner With `onDismiss` (Triggers Action)**
```kotlin
alertBanner.showBanner(
    alertMessage = "🔄 Syncing complete!",
    duration = AlertBanner.LENGTH_LONG,
    onDismiss = {
        println("📝 Banner dismissed! Logging event...")
    }
)
```
👉 After **5 seconds** (or when the close button is clicked), the log `"📝 Banner dismissed! Logging event..."` appears in the console.

---

### ⚠️ **Trigger Another Banner on Dismiss**
```kotlin
alertBanner.showBanner(
    alertMessage = "⚠️ Low battery! Charge your device.",
    duration = AlertBanner.LENGTH_INDEFINITE,
    onDismiss = {
        alertBanner.showBanner(
            alertMessage = "🔌 Plug in your charger!",
            duration = AlertBanner.LENGTH_SHORT
        )
    }
)
```
👉 Once the first banner is closed, a **new banner** appears immediately.

---

## 🎯 **Best Use Cases for `onDismiss`**
✔️ Logging user actions (e.g., tracking dismissed alerts)  
✔️ Triggering follow-up UI changes (e.g., showing another alert)  
✔️ Updating backend state (e.g., marking errors as "seen")  
✔️ Displaying confirmation messages (e.g., "Action completed!")  

---

🚀 This feature makes **AlertBanner** flexible and customizable! If you don’t need additional actions, just skip the `onDismiss` parameter, and the banner will close without extra logic. 🔥

---

## 🎭 Animation Behavior

The `bannerAnimate` attribute allows controlling how the banner appears and disappears:

- `top-to-bottom` (default) ➡️ Slides in from the top and exits towards the top.
- `bottom-to-top` ⬆️ Slides in from the bottom and exits towards the bottom.

---

## 🏆 License

This library is open-source under the MIT License.

---

Enjoy using `AlertBanner` and feel free to contribute! 🚀🔥

