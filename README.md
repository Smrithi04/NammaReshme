# NammaReshme: Digital Sericulture Assistant 🌿🐛
NammaReshme is a premium Android application designed to digitize and optimize the silk-rearing process for sericulturists. It provides a comprehensive platform for tracking active silkworm batches, monitoring environmental conditions, and receiving data-driven advice to maximize cocoon quality and yield.
## 🚀 Key Features
- **Dynamic Dashboard**: A state-aware dashboard that transitions from an informational hub to an active batch monitor when a rearing cycle begins.
- **Batch Management**: Easily initialize, track, and archive silkworm batches. Supports various breeds like Polyvoltine and Bivoltine.
- **Climate Monitoring**: Log real-time temperature and humidity data.
- **Intelligent Advice**: Receive instant feedback on environmental safety (Safe, Warning, or Critical) based on the specific instar (growth stage) of the batch.
- **Progress Tracking**: Visual progress bars and countdowns to keep track of growth stages and harvest dates.
- **Multi-language Support**: Designed for accessibility with localized strings (including Kannada).
- **Secure Authentication**: Firebase-powered authentication for personalized user profiles and secure data storage.
## 🛠 Tech Stack
- **UI Framework**: [Jetpack Compose](https://developer.android.com/compose) (Material 3)
- **Language**: [Kotlin](https://kotlinlang.org/)
- **Dependency Injection**: [Hilt](https://dagger.dev/hilt/)
- **Backend/Persistence**: 
  - [Firebase Auth](https://firebase.google.com/products/auth) for user sessions.
  - [Firebase Firestore](https://firebase.google.com/products/firestore) for real-time data persistence.
- **Navigation**: [Compose Navigation](https://developer.android.com/jetpack/compose/navigation)
- **Icons**: [Lucide Icons](https://lucide.dev/)
- **Architecture**: MVVM (Model-View-ViewModel)
## 📁 Project Structure
```text
app/src/main/java/com/example/nammareshme/
├── data/               # Data layer (Models, Repositories)
├── di/                 # Dependency Injection modules
├── navigation/         # Navigation graph and routes
├── ui/                 # UI layer
│   ├── screens/        # Compose screens (Dashboard, Splash, Batch, etc.)
│   ├── theme/          # Custom design system tokens (Color, Type, Shape)
│   └── components/     # Reusable UI components
└── viewmodel/          # State management and business logic
```
## 🛠 Setup Instructions
1. **Clone the repository**:
   ```bash
   git clone https://github.com/Smrithi04/NammaReshme.git
   ```
2. **Firebase Setup**:
   - Create a new project in the [Firebase Console](https://console.firebase.google.com/).
   - Add an Android app with package name `com.example.nammareshme`.
   - Download the `google-services.json` and place it in the `app/` directory.
   - Enable **Email/Password** authentication in the Firebase Auth tab.
   - Initialize **Cloud Firestore**.
3. **Build and Run**:
   - Open the project in Android Studio (Ladybug or later recommended).
   - Sync Gradle files.
   - Run the app on an emulator or physical device.
## 📄 License
This project is licensed under the MIT License - see the LICENSE file for details.
---
*Empowering sericulturists with digital intelligence.*
