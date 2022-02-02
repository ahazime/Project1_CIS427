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

        while (true) {
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

                            String strReceived2 = inputFromClient.readUTF();

                            if(strReceived2.equalsIgnoreCase("LOGOUT")) {
                                System.out.println("Client Has Logged Out...");
                                isLogged = false;
                                outputToClient.writeUTF("200 OK");
                                serverSocket.close();
                                socket.close();

                            }
                            String strReceived3 = inputFromClient.readUTF();
                            if(strReceived3.equalsIgnoreCase("quit")) {
                                System.out.print("Shutting down server...");
                                outputToClient.writeUTF("Shutting down server");
                                serverSocket.close();
                                socket.close();

                            }
                            else {
                                System.out.println("Unknown command received:" + strReceived);
                                outputToClient.writeUTF("Unknown command." + "Please try again.");
                            }
                        }
                    }


                }
                catch (IOException ex) {
                    ex.printStackTrace();
                } //end try catch


            }//end func
    }
}
