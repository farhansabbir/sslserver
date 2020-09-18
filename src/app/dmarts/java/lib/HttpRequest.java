package app.dmarts.java.lib;
/**
 * Author: Farhan Sabbir Siddique
 * Email: fsabbir@gmail.com
 * Web: github.com/farhansabbir
 */

import java.util.HashMap;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class HttpRequest implements RequestContext {
    private final Logger LOGGER = Logger.getLogger(HttpRequest.class.getName());
    private String METHOD, PATH, HTTP_VER, QUERY;
    private HashMap<String,String> HEADERS;
    private StringBuilder BODY;

    public HttpRequest(String requestline, HashMap<String, String> headers, String body){
        this.BODY = new StringBuilder(body);
        this.HEADERS = headers;
        Pattern pattern = Pattern.compile("\\s");
        this.METHOD = (pattern.split(requestline)[0]);
        this.HTTP_VER = (pattern.split(requestline)[2]);
        requestline = pattern.split(requestline)[1];
        pattern = Pattern.compile("[?]");
        this.PATH = (pattern.split(requestline)[0]);
        this.QUERY = (pattern.split(requestline)[1]);
    }

    public boolean requestHasQuery(){
        return this.QUERY.length()>0 ? true : false;
    }

    public String getQuery(){
        return this.QUERY;
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
    public String getHttpVersion(){
        return this.HTTP_VER;
    }

    @Override
    public String getContextPath() {
        return this.PATH;
    }
}
