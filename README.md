# Waft

Waft is an Android application with the purpose to enable commuters to share information with each other regarding public transport. This goal is to give commuters more information about what's happening on buses, trams and trains, and also to be able to gather data about how the commuters experience public transport.

The application consists of two parts - a server written in Node.js, and an application written in Java that requires an Android SDK version of 21 or higher. The server stores data that is generated in the application, and enables other instances of the application to use it.

All external documentation is found in the `docs/` folder.

## Getting Started

### Clone the repo
```
git clone --recursive git@github.com:hjorthjort/Waft.git
```

### Configuration

The URL to the server and all API keys are declared in the Config class in the package `package com.alive_n_clickin.waft.infrastructure.api`. Add your own API keys in the appropriate places.

It is recommended to use the server that is running over at Digital Ocean, but theoretically you should be able to run a local copy of it.

## Installing the APK

To install the APK on a phone, run the following command while in the repository root folder:

```
adb install app-release.apk
```

Note that the app requires a minimum SDK level of 21 to be run.

## Running tests

#### To run the test you need to export your sdk location by typing:

```
export ANDROID_HOME='<Path to Android SDK>'
```

#### Run tests

```
./gradlew test
```

See the result by going to the directory "/app/build/reports/tests/debug/index.html".

#### Run all checks

```
./gradlew check
```

This generates result files for the lint checks and FindBugs. You can find them in the directory "/app/build/outputs/".

## Dependencies

* Java 7 SE development environment
* Android SDK
* A (virtual) Android device
* Android SDK targets

## Using the application

The application uses WiFi to determine if you're on a bus or not. Currently if you're not a bus, the app defaults to the testing bus that is provided by the ElectriCity Innovation Platform. This is to make testing easier, and it should not be present in a publicly released version of the app.

## SDK targets

* Minimum SDK: 21
* Target SDK: 23 

## Team

* Oscar Evertsson
* Sebastian Sandberg
* Mats Högberg
* Rikard Hjort
* Erik Öhrn

Built at Chalmers, 2015.
