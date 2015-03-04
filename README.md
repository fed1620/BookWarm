# BookWarm

QUEST LOG:
~~~~~~~~~~
Igor(newLayoutLauncher) - Make sure each note activity has a skeleton java file
to go with it. Create a separate "Add Note Activity" with a large text box for inputting the note
content, a smaller EditText for an optional page number, and a button to create the note. 
Create a java file for that activity that will pass a Note object (via intent.putExtra()) back to NoteDetailsActivity (which
Jake will be creating) when the Create Note button is pressed.

Federico(Iostream Broadswordsman) - Remove the menu button we created, and switch it's functionality to the activity bar in
MainActivity

Jake(RogueFactory) - Implement the java for NoteDetailsActivity. (Possible custom adapter needed)

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
