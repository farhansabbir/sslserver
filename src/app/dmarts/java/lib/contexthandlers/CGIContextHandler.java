package app.dmarts.java.lib.contexthandlers;

import app.dmarts.java.lib.ContextHandler;
import app.dmarts.java.lib.HttpRequest;

public class CGIContextHandler implements ContextHandler {
    @Override
    public void handle(HttpRequest request) {
        System.out.println("In CGI handler");
    }
}
