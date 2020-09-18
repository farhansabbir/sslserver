package app.dmarts.java.lib;

import java.util.Date;
import java.util.HashMap;
import java.util.logging.Logger;

public class HttpResponse {
    private final Logger LOGGER = Logger.getLogger(HttpResponse.class.getName());
    private String FIRSTLINE;
    private HashMap<String, String> HEADERS;
    private String BODY;
    private HttpResponse(){
        HEADERS = new HashMap<>();
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append(this.FIRSTLINE);
        for(String key:this.HEADERS.keySet()){
            ret.append(key).append(":").append(this.HEADERS.get(key)).append("\n");
        }
        ret.append("\n");
        ret.append(this.BODY);
        return ret.toString();
    }

    public static HttpResponse getOKResponse(HttpRequest request){
        HttpResponse response = new HttpResponse();

        return response;
    }

    public static HttpResponse getNotFoundHTMLResponse(HttpRequest request){
        HttpResponse response = new HttpResponse();
        response.FIRSTLINE = new StringBuilder().append("" + request.getHttpVersion())
                .append(" ")
                .append(Defs.HTTP_STATUS_NOT_FOUND_INT)
                .append(" ")
                .append(Defs.HTTP_STATUS_NOT_FOUND_STR).append("\n").toString();
        response.HEADERS.put("Server",Defs.HTTP_SERVER_SIGNATURE);
        response.HEADERS.put("Author",Defs.HTTP_SERVER_AUTHOR);
        response.HEADERS.put("Content-type","text/html");
        response.HEADERS.put("Content-length","" + Defs.HTTP_NOT_FOUND_HTML.length());
        response.HEADERS.put("Date",new Date().toString());
        response.BODY = Defs.HTTP_NOT_FOUND_HTML;
        return response;
    }
}
