## **_Project 1 CIS427_**

## **Fatima Kourani & Ali Hazime**

***Commands Implemented***

**LOGIN**:  
Command needed from client in order to access all other commands

**SOLVE**:  
Returns the circumference or perimeter and area of numbers inputted  
Creates or overwrites the user text file containing the user's solutions

**LIST**:  
Returns a list of the user's solutions to the user  
Can only access solutions of the user who is logged in

**SHUTDOWN**:  
Shuts server down when "SHUTDOWN" command is received from user  
Closes all sockets and files and terminates the client and user

**LOGOUT**:  
Terminates the client and not the server

**HOW TO BUILD AND RUN PROGRAM**
1. Begin by running the Server file
2. Next, run the client file
3. Begin by using the login command once the client and server have started
4. Login by using login user password (_i.e. login root root22, login john john22_)
5. Run the solve command by typing _SOLVE -r_ or _SOLVE -c_ followed by a number and a newline
6. After the solutions are returned to you, run the LIST command to view all previous solutions
7. You may run the LIST -all command ONLY if you are logged in as the root user
8. To logout, type the LOGOUT command followed by a newline, this will terminate you
9. Rerun the server to login once more, this time as root if you would like to use the LIST -all command
10. To shutdown the server and disconnect yourself, use the SHUTDOWN command

**KNOWN PROBLEMS/BUGS**  
_No current problems/bugs are known_

**SAMPLE RUN OF ALL COMMANDS**

**_LOGIN:_**

_C:	LOGIN john john22   
S: SUCCESS_

**_FAILED LOGIN:_**

_C:	login john john21   
S: FAILURE: Please provide correct username and password. Try again._

**_SOLVE:_**

CIRCUMFERENCE:  
_C:	SOLVE -c 3   
S: Circle’s circumference is 18.84 and area is 28.25_

_C:	SOLVE -c  
S: Error: No radius found_

PERIMETER AND AREA:  
_C:	SOLVE -r 2  
S: Rectangle’s perimeter is 8 and area is 4_

_C:	SOLVE -r 5 6  
S: Rectangle’s perimeter is 22 and area is 30_

_C:	SOLVE -r  
S: Error: No sides found_


**_LIST:_**

_C:	LIST  
S: john  
radius 3: Circle’s circumference is 18.84 and area is 28.25  
sides 2 2: Rectangle’s perimeter is 8 and area is 4  
sides 5 6: Rectangle’s perimeter is 22 and area is 30  
radius 2: Circle’s circumference is 12.56 and area is 12.56  
Error: No radius found  
Error: No sides found_


_**LIST -all (not root user):**_

_C:	LIST -all  
S: Error: you are not the root user_

**_LIST -all(root user):_**

_C:	LIST -all  
S:  
root  
No interactions yet_

_john  
radius 3: Circle’s circumference is 18.84 and area is 28.25  
sides 2 2: Rectangle’s perimeter is 8 and area is 4  
sides 5 6: Rectangle’s perimeter is 22 and area is 30  
radius 2: Circle’s circumference is 12.56 and area is 12.56  
Error: No radius found
Error: No sides found_

_sally  
No interactions yet   
qiang  
No interactions yet_

**_SHUTDOWN_**

_C:	SHUTDOWN   
S: 200 OK_

_**LOGOUT**_

_S:	LOGOUT   
C: 200 OK_


