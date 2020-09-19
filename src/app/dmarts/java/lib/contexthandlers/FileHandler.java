package app.dmarts.java.lib.contexthandlers;

import app.dmarts.java.lib.ContextHandler;
import app.dmarts.java.lib.HttpRequest;
import app.dmarts.java.lib.HttpResponse;

public class FileHandler implements ContextHandler {
    private String FILE;

    public FileHandler(String filepath){
        this.FILE  = filepath;
    }
    @Override
    public void handle(HttpRequest request, HttpResponse response) {

    }
}
