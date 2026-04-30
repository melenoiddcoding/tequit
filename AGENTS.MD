# Tequitshit Android - Agent Instructions

This is a native Android app built with Kotlin and Jetpack Compose.

Do not add features outside the current phase.

Current scope:
- Auth
- Roles: user, notifier, admin
- Train status
- Crossing status
- Event creation
- In-app alerts
- Firebase Cloud Messaging push notifications

Do not add:
- maps
- payments
- chat
- social profiles
- citizen reports
- advanced analytics
- multi-city support

Architecture:
- MVVM
- Repository pattern
- Jetpack Compose UI
- Supabase for backend
- Firebase Cloud Messaging for push

All changes must compile.
Do not commit secrets.
Do not hardcode API keys except public Supabase anon key if needed.
Use local.properties for local secrets.
