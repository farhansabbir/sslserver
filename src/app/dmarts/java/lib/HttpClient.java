package app.dmarts.java.lib;
/**
 * Author: Farhan Sabbir Siddique
 * Email: fsabbir@gmail.com
 * Web: github.com/farhansabbir
 */

import app.dmarts.java.sslserver.Server;

import javax.net.ssl.SSLHandshakeException;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

public class HttpClient implements Runnable{

    private Socket CLIENTSOCKET;
    private HttpRequest REQUEST;
    public HttpClient(Socket socket) throws SocketException {
        this.CLIENTSOCKET = socket;
    }

    @Override
    public void run() {
        HttpParser httpParser = null;
        try {
             httpParser = new HttpParser(this.CLIENTSOCKET.getInputStream());
             this.REQUEST = httpParser.parseHttpRequest();
             if (!Server.CONTEXTS.containsKey(this.REQUEST.getContextPath())){
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(this.CLIENTSOCKET.getOutputStream()));
                 writer.write("HTTP/1.1 " + Defs.HTTP_STATUS_NOT_FOUND + " Not Found\nServer: devn\nContent-type:text/html\n\n<h1>Not Found</h1>");
                 writer.flush();
                 writer.close();
             }
            System.out.println(Thread.currentThread().getName());
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