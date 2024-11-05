package server;

public class Response {
    String response;
    String value;
    String reason;
    void ok() {
        response = "OK";
    }

    void error() {
        response = "ERROR";
    }

    void noSuchKey() {
        reason = "No such key";
    }
}
