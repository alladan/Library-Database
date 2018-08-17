This program is fairly simple to use, but does require some basic knowledge
or intuition to run.

Library Database Program tested and ran on Windows10, Eclipse SE 1.8, JavaFX.
MySQL and Connector-J libraries the latest versions as of 5/2/17.

Database Setup Screen
~~~
At program start, you will see a small screen.
It will indicate no connection, so you should connect.
Select your JDBC Driver, Database URL, and enter the Username/Password.

The Username and Password you enter will be based on whatever accounts you have available.
It should, at minimum, have access to read, and preferably write. A root or admin account
would work the best.

When you're finished entering the information, you may connect to the Database that you have
set up locally, or over a network. If you opt not to do this first, you will not be able
to create the preset library book database tables, and if you close the DB setup and go to
the next screen, you will find the View Database button disabled. All other buttons will also
not have any use without a Database Connection and relevant table setup.

When connected to the database, you will be able to Create Tables if you so choose - however,
if you choose not to, and still have the applicable "bookslibrary" database, you can still
run the program - it will save information to the database in-between uses, unless you reset it
with a script, sql commands, or re-create tables.

When done with everything, you can close the setup screen to enter the Library Control Screen.

Library Control Screen
~~~
You will see here quite a few buttons as well as textboxes to input things into.

Starting from the left, we have the Search by ISBN screen.
Typing in an applicable ISBN will return the title, author, and all available books, currently checked
in or out of the library database system.

In the middle, you find two buttons: One, View Database, which allows you to view the setup Database once
a connection and tables have been made. You will not be able to use this button unless you have setup
a new Database using the Create Table button on the DB Setup Screen.

The second button, Search by UserID, allows you to search a User's ID in order to obtain information,
namely Name and Address - listed in the system.

On the right, at the very top, a button to return to the Database Setup screen.
Lower, a place to enter a UID (userID), BID (bookID), or both.
To check out a book, you must enter a valid UID and BID - same to return a book.
To simply check all rentals by a user, enter the UID and click the button.
To check the status of a book by BID, enter the BID and click the button.

All output for this part of the screen, as well as the View Database button as well as some messages will be
displayed in the output console on the bottom of the screen.

Quick-Start Guide/Checklist
~~~
Make sure you have a working MYSQL Database Setup, with a user account preferably with superuser or root access.
Running the included bookslibrary.sql script ahead of time before the program may be required, depending on
whether you're able to connect to the bookslibrary database without its existence in your system prior.

1. Select your JDBC Driver and DB URL.
2. Enter your Username/Password.
3. Connect to the Database.
4. Create Tables.
5. Close Setup.
6. View Database to see successful setup of tables and database.
7. Utilize all the buttons to your leisure, reading the output messages where applicable.
8. See above for more detailed information.