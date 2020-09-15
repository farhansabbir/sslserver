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
    private HttpRequest REQUEST;
    public HttpClient(Socket socket){
        this.CLIENTSOCKET = socket;
    }

    @Override
    public void run() {
        HttpParser httpParser = null;
        try {
             httpParser = new HttpParser(this.CLIENTSOCKET.getInputStream());
             this.REQUEST = httpParser.parseHttpRequest();
             System.out.println(this.REQUEST.getRequestMethod());
             System.out.println(this.REQUEST.getRequestHeaders());
        } catch (Exception e) {
            System.err.println(e);
        }
        /* we want to indefinitely communicate with client, but lets get it later
        while (true){

        }
        *
         */
    }
}