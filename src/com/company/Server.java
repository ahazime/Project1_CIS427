//Project 1 Ali Hazime Fatima Kourani
package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int SERVER_PORT = 1619;

    public static void main(String args[]) throws Exception {
        //server function call
        createCommunicationLoop();

    }//end main


    static boolean isLogged =  false;

    public static void createCommunicationLoop() {
        try {

            BufferedReader reader;

            reader = new BufferedReader(new FileReader("logins.txt"));
            String line = reader.readLine();
            List<String> users = new ArrayList<String>();
            List<String> passwords = new ArrayList<String>();
            String[] message;
            while (line != null) {

                // lists user and pass
                users.add(line.split(" ")[0]);
                passwords.add(line.split(" ")[1]);
                line = reader.readLine();

            }//end loop
            reader.close();

            System.out.println(users.size());

            //creates server socket
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);

            System.out.println("Server Started");

            boolean quit_flag;
            boolean user_flag, pass_flag;
            String currentUser=null;
            int x=0,y=0;

            //keeps the server open for new clients
            while(!serverSocket.isClosed()){
                quit_flag=false;
                //client socket
                System.out.println("Listening for new client...");
                Socket socket = serverSocket.accept();
                System.out.println("New client connected!");

                DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
                DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());


                //Initial communication loop
                while(!quit_flag){

                    String strReceived = inputFromClient.readUTF();
                    message = strReceived.split(" ",3);

                    //login conditional check
                    if(message[0].equalsIgnoreCase("LOGIN")) {
                        user_flag = false;

                        if(message.length < 3){
                            System.out.println("Client failed login...");
                            outputToClient.writeUTF("Please provide a username and password when logging in.");
                        }
                        else{
                            for(int i=0; i<users.size();i++){
                                if(message[1].equalsIgnoreCase(users.get(i)) && message[2].equalsIgnoreCase(passwords.get(i))){
                                    user_flag = true;
                                    currentUser = message[1];
                                }

                                if(user_flag){
                                    isLogged = true;
                                }
                            }
                            if(isLogged) {
                                System.out.println("Client "+ currentUser +" is Logged On...");
                                outputToClient.writeUTF("SUCCESS: logged in as "+ currentUser);
                            }
                            else{
                                System.out.println("Client failed login...");
                                outputToClient.writeUTF("Incorrect login info, please try again.");
                            }
                        }

                    }//end login if statement
                    else {
                        System.out.println("Client not logged in");
                        outputToClient.writeUTF("Error." + "Please log in first.");
                    }

                    //client must log in before accessing other commands
                    //begin log in loop
                    while(isLogged == true) {

                        strReceived = inputFromClient.readUTF();
                        message = strReceived.split(" ",4);

                        if(message[0].equalsIgnoreCase("LOGOUT")) {
                            //disconnects client but keeps server running
                            System.out.println("200 OK");
                            isLogged = false;
                            quit_flag=true;
                            socket.close();
                            break;
                        }
                        else if(message[0].equalsIgnoreCase("quit")) {
                            System.out.println("Shutting down server...");
                            outputToClient.writeUTF("Shutting down server");
                            quit_flag = true;
                            serverSocket.close();
                            socket.close();
                            break;
                        }
                        else if(message[0].equalsIgnoreCase("hello")){
                            System.out.println("The "+ currentUser +" says hello");
                            outputToClient.writeUTF("Hello "+currentUser+"!");
                        }
                        else if(message[0].equalsIgnoreCase("SOLVE")){
                            if(message.length < 3 || message.length > 4){
                                System.out.println("Invalid format entered");
                                outputToClient.writeUTF("Error:" + " Please enter an equation type. -r or -c");
                            }
                            else if(message.length >= 3){
                                //logic for rectangle
                                if(message[1].equalsIgnoreCase("-r")){
                                    try{
                                        x = Integer.parseInt(message[2]);
                                        if(message.length > 3){
                                            y = Integer.parseInt(message[3]);
                                        }
                                        else{
                                            y = x;
                                        }

                                        System.out.println("Returning perimeter and area of rectangle");
                                        outputToClient.writeUTF("Perimeter: "+ 2*(x+y) +" Area: "+ x*y);
                                    }
                                    catch(Exception ex){
                                        System.out.println("Syntax error");
                                        outputToClient.writeUTF("Error: Invalid syntax");
                                    }
                                }
                                //logic for circle
                                else if(message[1].equalsIgnoreCase("-c")){
                                   if(message.length > 3){
                                       System.out.println("Arguments error");
                                       outputToClient.writeUTF("Error: Too many arguments for circle");
                                   }
                                   else{
                                       try{
                                           x = Integer.parseInt(message[2]);
                                           System.out.println("Returning circumference and area of circle");
                                           outputToClient.writeUTF("Circumference: "+ 2*3.14*x +" Area: "+ 3.14*x*x);
                                       }
                                       catch(Exception ex){
                                           System.out.println("Syntax error");
                                           outputToClient.writeUTF("Error: Invalid syntax");
                                       }
                                   }
                                }
                                else{
                                    System.out.println("Invalid equation type entered");
                                    outputToClient.writeUTF("Error." + "Please enter an equation type. -r or -c");
                                }
                            }
                        }
                        else {
                            System.out.println("Unknown command received:" + strReceived);
                            outputToClient.writeUTF("Unknown command." + "Please try again.");
                        }
                    }
                }//end communication loop

            }//end server socket loop

        }
        catch (IOException ex) {
            ex.printStackTrace();
        } //end try catch
    }//end func


}
