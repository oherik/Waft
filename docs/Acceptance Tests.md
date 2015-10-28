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

### View flags for current bus

**ID:**

**Description:**

**Precondition:**

#### Steps

### Add flag with comment to current bus

### Add flag without comment to current bus

### Refresh current vehicle

### Turn off vehicle scanning

### Turn on vehicle scanning

### Navigate through application with back buttons

### Remove flags on current bus

### Refresh the list of arriving vehicles

