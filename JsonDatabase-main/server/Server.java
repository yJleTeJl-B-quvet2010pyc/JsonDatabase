package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.ServerSocket;
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class Server {
    String address = "127.0.0.1";
    int port = 23456;
    Response outmsg;
    Gson gson;
    Database database;
    public Server() {
        gson = new GsonBuilder()
                .create();
        try {
            database = new Database();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void go() {

        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {
            System.out.println("Server started!");
            while (true) {
                try (Socket socket = server.accept();
                     DataInputStream input = new DataInputStream(socket.getInputStream());
                     DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                ) {
                    try {

                        String inpmsg = input.readUTF();
                        Command command = gson.fromJson(inpmsg, Command.class);

                        switch (command.type) {
                            case "exit" ->
                                    outmsg = database.exit();
                            case "set" -> outmsg = database.set(command.key, command.value);
                            case "get" -> outmsg = database.get(command.key);
                            case "delete" -> outmsg = database.delete(command.key);
                        }

                        output.writeUTF(gson.toJson(outmsg));
                    } catch (IOException e){
                        throw new RuntimeException(e);
                    }
                }
            }
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}
