//Project 1 Ali Hazime Fatima Kourani
package com.company;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int SERVER_PORT = 1619;

    public static void main(String args[]) throws Exception {
        //server function call
        createCommunicationLoop();

    } //end main

    static boolean isLogged = false;

    public static void createCommunicationLoop() {
        try {

            BufferedReader reader;

            reader = new BufferedReader(new FileReader("logins.txt"));
            String line = reader.readLine();
            List<String> users = new ArrayList<String>();
            List<String> passwords = new ArrayList<String>();
            String[] message;
            String content = null;


            while (line != null) {
                users.add(line.split(" ")[0]);
                passwords.add(line.split(" ")[1]);
                line = reader.readLine();

            } //end loop
            reader.close();

            List<File> fileList = new ArrayList<>();
            for (int x = 0; x < users.size(); x++) {
                File myObj = new File(users.get(x) + "_solutions.txt");
                fileList.add(myObj);
                FileWriter fw = new FileWriter(fileList.get(x));
                fw.write("");
                fw.flush();
                fw.close();
            }

            //creates server socket
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);

            System.out.println("Server Started");

            boolean quit_flag;
            boolean user_flag, pass_flag;
            String currentUser = null;
            int x = 0, y = 0;

            //keeps the server open for new clients
            while (!serverSocket.isClosed()) {
                quit_flag = false;

                //client socket
                System.out.println("Listening for new client...");
                Socket socket = serverSocket.accept();
                System.out.println("New client connected!");

                DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
                DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());

                //Initial communication loop
                while (!quit_flag) {

                    String strReceived = inputFromClient.readUTF();
                    message = strReceived.split(" ", 3);

                    //login conditional check
                    if (message[0].equalsIgnoreCase("LOGIN")) {
                        user_flag = false;
                        System.out.println("Client says: " + strReceived);

                        if (message.length < 3) {
                            System.out.println("Client failed login...");
                            outputToClient.writeUTF("FAILURE: Please provide correct username and password. Try again.");
                        } else {
                            for (int i = 0; i < users.size(); i++) {
                                if (message[1].equalsIgnoreCase(users.get(i)) && message[2].equalsIgnoreCase(passwords.get(i))) {
                                    user_flag = true;
                                    currentUser = message[1];
                                }

                                if (user_flag) {
                                    isLogged = true;
                                }
                            }
                            if (isLogged) {
                                System.out.println("Client " + currentUser + " is Logged On...");
                                outputToClient.writeUTF("SUCCESS");
                            } else {
                                System.out.println("Client failed login...");
                                outputToClient.writeUTF("FAILURE: Please provide correct username and password. Try again.");
                            }
                        }

                    } //end login if statement
                    else {
                        System.out.println("Client not logged in");
                        System.out.println("Client says: " + strReceived);
                        outputToClient.writeUTF("FAILURE: Please provide correct username and password. Try again.");
                    }

                    //client must log in before accessing other commands
                    //begin log in loop
                    while (isLogged == true) {

                        strReceived = inputFromClient.readUTF();
                        message = strReceived.split(" ", 4);

                        if (message[0].equalsIgnoreCase("LOGOUT")) {
                            //disconnects client but keeps server running
                            outputToClient.writeUTF("200 OK");
                            System.out.println("Client says: " + strReceived);
                            System.out.println("200 OK");
                            isLogged = false;
                            quit_flag = true;

                            socket.close();
                            break;
                        } else if (message[0].equalsIgnoreCase("SHUTDOWN")) {
                            System.out.println("Client says: " + strReceived);
                            System.out.println("Shutting down server...");
                            outputToClient.writeUTF("Shutting down server");
                            quit_flag = true;
                            serverSocket.close();
                            socket.close();
                            break;
                        } else if (message[0].equalsIgnoreCase("SOLVE")) {

                            if (message.length < 3 || message.length > 4) {

                                if (message.length == 2) {
                                    if (message[1].equalsIgnoreCase("-r")) {
                                        System.out.println("Invalid format entered");
                                        outputToClient.writeUTF("Error: No sides found");
                                        appendString(users, fileList, currentUser, "Error: No sides found");
                                    } else if (message[1].equalsIgnoreCase("-c")) {
                                        System.out.println("Invalid format entered");
                                        outputToClient.writeUTF("Error: No radius found");
                                        appendString(users, fileList, currentUser, "Error: No radius found");
                                    } else {
                                        System.out.println("Invalid format entered");
                                        outputToClient.writeUTF("301 message format error");
                                        System.out.println("Client says: " + strReceived);
                                    }
                                } else {
                                    System.out.println("Invalid format entered");
                                    outputToClient.writeUTF("301 message format error");
                                    System.out.println("Client says: " + strReceived);
                                }


                            } else if (message.length >= 3) {
                                //logic for rectangle
                                if (message[1].equalsIgnoreCase("-r")) {
                                    try {
                                        x = Integer.parseInt(message[2]);
                                        if (message.length > 3) {
                                            y = Integer.parseInt(message[3]);
                                        } else {
                                            y = x;
                                        }

                                        content = "sides " + x + " " + y + ": " + "Rectangle’s perimeter is " + 2 * (x + y) + " and area is " + x * y;
                                        appendString(users, fileList, currentUser, content);

                                        System.out.println(content);
                                        System.out.println("Returning perimeter and area of rectangle");
                                        System.out.println("Client says: " + strReceived);
                                        outputToClient.writeUTF("Rectangle’s perimeter is " + 2 * (x + y) + " and area is " + x * y);
                                    } catch (Exception ex) {
                                        System.out.println("Syntax error");
                                        outputToClient.writeUTF("301 message format error");
                                        System.out.println("Client says: " + strReceived);
                                    }

                                }
                                //logic for circle
                                else if (message[1].equalsIgnoreCase("-c")) {
                                    if (message.length > 3) {
                                        System.out.println(message.length);
                                        System.out.println("301 message format error");
                                        outputToClient.writeUTF("301 message format error");
                                        System.out.println("Client says: " + strReceived);
                                    } else {
                                        try {
                                            x = Integer.parseInt(message[2]);
                                            System.out.println("Returning circumference and area of circle");
                                            outputToClient.writeUTF("Circle’s circumference is " + 2 * 3.14 * x + " and area is " + 3.14 * x * x);
                                            String info = "radius " + x + ": " + "Circle’s circumference is " + 2 * 3.14 * x + " and area is " + 3.14 * x * x;
                                            appendString(users, fileList, currentUser, info);
                                            System.out.println("Client says: " + strReceived);
                                        } catch (Exception ex) {
                                            System.out.println("Syntax error");
                                            outputToClient.writeUTF("301 message format error");
                                            appendString(users, fileList, currentUser, "Error: No radius found");
                                            System.out.println("Client says: " + strReceived);

                                        }
                                    }
                                } else {
                                    System.out.println("Invalid equation type entered");
                                    outputToClient.writeUTF("301 message format error");
                                    System.out.println("Client says: " + strReceived);
                                }
                            }
                            //logic for LIST
                        } else if (message[0].equalsIgnoreCase("LIST")) {
                            System.out.println("Client says: " + strReceived);
                            if (message.length > 1) {
                                if (message[1].equalsIgnoreCase("-all") && !currentUser.equalsIgnoreCase("root")) {
                                    System.out.println("Invalid -all request");
                                    outputToClient.writeUTF("Error: you are not the root user");
                                    System.out.println("Client says: " + strReceived);
                                } else if (message[1].equalsIgnoreCase("-all") && currentUser.equalsIgnoreCase("root")) {
                                    System.out.println("Root user requested list -all");
                                    System.out.println("Client says: " + strReceived);
                                    String fileAsString = "";
                                    int flag;

                                    for (int i = 0; i < users.size(); i++) {
                                        StringBuilder sb = new StringBuilder();
                                        flag = 0;
                                        reader = new BufferedReader(new FileReader(users.get(i) + "_solutions.txt"));
                                        String q = reader.readLine();
                                        while (q != null) {
                                            sb.append(q).append("\n");
                                            q = reader.readLine();
                                            flag++;
                                        }
                                        if (flag > 0) {
                                            fileAsString = fileAsString + "\n" + (users.get(i) + "\n" + sb.toString());
                                        } else {
                                            fileAsString = fileAsString + "\n" + (users.get(i) + "\n" + "No interactions yet");
                                        }
                                        reader.close();
                                    }
                                    outputToClient.writeUTF("\n" + fileAsString);
                                } else {
                                    outputToClient.writeUTF("301 message format error");
                                    System.out.println("Client says: " + strReceived);
                                }
                            } else {
                                System.out.println("Listing for client...");
                                int i;
                                for (i = 0; i < users.size(); i++) {
                                    if (currentUser.equalsIgnoreCase(users.get(i))) {
                                        break;
                                    }
                                }
                                reader = new BufferedReader(new FileReader(users.get(i) + "_solutions.txt"));
                                StringBuilder sb = new StringBuilder();
                                String q = reader.readLine();
                                int flag = 0;
                                while (q != null) {
                                    sb.append(q).append("\n");
                                    q = reader.readLine();
                                    flag++;
                                }
                                String fileAsString;
                                if (flag > 0) {
                                    fileAsString = sb.toString();
                                } else {
                                    fileAsString = "No interactions yet";
                                }
                                outputToClient.writeUTF(currentUser + "\n" + fileAsString);
                                reader.close();
                            }

                        } else {
                            System.out.println("300 invalid command");
                            System.out.println("Client says: " + strReceived);
                            outputToClient.writeUTF("300 invalid command");
                        }
                    }
                } //end communication loop

            } //end server socket loop

        } catch (IOException ex) {
            ex.printStackTrace();
        } //end try catch
    } //end func

    //Function to append to files
    public static void appendString(List<String> users, List<File> files, String currentUser, String content) throws IOException {
        int x;
        for (x = 0; x < users.size(); x++) {
            if (currentUser.equalsIgnoreCase(users.get(x))) {
                break;
            }
        }

        FileWriter fw = new FileWriter(files.get(x), true);
        fw.write(content + "\n");
        fw.flush();
        fw.close();
    } //end func

}