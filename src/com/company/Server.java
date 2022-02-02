//Project 1 Ali Hazime Fatima Kourani
package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int SERVER_PORT = 1619;

    public static void main(String args[]) throws Exception {

        String filePath = "logins.txt";
        System.out.println(usingBufferedReader(filePath));

        //server function call
        createCommunicationLoop();

    }//end main

    private static String usingBufferedReader(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader buffer = new BufferedReader(new FileReader(filePath)))
        {

            String sCurrentLine;
            while ((sCurrentLine = buffer.readLine()) != null)
            {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

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
