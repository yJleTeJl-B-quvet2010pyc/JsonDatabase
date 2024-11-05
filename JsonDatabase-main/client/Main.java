package client;
import java.io.*;
import java.net.Socket;
import java.net.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Client client = new Client(args);
        client.go();
    }
}
