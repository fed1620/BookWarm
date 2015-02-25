# BookWarm

NOTES FOR TEAM MEMBERS GO HERE:
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
Just heard about xstream - its a library that allows you to pass in an object (even custom objects) and stores it as an XML file. Might make things pretty easy on us for file formatting.
-Jake

WOO! ListView now displays a book and a title. (Had to make a custom list class) Anyway, Federico, if you need to add books to it, check out the two class variables (String[] titles and Integer[] imageId) in MainActivity.java. You'll have to create functions to interface with those.
-Jake

---------------------------------------------------------------------------------------------------
Created a new configuration "Test" that we will use to run all of our tests.

To the left of the green RUN arrow, there is a drop-down menu that will either let you choose to
run the entire "app," or if you select "Test," it will only run everything contained in our test
package (edu.byui.cs246.bookwarm.test).

Whenever you build test classes, right click on the "test" package:

[app > build > src > androidTest > java > edu.byui.cs246.bookwarm > test]

and press: New -> Java Class


** It's important that all testing programs are created within this package, otherwise they will be
ignored when we run the "Test" configuration!

    - Federico
---------------------------------------------------------------------------------------------------