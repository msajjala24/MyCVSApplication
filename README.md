# Snap Image Search App

This is an Android application that fetches images from a public API based on user input (tags).
The app allows users to search for images and displays the results in a list. It also handles
various states like loading, success, and error.

## Features

- Search images by tag
- Display images in a list view
- Handle loading and error states
- Use of **MVVM** architecture
- **Hilt** for Dependency Injection
- **Kotlin Coroutines** for asynchronous operations
- Unit tests with **JUnit** and **Mockito**

## Technologies Used

- **Kotlin** - Programming language
- **Jetpack Compose** - UI framework
- **ViewModel** & **LiveData** - Android architecture components for managing UI-related data
- **Hilt** - Dependency injection
- **Coroutines** - Asynchronous programming
- **Retrofit** - API interaction
- **Mockito** & **JUnit** - Unit testing

## Getting Started

### Prerequisites

Ensure you have the following installed:

- Android Studio
- JDK 11 or later

### Build and Run

1. Open the project in **Android Studio**.
2. Click on `Build > Rebuild Project` to make sure all dependencies are downloaded.
3. Run the app on an emulator or physical device.

## Architecture

This project follows **MVVM (Model-View-ViewModel)** architecture to separate the concerns and
ensure testability:

- **Model**: Contains data and the business logic. The `SnapRepositoryImpl` interacts with the API
  to fetch images.
- **View**: Displays the UI using **Jetpack Compose**.
- **ViewModel**: Manages the data for the view and handles UI state using **LiveData**. It calls the
  `SnapRepository` to fetch data and handles loading, success, and error states.

## UI States

The app uses an `UiState` sealed class to represent different states of the UI:

```kotlin
sealed class UiState<out T> {
    object None : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
```

### Usage

- **Search**: The user enters a tag in the search bar, and the app fetches relevant images from the
  API.
- **Loading**: When the API call is in progress, the UI will display a loading indicator.
- **Success**: Once the data is fetched successfully, the images are displayed in a list.
- **Error**: If the API call fails, an error message is shown.

## Dependency Injection

This project uses **Hilt** for dependency injection. The `SnapRepositoryImpl` class is injected into
the `SnapViewModel` to manage API calls.

## Unit Testing

The project has unit tests for the repository and ViewModel layer using **JUnit** and **Mockito**.
Tests ensure the correctness of the app logic by mocking the API responses and validating the UI
state transitions.

### Run Unit Tests

To run the tests:

1. Open the project in **Android Studio**.
2. Click on `Run > Run Tests` or use the `./gradlew test` command in the terminal.

## Folder Structure

```plaintext
├── app
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── com
│   │   │   │       └── cvs
│   │   │   │           └── tagsnap
│   │   │   │               ├── data
│   │   │   │               │   ├── repository
│   │   │   │               │   │   └── SnapRepositoryImpl.kt
│   │   │   │               ├── model
│   │   │   │               │   └── SnapImage.kt
│   │   │   │               ├── network
│   │   │   │               │   └── TapSnapApi.kt
│   │   │   │               └── ui
│   │   │   │                   ├── SnapScreen.kt
│   │   │   │                   └── SnapViewModel.kt
│   │   │   └── resources
│   │   ├── test
│   │   │   └── java
│   │   │       └── com
│   │   │           └── cvs
│   │   │               └── tagsnap
│   │   │                   ├── repository
│   │   │                   │   └── SnapRepositoryImplTest.kt
│   │   │                   └── viewmodel
│   │   │                       └── SnapViewModelTest.kt
```

### Description of Key Files:

- `SnapRepositoryImpl.kt`: The repository class that fetches images from the API.
- `SnapViewModel.kt`: ViewModel that interacts with the repository and manages UI state.
- `SnapScreen.kt`: Composable UI that displays the images.
- `SnapRepositoryImplTest.kt`: Unit tests for the repository.
- `SnapViewModelTest.kt`: Unit tests for the ViewModel.

## API

The app uses the `SnapApi` to interact with a public image API.
The `fetchImages` method is called to retrieve images based on the user's search tag.

### Example API Response:

```json
{
  "items": [
    {
      "id": "1",
      "title": "Image 1",
      "url": "http://example.com/1"
    },
    {
      "id": "2",
      "title": "Image 2",
      "url": "http://example.com/2"
    }
  ]
}
```