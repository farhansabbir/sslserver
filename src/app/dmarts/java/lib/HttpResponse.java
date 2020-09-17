package app.dmarts.java.lib;

import java.util.Date;
import java.util.HashMap;

public class HttpResponse {
    private String FIRSTLINE;
    private HashMap<String, String> HEADERS;
    private HttpResponse(){
    }

    public static HttpResponse getOKResponse(HttpRequest request){
        HttpResponse response = new HttpResponse();

        return response;
    }

    public static String getNotFoundHTMLResponse(HttpRequest request){
        StringBuilder ret = new StringBuilder();
        ret.append("" + request.getHttpVersion())
                .append(" ")
                .append(Defs.HTTP_STATUS_NOT_FOUND_INT)
                .append(" ")
                .append(Defs.HTTP_STATUS_NOT_FOUND_STR).append("\n")
                .append("Server:").append(Defs.HTTP_SERVER_SIGNATURE).append("\n")
                .append("Author:").append(Defs.HTTP_SERVER_AUTHOR).append("\n")
                .append("Content-type:").append("text/html").append("\n")
                .append("Date:").append(new Date()).append("\n")
                .append("\n\n")
                .append("<h1>Not Found</hi>");
        return ret.toString();
    }
}
