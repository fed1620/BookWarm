# BookWarm

QUEST LOG:
~~~~~~~~~~
Igor(newLayoutLauncher) - Page number is ignored if 0
                          Back Button should take the user from ListNoteActivity to BookDetails instead of AddNote
                          Activity names in-app are user friendly
                          Make notes persistent

Federico(Iostream Broadswordsman) - sql SQL SQLLLLLLLLL

Jake(RogueFactory) - Fun with Google Books.

NOTES FOR TEAM MEMBERS GO HERE:
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

3/4/15

Always make sure that each activity has a .java file associated with it! For clarity, make sure
that the names of the files match in the following way:

    NewActivity.java        // Implementation
    activity_new.xml        // Layout

    (Example):
    AddBookActivity.java
    activity_add_book.xml

Appending "Activity" onto the end of the .java file will help us differentiate between the
activity classes and the regular classes.

    - Federico

---------------------------------------------------------------------------------------------------
2/27/15

IMPORTANT: Before committing any new code, first run the app and make sure
that everything is working as intended!

    - Federico

---------------------------------------------------------------------------------------------------
2/25/15

Created a new configuration "Test" that we will use to run all of our tests.

To the left of the green RUN arrow, there is a drop-down menu that will either 
let you choose to run the entire "app," or if you select "Test," it will only 
run everything contained in our test package (edu.byui.cs246.bookwarm.test).

Whenever you build test classes, right click on the "test" package:

[app > build > src > androidTest > java > edu.byui.cs246.bookwarm > test]

and press: New -> Java Class


** It's important that all testing programs are created within this package, 
otherwise they will be ignored when we run the "Test" configuration!

    - Federico
---------------------------------------------------------------------------------------------------

Just heard about xstream - its a library that allows you to pass in an object (even custom objects) and stores it as an XML file. Might make things pretty easy on us for file formatting.
-Jake
