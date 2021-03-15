package skaing.a8;

import android.widget.EditText;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import jforsythe.Message;

/**
 * ServerListener that extends Thread and listens for messages from client
 * @author Samuel Kaing
 * @version 1.0
 */
public class ServerListener extends Thread{
    private Socket socket;
    private InputStream inputStream;
    private ObjectInputStream objectInputStream;
    private EditText output;
    public boolean running = true;

    /**
     * Constructor sets socket, output, inputStream, and objectInputStream
     */
    public ServerListener(Socket socket, EditText output) {
        this.socket = socket;
        this.output = output;
        try{
            inputStream = socket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Listens for messages as long as running is true.
     */
    @Override
    public void run() {
        try{
            while(running){
                Message tmp = (Message)objectInputStream.readObject();
                output.append(String.format("%s: %s%n", tmp.getName(), tmp.getMessage()));
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try{
                objectInputStream.close();
                inputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
