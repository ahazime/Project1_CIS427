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

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("logins.txt"));
            String line = reader.readLine();
            List<String> list = new ArrayList<String>();

            while (line != null) {

                // prints first user + pass
                String users = line.split(" ")[0];
                String passwords = line.split(" ")[1];
                // System.out.println(user1);
                //System.out.println(pass1);
                list.add(users);
                list.add(passwords);
                line = reader.readLine();
                System.out.println(list);
                // break;

            }//end loop
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }//end catch
        //server function call
        createCommunicationLoop();

    }//end main


    static boolean isLogged =  false;

    public static void createCommunicationLoop() {
        try {

            //creates server socket
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);

            System.out.println("Server Started");

            //keeps the server open for new clients
            while(!serverSocket.isClosed()){

                //client socket
                System.out.println("Listening for new client...");
                Socket socket = serverSocket.accept();
                System.out.println("New client connected!");

                DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
                DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());

                boolean quit_flag = false;

                //Initial communication loop
                while(!quit_flag){
                    String strReceived = inputFromClient.readUTF();

                    if(strReceived.equalsIgnoreCase("LOGIN")) {
                        isLogged = true;
                        System.out.println("Client is Logged On...");
                        outputToClient.writeUTF("SUCCESS");


                        //client must log in before accessing other commands
                        while(isLogged == true) {

                            strReceived = inputFromClient.readUTF();

                            if(strReceived.equalsIgnoreCase("LOGOUT")) {
                                //disconnects client but keeps server running
                                System.out.println("200 OK");
                                isLogged = false;
                                quit_flag=true;
                                socket.close();
                                break;
                            }

                            else if(strReceived.equalsIgnoreCase("quit")) {
                                System.out.println("Shutting down server...");
                                outputToClient.writeUTF("Shutting down server");
                                quit_flag = true;
                                serverSocket.close();
                                socket.close();
                                break;
                            }
                            else if(strReceived.equalsIgnoreCase("hello")){
                                System.out.println("The client says hello");
                                outputToClient.writeUTF("Hello client!");
                            }
                            else {
                                System.out.println("Unknown command received:" + strReceived);
                                outputToClient.writeUTF("Unknown command." + "Please try again.");
                            }
                        }
                    }//end login if statement
                    else {
                        System.out.println("Client not logged in");
                        outputToClient.writeUTF("Error." + "Please log in first.");
                    }

                        /*(if(quit_flag){
                            break;
                        }*/
                }//end communication loop

            }//end server socket loop

        }
        catch (IOException ex) {
            ex.printStackTrace();
        } //end try catch
    }//end func
}
