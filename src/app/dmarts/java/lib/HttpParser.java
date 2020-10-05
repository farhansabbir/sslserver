package app.dmarts.java.lib;
/**
 * Author: Farhan Sabbir Siddique
 * Email: fsabbir@gmail.com
 * Web: github.com/farhansabbir
 */

import javax.net.ssl.SSLHandshakeException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;

public class HttpParser{

    private BufferedReader READER;
    private Socket CLIENT;

    public HttpParser(Socket clientsocket) throws IOException {
        this.CLIENT = clientsocket;
        this.READER = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
    }

    public HttpRequest parseHttpRequest() throws IOException {
        HttpRequest request = null;
        String req_line = this.getRequestLine();
        HashMap<String, String> headers = this.getRequestHeaders();
        String body = this.getRequestBody();
        request = new HttpRequest(req_line,headers,body);
        request.setClientSocket(this.CLIENT);
        return request;
    }
    private String readByLine() throws IOException {
        String line = new String();
        try {
            line = this.READER.readLine();
        }catch (SocketException | SSLHandshakeException soex){
            System.out.println(line);
        }
        if (line == null) return "";
        return line;
        /*
        StringBuilder INIT = new StringBuilder();
        int data;
        while ((data = this.READER.read())>=0) {
            if ((char) data == '\n') {
                break;
            }
            INIT.append((char) data);
        }
        return INIT.toString();
        */
    }
    private String getRequestLine() throws IOException {
        return this.readByLine();
    }
    private HashMap<String, String> getRequestHeaders() throws IOException {
        HashMap<String,String> ret = new HashMap<>();
        String header;
        while((header = this.readByLine()).length()!=0) {

            ret.put(header.split(":")[0].trim(),header.split(":")[1].trim());
        }
        return ret;
    }
    private String getRequestBody() throws IOException {
        StringBuilder ret = new StringBuilder();
        while (this.READER.ready()){
            ret.append((char) this.READER.read());
        }
        return ret.toString();
    }
}

