
# Fetchlt (Stock Ticker Data App)

This is a modern Android application built using **Jetpack Compose** and **Kotlin** that fetches **end-of-day stock data** for one or multiple stock tickers. The app utilizes a clean architecture approach to ensure scalability and maintainability, while integrating with a remote API to retrieve financial data.

---

## Features

✅ Fetches end-of-day data for one or multiple stock tickers  
✅ Displays stock information in a clean, modern UI using **Jetpack Compose**  
✅ Supports navigation between screens with **Navigation Compose**  
✅ Robust error handling for network failures  
✅ Well-structured codebase following **MVVM Architecture**

---

## Technologies Used

| Technology            | Purpose                                                                                  |
|--------------------|----------------------------------------------------------------------------------|
| Kotlin               | Primary programming language |
| Jetpack Compose      | Declarative UI Toolkit |
| Navigation Compose   | Managing navigation between screens |
| Retrofit             | API communication |
| Coroutine + Flow     | Asynchronous programming |
| Dependency Injection | Dagger/Hilt (if used) |
| Material3            | UI Components & Theming |
| ViewModel            | State management |

---

## Project Structure

The project follows a modular and clean structure:

```
app/
├── ui/                // Compose screens, components & themes
├── data/              // Network layer, DTOs, and Repository implementations
├── domain/            // Models, UseCases, Repository interfaces
├── di/                // Dependency Injection setup (if used)
└── navigation/        // Navigation graph & argument handling
```

---

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog (or newer)
- Minimum SDK: 24
- Kotlin 1.9+
- Internet Connection (for API fetching)

---

### Setup Instructions

- **Note:** For testing purposes, the API key is placed in a `gradle.properties` file so that you can easily run the app 
- **Important:** For production-level builds, the API key has to be stored in the `local.properties` file to ensure it remains hidden and is not checked into version control

1. **Clone the repository**  
    ```bash
    git clone https://github.com/hzdawoud/Fetchlt.git
    ```
2. **Open the project in Android Studio**

3. **Add your API key** (if required by your stock data provider)
   In `build.gradle.kts` (Module: app), or a separate `gradle.properties` file, add:
   ```properties
   API_KEY=your_api_key_here
   ```
4. **Sync Gradle**  
   Android Studio will prompt you to do this.

5. **Run the app**  
   Use the green play button in Android Studio to install and run the app on an emulator or device.

---

### API Source

This app connects to an external financial data provider (like Alpha Vantage, Twelve Data, or any similar provider). You can easily swap out the data source by modifying the `Retrofit` service implementation.

Example (using Alpha Vantage):
```kotlin
@GET("eod")
suspend fun getEndOfDayData(
    @Query("symbols") symbols: String = "AAPL,MSFT"
): Response<EodResponseDto>
```

---

## Testing

This project includes unit tests to ensure the reliability and correctness of the repository layer and its conversion of API responses into a user-friendly Resource object. The tests are written using:
- JUnit4 for the test framework
- MockK for mocking dependencies
- Kotlinx-Coroutines-Test for testing coroutines
- Robolectric (optional) to simulate an Android environment when Android-specific calls are used

---

## Architecture

This project follows a **clean MVVM architecture**:

- **UI Layer:** Composable functions and ViewModel
- **Domain Layer:** Business logic (UseCases, Models)
- **Data Layer:** Retrofit API, DTOs, and Repository implementations

---

## Error Handling

The app includes basic error handling such as:
- Network connection failures
- Invalid symbols/tickers
- API limit exceeded

---

## Future Improvements

- Add caching for offline support
- Support for multiple data sources (user can select preferred provider)
- Add charting (line charts for historical data)
- Dark mode support
