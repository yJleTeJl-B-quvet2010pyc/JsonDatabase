package server;

import java.net.Socket;

public class Session extends Thread {
    private final Socket socket;
    public Session(Socket socketForClient) {
        this.socket = socketForClient;
    }
}