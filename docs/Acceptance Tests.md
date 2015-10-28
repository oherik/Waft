Acceptance Tests
================

**Latest revision: 2015-10-28**

This document describes high level tests that must be possible to perform in the application. There is not a one-to-one mapping between the tests and specific user stories – some tests may be related to more than one user stories, and viece versa.

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

*Expected result*

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

1. Press the bus icon.
1. Press the '+' icon.
1. Scroll to the bottom of list and press "Övrigt" icon.
1. Enter "Det saknas brandsläckare"
1. Press send.

*Variation 3*

1. Press the bus icon.
1. Press the '+' icon.
1. Scroll to the bottom of list and press "Övrigt" icon.
1. Enter "Hej".
1. Press send.

#### Expected result

*Variation 1*

1. You are brought back to the view add flags view.
1. A toast appears saying "Flagga skickad"

*Variation 2* 

Same as above.

*Variation 3*

1. A toast appears saying "Vänligen skriv in en kommentar (minst fem tecken)".

### View flags for current bus

**ID:** 

**Description:** See what flags the vehicle you are on have been flagged with.

**Precondition:** 

#### Steps

### Remove flags on current bus

### Refresh current vehicle

### Turn off vehicle scanning

### Turn on vehicle scanning

### Navigate through application with back buttons
