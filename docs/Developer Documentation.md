Developer Documentation for Waft
================================

##Table of Contents

###[1 Introduction](#inctroduction)

####[1.1 Design goals](#designgoals)

####[1.2 Definitions, acronyms and abbreviations](#definitions)

###[2 System design](#systemdesign)

####[2.1 Overview](#overview)

####[2.2 Software decomposition](#softwaredecomposition)

<a name="introduction">1. Introduction</a>
---------------

Waft is an Android application, built for SDK version 23 and up. The purpose is to enable commuters to share information regarding public transport. This is the combined with other real time data, like that which can be found in most travel planners.

To run Waft, download and install the .apk file in the repo. Note that not all releases have pre built .apk files. For those that do not, you will need to perform your own build.

### <a name="designgoals">1.1 Design goals</a>

The software is designed in such a way as to allow easy detatchment and rebuilding of the GUI. Loose coupling is a major design goal. Only relying on interfaces of other classes, not their implementation, is another. (This is sometimes called *Dependency Inversion Principle*, or *DIP*.) These are the overriding principles of the entire project architecture.

*Note: Even though the software follows the principle of relying only on interfaces of other classes, the user interface might not. The user may be given relevant information about the system state, if this improves the user experience. However, this knowledge is separated from the main code base, and kept in separate string resources.*

### <a name="definitions">1.2 Definitions, acronyms and abbreviations</a>

* **APK**: Android Applicaion Package. The package archive that is installed on an Android device that creates an app. The APK contains all necessary resources, code and assets for the application. The filenames end with `.apk`.
* **Flag**: A user generated piece of information associated with a vehicle. These are stored on the server and displayed to the user.
* **DIP**: Dependency Inversion Principle, a software pattern. Dictates that high level modules should only depend on abstractions of low level modules, and details should depend on abstractions. Could be briefly stated as "program towards interfaces, not implementation".

<a name="systemdesign">2. System Design</a>
-------------------------------------------

### <a name="overview">2.1 Overview</a>

The system consists of two parts: the Android application, and the server. The server stores flags and makes it possible to create an interface for the data collectected through the app. 

### <a name="androidapplication">2.2 Android application</a>

The app software in are ordered according to the [Layered Architecture] pattern, which is a modified form of MVC. This orders packages hierarchically, meaning that higher level packages may have knowledge of lower levels, but not vice versa. Lower levels communicate with the higher ones through the Observer pattern (Gamma, Helm, Johnson and Vlissides, 1995). 

We use the following hierarchy, in the descending order.

1. Presentation (or View)
1. Application (or Controller)
1. Infrastructure
1. Domain (or Model)

The first and second layers roughly correlate to MVC's View and Controller parts, and infrastructure together with model correlate with MVC's Model part. Infrastructure is for handling low level tasks such as connecting to databases and API:s, and persistent storage. The infrastructure layer allows the other layers to interact with lowere level details through abstractions, keeping the code higher up in the hierarchy pristine and focused.

For a more detailed description of layered architecture, see [Evans (2004, p. 68-75)](#evans2004). His approach uses the reversed order for the domain and infrastructure layers, which is the standard approach. However, since this application uses a very simple model and a very advanced infrastructure, the order stated above is more suitable for this project.

There is also a `util` package. This is a catch all package for classes that are generally handy to have, but don't have a specified place within the rest of the architecture. It holds classes that help with event handling, and other miscellaneous tools. The event classes may have some knowlegde of the system, such as of domain obects, but not much else.

#### 2.2.1 Presentation

The presentation is made up several kinds of files, and not all are contained in the presentation Java package. 

The look and feel of the views are defined in xml, as per Android standard. Layouts, images, strings and all other such view resources are found in the `res` folder.

The behavior of the views are defined in the Java files of the presentation package. 

The package consists of three submodules. Read more under [Software Decomposition](#presentationdecomposition)

#### 2.2.2 Application

Here you will find classes that handle complex behavior which may require utilizing both the domain and the infrastructure. These classes help the presentation by creating an abstraction for these complex behaviors. 

For example, the `Manager` class can help with most anything that relates to modifying and getting information about vehicles. It lets the presentation use methods such as `addFlagToCurrentBus` and `getVehicles`, and orchestrates the necessary behavior by delegating it to the domain and the infrastructure.

#### 2.2.3 Domain

The domain model in Waft is rather thin, since the business logic is simple and straightforward. The domain layer contains classes representing bus stops, vehicles, and flags.

All the different kinds of objects in the domain are pure value objects, meaning they are immutable, and can be shared freely without fear of unwanted state changes.

Vehicles are modeled in a few different ways. All different types implement the IVehicle interface and extend AbstractVehicle. There are then two implementing classes: ElectricityBus and ArrivingVehicle. Each of these have their own interface. One represents vehicles approaching a stop and has an arrival time, and the other an ElectricityBus, which has a vehicle identification number.

A Flag object has the information necessary to store and modify it: It's type, a comment (which may be empty), an id and a time of creation. Flags can be created for the purpose of being sent, and can also be created from API responses.

### 2.3 Server

The server runs on Node.js and uses the node module *Restify* to handle API requests. The server's purpose is to handle the change and retrieval of flags. It does so by storing the flags and relevant information connected to the flags in a MongoDB database, hosted remotely, on MongoLabs.

The API supports GET, POST, and DELETE requests.

### <a name="softwaredecomposition">2.4 Software decomposition</a>

#### 2.4.1 General

The Android application is composed of the layers detailed [above](#andoidapplication). A few of these are furhter composed of submodules.

#### <a name="presentationdecomposition">2.4.2 Presentation</a>

The packages in the Presentation layer are ordered into submodules accordin to which activity they relate to. There are two activities in the app, one for searching for vehicles, and one for adding flags to vehicles.

##### 2.4.2.1 Arrival list

These files control the behavior of the start view, for viewing arriving vehicles and their flags.

From here the user can navigate to searching or to flagging vehicles.

These files also control the behavior of the search view, where the user can input text and get suggested stops based on that text. It passes the information of which stop has been requested on to the main view, and sends the user back to this view.

##### 2.4.2.2 Flagreport

These files control the behavior of the views that create and manipulate the flags for the current vehicle. These are navigated in the following order:

FlagsOnBusFragment > FlagVehicleFragment > FlagVehichleDetailFragment

The first shows the user the flags on their vehicle, and lets them delete these flags. The second shows a list of flags that can be posted, and lets the user perform a quick send by doing a long press on any of the icons. The third one lets the user post a comment along with the flag.

##### 2.4.3 Application

###### 2.4.3.1 Api

##### 2.4.4 Infrastructure

###### 2.4.4.1 Api

###### 2.4.4.1 Wifi

#### 2.4.5 Server

See the [documentation within the server repository](https://github.com/Oscmage/DAT255_server/tree/master/docs)

### 2.5 Persistent data storage

The only data that is stored persistently is the flags. These are stored on the server, which can be manipulated via a RESTful API. The flags are stored in a MongoDB document database. There are no relations. Here is what a flag looks like, in JSON format:

        {
            "_id": "562e3bcfed6c36266108b763",
            "flagType": 14,
            "comment": "foobar",
            "journeyID": "9015014505500098",
            "dgw": "Ericsson$171330",
            "time": "Mon Oct 26 15:42:22 CET 2015",
            "__v": 0
        }

The `_id` field is the key of the tuple. `flagType` is which of all available flags have been posted. `comment` is a string entered by the user to post with the flag. `journeyID` connects the flag to a journey, as specified by the Västtrafik API. `dgw` is the vehicle id assigned by the Electricity API.

### 2.6 Access control and security

#### 2.6.1 Issues
Being in a prototyping stage, there are some security considerations to take into account when using and viewing the application. These should ideally be addressed, but are deemed to not pose any major security risks. A fully deployed project should address these issues.

#### 2.6.1.1 Issus Server

The server uses the standard HTTP protocol, and does not require authentication. Any application can send as many requests as they want.

#### 2.6.1.2 API Connections

The authorization for the Västtrafik and ElectriCity API are hardcoded into Java files.

### 2.7 External dependencies

All external dependencies for the Android Application are defined in the Gradle build file for the application.

#### 2.7.1 Android Application dependencies

##### 2.7.1.1 Android SDK

##### 2.7.1.2 GSON

##### 2.7.1.3 Mockito

##### 2.7.1.4 JUnit

##### 2.7.1.5 Lombok

#### 2.7.2 Server dependencies

##### 2.7.2.1 Node.js

##### 2.7.2.2 Restify

##### 2.7.2.3 MongoDB

References
----------

1. <a name="evans2004">Layered Architecture: Evans, E. (2004). Domain-Driven Design: Tackling Complexity in the Heart of Software. Prentice Hall.</a>
1. <a name="gamma1995">Design patterns: Gamma, E., Helm, R., Johnson, R. och Vlissides, J. (1995). Design Patterns: Elements of Reusable Object-Oriented Software. Addison-Wesley.</a>
