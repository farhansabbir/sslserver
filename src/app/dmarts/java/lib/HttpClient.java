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
import java.util.logging.Logger;

public class HttpClient implements Runnable{
    private final Logger LOGGER = Logger.getLogger(HttpClient.class.getName());

    private Socket CLIENTSOCKET;
    private HttpRequest REQUEST;
    public HttpClient(Socket socket) throws SocketException {
        this.CLIENTSOCKET = socket;
    }

    @Override
    public void run() {
        // HTTP is stateless, so we dont need same thread to handle all requests; hence no forever loops
        HttpParser httpParser = null;
        try {
             httpParser = new HttpParser(this.CLIENTSOCKET.getInputStream());
             this.REQUEST = httpParser.parseHttpRequest();
             if (!Server.CONTEXTS.containsKey(this.REQUEST.getContextPath())){
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(this.CLIENTSOCKET.getOutputStream()));
                 writer.write(HttpResponse.getNotFoundHTMLResponse(this.REQUEST).toString());
                 writer.flush();
                 writer.close();
             }
             else {
                 // this part is what gets interesting to actually fetch file and send.
                 // and this is also where i can add handlers later on.


             }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}