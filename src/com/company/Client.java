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
        String[] messageArr;

        //attempt to connect to server
        try {
            boolean isLogged = false;
            Socket socket = new Socket("localhost", SERVER_PORT);

            //create input stream to receive data from server
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());

            while (true) {

                System.out.print("C: ");
                message = input.nextLine();
                toServer.writeUTF(message);
                messageArr = message.split(" ", 2);
                if(messageArr[0].equalsIgnoreCase("LOGIN")){
                    message = fromServer.readUTF();
                    System.out.println("S: " + message);
                    if(message.equalsIgnoreCase("SUCCESS")){
                        isLogged = true;
                    }
                    else{
                        isLogged = false;
                    }
                }
                else if (message.equalsIgnoreCase("SHUTDOWN")) {
                    message = fromServer.readUTF();
                    System.out.println("S: " + message);
                    socket.close();
                    break;
                }
                else if (message.equalsIgnoreCase("LOGOUT")&&isLogged) {
                    message = fromServer.readUTF();
                    System.out.println("S: " + message);
                    socket.close();
                    break;

                }
                else{
                    message = fromServer.readUTF();
                    System.out.println("S: " + message);

                }

            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }//end try catch
    }//end main
}
