# BookWarm

QUEST LOG:
~~~~~~~~~~
Igor(newLayoutLauncher) - Create note XML activity [add new note button, delete button], displays all previously written notes, adds notes to Book object, retrieves notes from Book object.
Federico(Iostream Broadswordsman) - Implement rating bar and readstatus button in BookDetails
Jake(RogueFactory) - Implement newActivity from onClicks on the ListView objects.


NOTES FOR TEAM MEMBERS GO HERE:
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
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
