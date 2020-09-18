package app.dmarts.java.lib;
/**
 * Author: Farhan Sabbir Siddique
 * Email: fsabbir@gmail.com
 * Web: github.com/farhansabbir
 */
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Logger;

public class HttpResponse {
    private final Logger LOGGER = Logger.getLogger(HttpResponse.class.getName());
    private String FIRSTLINE;
    private HashMap<String, String> HEADERS;
    private String BODY;
    private HttpResponse(){
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

    public static class HttpResponseBuilder{
        private String FIRSTLINE = "";
        private HashMap<String, String> HEADERS = new HashMap<>();
        private String BODY = "";

        public HttpResponseBuilder(){
            this.BODY = "";
            this.FIRSTLINE = new StringBuilder("HTTP/1.1 200\n").toString();;
            this.HEADERS = new HashMap<>();
            this.HEADERS.put("Server",Defs.HTTP_SERVER_SIGNATURE);
            this.HEADERS.put("Author",Defs.HTTP_SERVER_AUTHOR);
            this.HEADERS.put("Date",new Date().toString());
        }

        public HttpResponseBuilder addHeader(String key, String value){
            this.HEADERS.put(key,value);
            return this;
        }

        public HttpResponseBuilder appendToBody(String body){
            StringBuilder stringBuilder = new StringBuilder(this.BODY);
            stringBuilder.append(body);
            this.BODY = stringBuilder.toString();
            return this;
        }

        public HttpResponseBuilder setResponseLine(String line){
            this.FIRSTLINE = new StringBuilder(line).append("\n").toString();
            return this;
        }

        public HttpResponseBuilder setOKResponseLine(String httpversion){
            this.FIRSTLINE = new StringBuilder(" ").append(httpversion).append(" 200\n").toString();
            return this;
        }

        public HttpResponseBuilder setNotFoundResponseLine(String httpversion){
            this.FIRSTLINE = new StringBuilder("")
                    .append(httpversion)
                    .append(" ")
                    .append(Defs.HTTP_STATUS_NOT_FOUND_INT)
                    .append(" ")
                    .append(Defs.HTTP_STATUS_NOT_FOUND_STR)
                    .append("\n")
                    .toString();
            return this;
        }

        public HttpResponse build(){
            HttpResponse response = new HttpResponse();
            response.FIRSTLINE = this.FIRSTLINE;
            response.HEADERS = this.HEADERS;
            response.BODY = this.BODY;
            return response;
        }
    }


    public static HttpResponse getNotFoundHttpResponse(HttpRequest request){
        return new HttpResponseBuilder()
                .setNotFoundResponseLine(request.getHttpVersion())
                .addHeader("Content-type","text/html")
                .addHeader("Content-length","" + Defs.HTTP_NOT_FOUND_HTML.length())
                .appendToBody(Defs.HTTP_NOT_FOUND_HTML)
                .build();
    }

    public static HttpResponse getOKHttpResponse(HttpRequest request){
        return new HttpResponseBuilder().build();
    }
}
