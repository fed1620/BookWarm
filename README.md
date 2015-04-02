# BookWarm

QUEST LOG:
~~~~~~~~~~
Igor(newLayoutLauncher) - Library sorting
                        - Sort by: Title
                        - Sort by: Author
                        - Sort by: Favorite status
                        - Test on Nexus device!

Federico(Iostream Broadswordsman) - Create Library method to return array of bools (Favorite status)
                                  - Create Edit Note Activity
                                  - Favorite filter tab

Jake(RogueFactory) - Get a more detailed / hi-res book icon
                   - BookWarm icon!
                   - isFavorite icon
                   - isRead visual indication
~~~~~~~~~~~

NOTES FOR TEAM MEMBERS GO HERE:
-------------------------------
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
