//Project 1 Ali Hazime Fatima Kourani
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

            while(true){

                System.out.print("Send command to server:\t");
                message = input.nextLine();
                toServer.writeUTF(message);
                if(message.equalsIgnoreCase("quit")) {
                    socket.close();
                    break;
                }
                if(message.equalsIgnoreCase("logout")){
                    socket.close();
                    break;
                }

                message = fromServer.readUTF();
                System.out.println("Server says: " + message);
            }

        }
        catch(IOException ex){
            ex.printStackTrace();
        }//end try catch
    }//end main
}
