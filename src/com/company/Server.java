package com.company;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int SERVER_PORT = 1613;

    public static void main(String args[]){
        //server function call
        createCommunicationLoop();
    }//end main
    static boolean isLogged =  false;

    public static void createCommunicationLoop() {
        boolean quit_flag = false;
        while (true) { // loop to ensure that the server doesn't close
                try {

                    //creates server socket
                    ServerSocket serverSocket = new ServerSocket(SERVER_PORT);

                    System.out.println("Server Started");

                    //client socket
                    Socket socket = serverSocket.accept();

                    DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
                    DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());

                    String strReceived = inputFromClient.readUTF();

                    if(strReceived.equalsIgnoreCase("LOGIN")) {
                        isLogged = true;
                        System.out.print("Client is Logged On...");
                        outputToClient.writeUTF("SUCCESS");

                        while(isLogged == true) {

                            strReceived = inputFromClient.readUTF();

                            if(strReceived.equalsIgnoreCase("LOGOUT")) {
                                System.out.println("200 OK");
                                isLogged = false;
                                serverSocket.close();
                                socket.close();
                            }

                            else if(strReceived.equalsIgnoreCase("quit")) {
                                System.out.print("Shutting down server...");
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

                    }

                    if(quit_flag){
                        break;
                    }
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                } //end try catch


            }//end of loop
    }//end func
}
