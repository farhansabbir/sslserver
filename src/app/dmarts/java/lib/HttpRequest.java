package app.dmarts.java.lib;
/**
 * Author: Farhan Sabbir Siddique
 * Email: fsabbir@gmail.com
 * Web: github.com/farhansabbir
 */

import java.util.HashMap;
import java.util.regex.Pattern;

public class HttpRequest {
    private String METHOD, PATH, HTTP_VER;
    private HashMap<String,String> HEADERS;
    private StringBuilder BODY;

    public HttpRequest(String requestline, HashMap<String, String> headers, String body){
        this.BODY = new StringBuilder(body);
        this.HEADERS = headers;
        Pattern pattern = Pattern.compile("\\s");
        this.METHOD = (pattern.split(requestline)[0]);
        this.PATH = (pattern.split(requestline)[1]);
        this.HTTP_VER = (pattern.split(requestline)[2]);
    }

    public String getRequestMethod(){
        return this.METHOD;
    }

    public String getRequestBody(){
        return this.BODY.toString();
    }

    public HashMap<String,String> getRequestHeaders(){
        return this.HEADERS;
    }
}
