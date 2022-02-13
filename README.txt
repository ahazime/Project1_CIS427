Github repo:https://github.com/ahazime/Project1_CIS427.git
Project 1 CIS427
Fatima Kourani & Ali Hazime
Commands Implemented

LOGIN:
Command needed from client in order to access all other commands

SOLVE:
Returns the circumference or perimeter and area of numbers inputted
Creates or overwrites the user text file containing the user's solutions

LIST:
Returns a list of the user's solutions to the user
Can only access solutions of the user who is logged in

SHUTDOWN:
Shuts server down when "SHUTDOWN" command is received from user
Closes all sockets and files and terminates the client and user

LOGOUT:
Terminates the client and not the server

HOW TO BUILD AND RUN PROGRAM

Begin by running the Server file
Next, run the client file
Begin by using the login command once the client and server have started
Login by using login user password (i.e. login root root22, login john john22)
Run the solve command by typing SOLVE -r or SOLVE -c followed by a number and a newline
After the solutions are returned to you, run the LIST command to view all previous solutions
You may run the LIST -all command ONLY if you are logged in as the root user
To logout, type the LOGOUT command followed by a newline, this will terminate you
Rerun the server to login once more, this time as root if you would like to use the LIST -all command
To shutdown the server and disconnect yourself, use the SHUTDOWN command
KNOWN PROBLEMS/BUGS
No current problems/bugs are known

SAMPLE RUN OF ALL COMMANDS

LOGIN:

C: LOGIN john john22
S: SUCCESS

FAILED LOGIN:

C: login john john21
S: FAILURE: Please provide correct username and password. Try again.

SOLVE:

CIRCUMFERENCE:
C: SOLVE -c 3
S: Circle’s circumference is 18.84 and area is 28.25

C: SOLVE -c
S: Error: No radius found

PERIMETER AND AREA:
C: SOLVE -r 2
S: Rectangle’s perimeter is 8 and area is 4

C: SOLVE -r 5 6
S: Rectangle’s perimeter is 22 and area is 30

C: SOLVE -r
S: Error: No sides found

LIST:

C: LIST
S: john
radius 3: Circle’s circumference is 18.84 and area is 28.25
sides 2 2: Rectangle’s perimeter is 8 and area is 4
sides 5 6: Rectangle’s perimeter is 22 and area is 30
radius 2: Circle’s circumference is 12.56 and area is 12.56
Error: No radius found
Error: No sides found

LIST -all (not root user):

C: LIST -all
S: Error: you are not the root user

LIST -all(root user):

C: LIST -all
S:
root
No interactions yet

john
radius 3: Circle’s circumference is 18.84 and area is 28.25
sides 2 2: Rectangle’s perimeter is 8 and area is 4
sides 5 6: Rectangle’s perimeter is 22 and area is 30
radius 2: Circle’s circumference is 12.56 and area is 12.56
Error: No radius found Error: No sides found

sally
No interactions yet
qiang
No interactions yet

SHUTDOWN

C: SHUTDOWN
S: 200 OK

LOGOUT

S: LOGOUT
C: 200 OK