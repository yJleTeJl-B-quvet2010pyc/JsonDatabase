package server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Database {
    String filePath = System.getProperty("user.dir") + "/src/server/data/db.json";
    File databaseFile = new File(filePath);
    FileWriter writer = new FileWriter(databaseFile, true);
    ReadWriteLock lock = new ReentrantReadWriteLock();
    Lock writeLock = lock.writeLock();
    Map<String, String> arkadii;
    Response exit() {
        Response response = new Response();
        response.ok();
        return response;
    }
    Response set(String index, String text) throws IOException {
        Response response = new Response();
        if (text.isEmpty()) {
            response.error();
            response.noSuchKey();
        } else {
            writeLock.lock();
            arkadii.put(index, text);
            writer.write(arkadii.toString());
            writeLock.unlock();
            response.ok();
        }
        return response;
    }
    Response get(String index) {
        Response response = new Response();
        if (arkadii.get(index) == null) {
            response.error();
            response.noSuchKey();

        } else {
            response.value = arkadii.get(index);
            response.ok();
        }
        return response;
    }

    Response delete(String index) throws IOException {
        Response response = new Response();
        if (arkadii.get(index) == null) {
            response.error();
            response.noSuchKey();
        } else {
            writeLock.lock();
            arkadii.remove(index);
            writer.write(arkadii.toString());
            writeLock.unlock();
            response.ok();
        }
        return response;
    }

    public Database() throws IOException {
        arkadii = new HashMap<>();
        arkadii = DatabaseFromFile.byBufferedReader(filePath);
    }
}
