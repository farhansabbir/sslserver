package app.dmarts.java.lib.contexthandlers;

import app.dmarts.java.lib.ContextHandler;
import app.dmarts.java.lib.HttpRequest;

import java.io.IOException;

public class CGIContextHandler implements ContextHandler {
    @Override
    public void handle(HttpRequest request) throws IOException {
        System.out.println("In CGI handler" + request.getClientSocket().getRemoteSocketAddress());
    }
}
