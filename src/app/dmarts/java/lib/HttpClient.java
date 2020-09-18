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
            //System.out.println(Server.CONTEXTS);
             if (!Server.CONTEXTMAP.containsKey(this.REQUEST.getContextPath())){
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(this.CLIENTSOCKET.getOutputStream()));
                 writer.write(HttpResponse.getNotFoundHttpResponse(this.REQUEST).toString());
                 writer.flush();
                 writer.close();
             }
             else {
                 // this part is what gets interesting to actually fetch file and send.
                 // and this is also where i can add handlers later on.
                 //BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(this.CLIENTSOCKET.getOutputStream()));
                 //BufferedReader reader = new BufferedReader(new FileReader(new File(Server.CONTEXTS.get(this.REQUEST.getContextPath()))));
                 BufferedOutputStream writer = new BufferedOutputStream(this.CLIENTSOCKET.getOutputStream());
                 BufferedInputStream reader = new BufferedInputStream(new FileInputStream(new File(Server.CONTEXTMAP.get(this.REQUEST.getContextPath()))));
                 System.out.println(HttpResponse.getOKHttpResponse(this.REQUEST).toString());
                 writer.write("HTTP/1.1 200 OK\n".getBytes());
                 writer.write("Server: wow\n".getBytes());
                 if(this.REQUEST.getContextPath().endsWith(".jpeg")) {
                     writer.write("Content-type:image/jpg\n\n".getBytes());
                 }
                 else if(this.REQUEST.getContextPath().endsWith(".png")) {
                     writer.write("Content-type:image/png\n\n".getBytes());
                 }
                 else if(this.REQUEST.getContextPath().endsWith(".html")) {
                     writer.write("Content-type:text/html\n\n".getBytes());
                 }
                 writer.flush();
                 byte[] buffer = new byte[1024];
                 while (reader.read(buffer)!=-1){
                     writer.write(buffer);
                     writer.flush();
                 }
                 reader.close();
                 writer.close();
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}