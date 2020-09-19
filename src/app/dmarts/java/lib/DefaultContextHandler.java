package app.dmarts.java.lib;

import java.io.BufferedOutputStream;
import java.io.IOException;

public class DefaultContextHandler implements ContextHandler{
    @Override
    public void handle(HttpRequest request) throws IOException {
        HttpResponse response = HttpResponse.getNotFoundHttpResponse(request);
        BufferedOutputStream OUT = new BufferedOutputStream(request.getClientSocket().getOutputStream());
        OUT.write(response.toString().getBytes());
        OUT.close();
    }
}
