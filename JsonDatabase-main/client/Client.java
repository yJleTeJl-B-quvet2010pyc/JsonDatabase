package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Client {

    @Expose
    @Parameter(names = {"-t"})
    String type;
    @Expose
    @Parameter(names = {"-k"})
    String key;
    @Expose
    @Parameter(names = {"-v"})
    String value;
    @Parameter(names = {"-in"})
    String fileName;
    Gson gson;

    public Client(String[] args) {

        JCommander.newBuilder()
                .addObject(this)
                .build()
                .parse(args);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    String address = "127.0.0.1";
    int port = 23456;

    void go() {

        try (Socket socket = new Socket(InetAddress.getByName(address), port);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        ) {
            System.out.println("Client started!");
            String inputJson = "eblan";

            if (fileName == null) {
                inputJson = gson.toJson(this);
            } else {
                String filePath = System.getProperty("user.dir") + "/src/client/data/" + fileName;
                inputJson = Files.readString(Path.of(filePath));
            }

            output.writeUTF(inputJson);
            System.out.println("Sent: " + inputJson);
            String inpmsg = input.readUTF();
            System.out.println("Received: " + inpmsg);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}