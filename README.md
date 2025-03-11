# Dictionary App

## Overview
This is a personalized dictionary app designed for an engaging and educational word discovery experience. It allows users to look up definitions, receive word suggestions, save favorite words, and view them in a saved list. The app is built using Jetpack Compose for the UI and follows the MVVM architecture.

<img width="381" alt="Screenshot 2025-02-20 at 11 53 23 PM" src="https://github.com/user-attachments/assets/54d3ad11-9f12-425f-ae7a-fff131e32544" />
<img width="381" alt="Screenshot 2025-02-20 at 11 58 50 PM" src="https://github.com/user-attachments/assets/d32f060f-0aad-48ba-ba5f-acde61dff875" />
<img width="381" alt="Screenshot 2025-02-20 at 11 59 59 PM" src="https://github.com/user-attachments/assets/debc56ff-a89e-409f-9531-e7d718492c44" />


## Features
- **Search & Definitions**: Users can search for words and view their definitions.
- **Word Aggregation**: Words are grouped based on their starting characters.
- **Saved Words**: Users can save words and revisit them later.
- **Word Suggestions**: The app provides word suggestions as users type.

## Future Enhancements
- Implement AI-generated example sentences and interactive word challenges.
- Add themed word lists (college vocabulary, business vocabulary).
- Implement speech synthesis for pronunciation.
- Enhance gamification with daily word challenges.

## Tech Stack
- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **UI**: Jetpack Compose
- **Networking**: Retrofit + Moshi
- **State Management**: Flows, LiveData, Coroutines
- **Database**: Room with Hilt for dependency injection


## Setup & Installation
1. Clone the repository:
   ```sh
   git clone https://github.com/rosannanaomy/dictionary-app.git
   ```
2. Navigate to the project directory:
   ```sh
   cd dictionary-app
   ```
3. Open the project in Android Studio.
4. Ensure you have the latest Android SDK installed.
5. Sync the project with Gradle files.
6. Build and run the project on an emulator or physical device.

## Using the Repository
To use this repository, you must provide your own **OpenAI API key**. This key is required for generating AI-powered word suggestions and other related features.

### **Steps to Set Up API Keys**
1. Navigate to your project's root directory.
2. Open or create a file named `local.properties`.
3. Add the following line:
   ```
   API_KEY=your_openai_api_key
   ```
4. Save the file. **Do not commit `local.properties`** to GitHub for security reasons.

## API Usage
This app integrates with an external dictionary API and OpenAI for generating AI-powered responses. The app fetches:
- **Word Definitions**: From an external dictionary API.
- **AI-Generated Word Suggestions**: Using OpenAI’s API.

To ensure smooth functionality, store API keys securely in `local.properties`:
```
API_KEY=your_openai_api_key
```
