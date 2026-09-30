# Expense Tracker App

A modern Android Expense Tracker application built using **Jetpack Compose**, following **MVVM architecture** and using **Room Database** for local persistence.

This app helps users track expenses, monitor spending, and manage transactions with a clean and intuitive UI.

---

## Features

* Add new expenses
* View recent transactions
* Monthly total expense calculation
* Local data persistence using Room
* Reactive UI with StateFlow
* Material 3 UI design
* Jetpack Compose navigation

---

## Tech Stack

### Language

* Kotlin

### UI

* Jetpack Compose
* Material 3

### Architecture

* MVVM (Model-View-ViewModel)

### Database

* Room Database

### State Management

* StateFlow
* Coroutines

### Navigation

* Navigation Compose

---

## App Screens

### Home Screen

Displays:

* Total expenses for the month
* List of recent transactions
* Add Expense button

### Add Expense Screen

Allows users to:

* Select Title
* Enter amount
* Select expense category
* Save transaction

---

## How to Run

### Prerequisites

* Android Studio Hedgehog or later
* Kotlin 1.9+
* Android SDK 34+

### Steps

1. Clone the repository

```bash
git clone https://github.com/yourusername/expense-tracker.git
```

2. Open in Android Studio

3. Sync Gradle

4. Run the app

---

## Architecture Overview

This project follows **MVVM architecture**:

**View (Compose UI)**
Handles rendering UI and user interactions.

**ViewModel**
Manages UI state and business logic.

**Repository**
Acts as a single source of truth.

**Room Database**
Stores expense data locally.

---

## Author

Baby Varna C P

Android Developer
Kotlin | Jetpack Compose | MVVM | Room

---
