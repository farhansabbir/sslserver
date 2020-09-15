package app.dmarts.java.lib;

import javax.net.ssl.SSLHandshakeException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

public class HttpClient implements Runnable{

    private Socket CLIENTSOCKET;
    public HttpClient(Socket socket){
        this.CLIENTSOCKET = socket;
    }

    @Override
    public void run() {
        // we want to indefinitely communicate with client
        while (true){

        }

    }
}