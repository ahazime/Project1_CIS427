package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {
    public static void main(String args[]){
        //server function call
    }//end main

    public static void createCommunicationLoop(){
        try {

            //creates server socket
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            
            System.out.println("Server Started");

            
            //client socket 
            Socket socket = serverSocket.accept();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } //end try catch


    }//end func
}
