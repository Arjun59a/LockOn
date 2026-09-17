🔒 Lock-On

Learn first, enjoy later.

Lock-On is an Android educational app-lock application designed to help users stay focused by requiring them to complete short learning/quiz questions before accessing selected entertainment or distracting apps.

Learn something new → complete the questions → enjoy your selected app.

✨ Features

📱 Detects the currently opened/foreground app.

🔒 Lets users select apps that should be protected.

♿ Uses Android Accessibility Service to detect when a protected app is opened.

🧠 Shows a short question-based learning screen before allowing access.

✅ Requires the user to complete the configured questions successfully.

💾 Stores selected app-lock settings locally using SharedPreferences.

🎓 Combines learning with controlled entertainment access.

📱 How It Works

Select Apps
     ↓
Enable Lock-On Accessibility Service
     ↓
Open a Protected App
     ↓
Lock-On Detects the App
     ↓
Question Screen Appears
     ↓
Answer the Questions
     ↓
Complete Successfully
     ↓
Access the App

⚙️ Setup

1. Open Lock-On

Launch the Lock-On Android application.

2. Select the apps you want to protect

On the Lock Your Apps screen, select the applications you want Lock-On to monitor.



3. Enable Lock-On Accessibility Service

Lock-On needs Android's Accessibility Service permission to detect when a protected application is opened.

Tap Enable Lock-On Access in the app.

Android will open the Accessibility settings.

Follow this path:

Settings
   ↓
Accessibility
   ↓
Installed apps
   ↓
Lock-On
   ↓
Turn ON / Enable

Depending on the Android or One UI version, the exact wording/layout may be slightly different.

Quick path:
Settings → Accessibility → Installed apps → Lock-On → Enable

4. Return to Lock-On

After enabling the service, return to the Lock-On application.

The Accessibility Service can now detect when a protected app comes to the foreground.

🧠 Question-Based App Lock

When a protected application is opened, Lock-On detects the package and displays the learning screen.



The user answers the questions and submits the answer. After successfully completing the required questions, access to the protected application is allowed.

♿ Why Accessibility Service?

Android's Accessibility Service allows Lock-On to receive information about changes to the currently visible application.

Lock-On uses it to:

Listen for foreground/window changes.

Identify the package currently being displayed.

Check whether that package is protected.

Launch the question screen when necessary.

The service is used for the core app-detection and app-lock mechanism.

💾 Data Storage

Lock-On uses Android SharedPreferences for lightweight local settings such as selected/locked applications.

No external database is required for the basic app-lock configuration.

🏗️ Project Structure

A simplified structure of the project:

Lock-On/
├── app/
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com/example/lockon/
│           │       ├── MainActivity.kt
│           │       ├── SetALock.kt
│           │       ├── QuestionActivity.kt
│           │       ├── LockOnAccessibilityService.kt
│           │       ├── Appmonitor.kt
│           │       └── AppInfoAdapter.kt
│           │
│           ├── res/
│           │   ├── layout/
│           │   ├── drawable/
│           │   └── values/
│           │
│           └── AndroidManifest.xml
│
├── docs/
│   └── images/
│       ├── accessibility-screen.png
│       └── quiz-screen.png
│
└── README.md

File names may differ depending on the current version of the project.

🛠️ Technologies Used

Kotlin

Android SDK

Android Accessibility Service

SharedPreferences

RecyclerView

PackageManager

UsageStatsManager

Intents

XML layouts

🎯 Project Goal

The goal of Lock-On is to reduce unplanned entertainment/app usage by introducing a small educational step before access.

Instead of simply blocking an application:

❌ Block the app

Lock-On follows:

📚 Learn
   ↓
🧠 Answer
   ↓
🎮 Enjoy

🚀 Future Improvements

Possible improvements include:

📚 Syllabus-based question generation

🤖 Adaptive difficulty

⏱️ Configurable app usage duration

📊 Learning/progress statistics

👨‍👩‍👧 Parent-controlled settings

🔢 PIN protection for lock settings

🎯 Subject-specific question sets

🔔 Study reminders

📈 Daily learning and app-usage reports

⚠️ Troubleshooting

Lock-On does not detect protected apps

Make sure:

Lock-On Accessibility Service is enabled.

The application you opened is selected as a protected app.

Lock-On is allowed to run in the background if your device requires it.

Battery optimization is not stopping the Lock-On service.

Accessibility service turns off automatically

Some Android device manufacturers apply aggressive battery/background restrictions. Check the device's battery or app-management settings and allow Lock-On to continue running in the background.

👨‍💻 Development

Lock-On is an Android project focused on combining education, productivity, and app-control mechanisms.

📄 License

Add your preferred license here, for example MIT, Apache-2.0, or a project-specific license.

Lock-On — Learn first, enjoy later. 🔒📚
