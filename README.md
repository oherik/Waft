# Waft

## Getting Started

### Clone the repo
```
git clone git@github.com:oherik/DAT255.git
```

### Get the associated server
```
git submodule init
```
followed by:
```
git submodule update
```

### Configuration

The URL to the server and all API keys are declared in the Config class in the package `package com.alive_n_clickin.waft.infrastructure.api`. It is recommended to use the server that is running over at Digital Ocean, but theoretically you should be able to run a local copy of it.

## Installing the APK

To install the APK, run the following command while in the repository root folder:

```
adb install app-release.apk
```

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
