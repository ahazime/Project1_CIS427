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
            List<String> list = new ArrayList<String>();
            String[] message;
            while (line != null) {

                // lists user and pass
                list.add(line.split(" ")[0]);
                list.add(line.split(" ")[1]);
                line = reader.readLine();

            }//end loop
            reader.close();

            System.out.println(list.size());

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
                boolean user=false, pass=false;
                //Initial communication loop
                while(!quit_flag){

                    String strReceived = inputFromClient.readUTF();
                    message = strReceived.split(" ",3);

                    if(message[0].equalsIgnoreCase("LOGIN")) {
                        user = false;
                        pass = false;
                        if(message.length < 3){
                            System.out.println("Client failed login...");
                            outputToClient.writeUTF("Please provide a username and password when logging in.");
                        }
                        else{
                            for(int i=0; i<list.size();i++){
                                if(message[1].equalsIgnoreCase(list.get(i))){
                                    user = true;
                                }
                                if(message[2].equalsIgnoreCase(list.get(i))){
                                    pass = true;
                                }
                                if(user && pass){
                                    isLogged = true;
                                }
                            }
                            if(isLogged) {
                                System.out.println("Client is Logged On...");
                                outputToClient.writeUTF("SUCCESS");
                            }
                            else{
                                System.out.println("Client failed login...");
                                outputToClient.writeUTF("Incorrect login info, please try again.");
                            }
                        }
                        //client must log in before accessing other commands
                        while(isLogged == true) {

                            strReceived = inputFromClient.readUTF();
                            message = strReceived.split(" ",3);

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
