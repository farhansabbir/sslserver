package app.dmarts.java.lib.contexthandlers;

import app.dmarts.java.lib.ContextHandler;
import app.dmarts.java.lib.HttpRequest;
import app.dmarts.java.lib.HttpResponse;
import app.dmarts.java.sslserver.Server;

import java.io.BufferedOutputStream;
import java.io.IOException;

public class CGIContextHandler implements ContextHandler {
    @Override
    public void handle(HttpRequest request) throws IOException {
        System.out.println("In CGI handler" + request.getClientSocket().getRemoteSocketAddress());
        HttpResponse response = new HttpResponse.HttpResponseBuilder()
                .addHeader("Content-type","application/json")
                .build();
        BufferedOutputStream outputStream = new BufferedOutputStream(request.getClientSocket().getOutputStream());
        outputStream.write(response.toString().getBytes());
        synchronized (Server.STATUS){
            outputStream.write(Server.STATUS.toString().getBytes());
            Server.STATUS.get(request.getContextPath()).getAsJsonArray().add(request.getClientSocket().getRemoteSocketAddress().toString());
        }
        outputStream.flush();
        outputStream.close();

    }
}
