package app.dmarts.java.lib;

import java.util.HashMap;

public class HttpResponse {
    private String FIRSTLINE;
    private HashMap<String, String> HEADERS;
    private HttpResponse(){
    }

    public static HttpResponse getOKResponse(){
        HttpResponse response = new HttpResponse();

        return response;
    }
}
