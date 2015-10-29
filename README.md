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