//Project 1 Ali Hazime
package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final int SERVER_PORT = 1619;

    public static void main(String[] args) {
        DataOutputStream toServer;
        DataInputStream fromServer;
        Scanner input = new Scanner(System.in);
        String message;

        //attempt to connect to server
        try{
            Socket socket = new Socket("localhost",SERVER_PORT);
            //create input stream to receive data from server
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());


        }
        catch(IOException ex){
            ex.printStackTrace();;
        }//end try catch
    }//end main
}
