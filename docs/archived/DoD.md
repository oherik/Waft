General Definition of Done
==========================

Documentation
-------------

All classes should be documented except test classes where it's optional. Things that should be there:

* A brief class description.
* Javadoc for public metods and fields. This should simply describe the contract of the method: What does it need, and what will it do.
* A Markdown document describing any *module* consisting of a couple of classes, that has specific purpose.



Automated Testing
-----------------

Infrastructure Layer: Data storage and manipulation should be tested, with edge cases. No data should be lost.

Domain Layer: Businiess logic should be tested, especially with edge cases. The tests should be written as standard Android tests. 

Application Layer: Controllers should be tested, to show that they work. Edge cases are nice, but not necessary.

Presentation Layer: GUI does not need to be tested, but can be, if it makes sense to the implementor.

Manual Testing
--------------

All GUI elements should be tested manually, by manipulating them in any way they allow manipulation. For example, list items should be pressed, pressed and held, dragged, swiped left and right, etc. Behavior must be what is desired.
