package app.dmarts.java.lib;
/**
 * Author: Farhan Sabbir Siddique
 * Email: fsabbir@gmail.com
 * Web: github.com/farhansabbir
 */

import app.dmarts.java.sslserver.Server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

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
                 writer.write(HttpResponse.getNotFoundHTMLResponse(this.REQUEST));
                 //writer.write(this.REQUEST.getHttpVersion() + " " + Defs.HTTP_STATUS_NOT_FOUND_INT + " Not Found\nServer: devn\nContent-type:text/html\n\n<h1>Not Found</h1>");
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