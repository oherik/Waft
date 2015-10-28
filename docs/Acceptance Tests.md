Acceptance Tests
================

**Latest revision: 2015-10-28**

This document describes high level tests that must be possible to perform in the application. There is not a one-to-one mapping between the tests and specific user stories – some tests may be related to more than one user stories, and viece versa.

**Note:** This testing relies on a prototypical version of the app, specifically for testing. When possible, the tests that include using the bus the user is currenlty on should also be performed on an actual bus, with an app version that doesn't rely on a fake bus. Due to the nature of the buses, the destination of the bus may be shown as "Ej i trafik" or simply be empty, if the particular bus has retired for the day.

View names
----------

|Name in this document   	|Canonical description       |
|---	|---    |
|Main view   	|The starting view of the application, where the user can search for stops and see arrivals to it.       |
|Search view   	|The view shown when the user presses the the search bar in the main view. Here the user can input a search string and see matching stops, and select one.   	|
|Currenct flags view   	|The view shown when the user presses the bus icon in the main view. Displays flags on current vehicle.   	|
|Add flags view |The view shown when pressing the '+' icon in the Current flags view. Here the user sees the different kinds of flags that can be sent.  |
|Add flag detail view | The view shown when pressing any flag icon in the Add flags view. Here the user can input a comment before sending. |

Tests
-----

### Search for stop

**ID:** T01

**Description:** Find a stop based on a search string

**Precondition:** Be in the main view

#### Steps
*Variation 1*

1. Press the search bar at the top of the screen.
1. Input "Chalmers, Göteborg".
1. Choose "Chalmers, Göteborg" in the list.

*Variation 2*

1. Press the search bar at the top of the screen.
1. Input "cha".
1. Choose "Chalmers, Göteborg"

#### Expected result

1. You are brought back to the main view, and see a list of vehciles scheduled for arrival to that stop.
1. The text in the search bar reads "Chalmers, Göteborg".

### Refresh the list of arriving vehicles

**ID:** T02

**Description:** Get the latest available data for vehicles on the way to a stop you have searched for.

**Precondition:** Have performed a stop search so that there is a list of arriving vehicles present in the main view. Preferrably also having waited one minute since the search was performed, so that new data is available.

#### Steps

1. Scroll to top of list.
1. Drag your finger down from top of lit to bottom.

#### Expected result

1. A refresh icon descends from behind the search bar and down over the list.
1. The refresh icon starts spinning.
1. The refresh icon disappears, and the list is updated.

### Add flag with comment to current bus

**ID:** T03.1

**Description:** Add a flag to the bus you are currently on, and write a comment that accompanies the flag.

**Precondition:** Have phone wifi turned on. Have main view open.

#### Steps 

*Variation 1*

1. Press the bus icon.
1. Press the '+' icon.
1. Press the "Gott om plats" icon.
1. Enter "Inte mycket folk idag".
1. Press "Skicka".

*Variaton 2*

Same as above, but press the "Övrigt" icon and enter "Det saknas brandsläckare".

*Variation 3*

Same as above, but press the "Övrigt" icon and enter "Hej".

#### Expected result

*Variation 1*

1. You are brought back to the view add flags view.
1. A toast appears saying "Flagga skickad"
1. At the website http://95.85.21.47/flags, at the bottom a page there is a new JSON object, with flagType: 14 and comment: "Inte mycket folk idag".

*Variation 2* 

Same as above, but with flag type 1 and "Det saknas brandsläckare" as comment.

*Variation 3*

1. A toast appears saying "Vänligen skriv in en kommentar (minst fem tecken)".

### Add flag to current bus through quick send

**ID:** T03.2

**Description:** Add a flag to the bus you are currently on by a long press on a flag icon.

**Precondition:** Have phone wifi turned on. Have main view open.

#### Steps 

*Variation 1*

1. Press the bus icon.
1. Press the '+' icon.
1. Press and hold the "Gott om plats" icon.
1. When the icon is fully filled in with dark red, release your finger.

*Variaton 2*

Same as above, but press and hold the "Övrigt" icon.

#### Expected result

*Variation 1*

1. A toast appears saying "Flagga skickad"
1. At the website http://95.85.21.47/flags, at the bottom a page there is a new JSON object, with flagType: 14 and comment: "".

*Variation 2*

1. A toast appears saying "Den här flaggan kan inte skickas utan en kommentar".

### View flags for current bus

**ID:** T04

**Description:** See what flags the vehicle you are on have been flagged with.

**Precondition:** That there exists at least one flag for the vehicle you are on, with a comment. (If none exists already, you can create one or more through either test T03.1 or T03.2.) Be in main view. Have wifi turned on.

#### Steps

1. Press bus icon.

#### Expected result

1. See the flags for current vehicle. If more than one person is using the application, you may see other flags than your own.

### Remove flags on current bus

**ID:** T05

**Description:** Delete a flag that has been posted to the vehicle you are on, either by someone else or by yourself.

**Precondition:** That there exists at least one flag for the vehicle you are on. (If none exists already, you can create one or more through either test T03.1 or T03.2.) Be in main view. Have wifi turned on.

#### Steps

1. Press bus icon.
1. Press 'x' icon on flag.

#### Expected result

1. The flag disappears from the list.
1. The flag may reappear if you switch between view, but if you press refresh in the action bar, the flag doesn't return.

### Refresh current vehicle

**ID:** T06

**Description:** Update the app to look for your current vehicle.

**Precondition:** Be in main view. Have wifi turned on.

#### Steps

1. Press the bus icon.
1. Press the refresh icon in the top right corner.
1. Press '+' icon.
1. Press the refresh icon in the top right corner.
1. Press any flag icon.
1. Press the refresh icon in the top right corner.

#### Expected result

1. Whenever you press the refresh icon, the text in the action bar changes to "Letar efter ditt fordon ...".
1. After a short while (no more than a few seconds) the text changes to line number 55 and the destination of the vehicle.

### Turn off vehicle scanning

**ID:** T07

**Description:** Stop vehicle scanning by turning off phone wifi

**Precondition:** Be in main view. Have wifi turned on.

#### Steps

1. Press bus icon.
1. Turn off phone wifi.

#### Expected results

1. The text in the action bar changes to "Aktivera wifi".
1. The refresh icon in the action bar changes to a crossed out wifi icon.

### Turn on vehicle scanning from action bar

**ID:** T08.1

**Description:** Start vehicle scanning by turning on phone wifi.

**Precondition:** Be in main view. Have wifi turned off.

#### Steps

1. Press the bus icon.
1. Press the crossed out wifi icon.

#### Expected result

1. Text in action bar changes to "Letar efter ditt fordon" and the crossed out wifi icon changes to a refresh icon.
1. After no more than a few seconds, the text in the action bar changes to line number 55 and a destination.
1. The phone's wifi is activated.

### Turn on vehicle scanning when sending flag

**ID:** T08.2

**Description:** Start vehicle scanning by turning on phone wifi when sending a flag.

**Precondition:** Be in main view. Have wifi turned off.

#### Steps

1. Press bus icon.
1. Press '+' icon.
1. Press "Full" flag icon.
1. Press "Skicka".
1. Press "Slå på WiFi" in the dialog.
1. When action bar shows current bus, press send again.

#### Expected result

1. Phone wifi is turned on.
1. At the website http://95.85.21.47/flags, at the bottom a page there is a new JSON object, with flagType: 2 and comment: "".

### Navigate through application with back buttons

**ID:** T09

**Description:** Use back button in app as well as hardware back button to navigate through the app.

**Precondition:** Be in main view.

#### Steps

*Variation 1*

1. Press the bus icon.
1. Press the '+' icon.
1. Press any flag icon.
1. Press the hardware back button 4 times.

*Variation 2*

1. Same as above, but press the back button in the top left corner instead of hardware button, and only press 3 times.

*Variation 3*
1. Press the search bar.
1. Press the hardware back button 2 times.

#### Expected result

*Variation 1*

1. At first press at back button, keyboard disappears.
1. Second press, you are returned to the add flags view.
1. Third press, you are returned to the flags on current bus view.
1. Fourth press, you are returned to the main view.

*Variation 2*

The first two events described above happen at the first press. Consecutive presses have the same effect as described above.

*Variation 3*

1. At first press, keyboard disappears.
1. At second press, you are returned to the main view.

******

User stories
------------

These are the user stories that relate to acceptance functional accepance tests. Some user stories concern non-functional requirements, and thus are not included here.

|User story | Associated tests |
|-----------| ---------------- |
|**From product backlog, by product owner** | |
|As a traveler, I want to tell others about problems with or on my vehicle by flagging them, so that I can help my fellow travelers. | T03.1, T06, T07|
|As a traveler I want to be able to view information regarding the vehicles heading towards my stop, so that I can see if they have been flagged with any issues. |T01 |
|As a traveler, I want to be able to send flags with a single press, so that I with ease can let travelers know of issues on my bus. | T03.2 |
| As a traveller, I want to be able to refresh the list of arriving vehicles, so that I can se the latest data without having to perform a new search | T02 |
| As a traveler I want to be able to remove flags that I find incorrect, so that others know the issue reported is no longer (or was never) there. | T04, T05 |
|**From sprint backlog, by developers**. | |
|User can add flag to current bus | T03.1 |
|User can put comment on flag | T03.1 |
|User can quick send flag | T03.2 |
|User can search for stops | T01 |
|User can refresh vehicle list by pulling it down| T02 |
|User can see what bus they are on through autodetection | T06, T07, T08.1 |
|User can refresh current vehicle status | T06|
|User can see when vehicle scanning is turned off because there is no wifi | T07 |
|User can turn vehicle scanning on through app | T08.1-T08.2 |
|User can turn vehcile scanning on when sending fails because scanning is impossible | T08.2
|User can send flags with single press | T03.2 |
|User can view flags on current vehicle | T04 |
|User can remove flag from current vehicle | T05 |
|User can use hardware button to navigate backwards through app | T09 |
|User can use back button in action bar to navigate backwards through parts of app | T09 |
