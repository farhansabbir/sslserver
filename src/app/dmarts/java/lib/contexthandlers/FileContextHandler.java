package app.dmarts.java.lib.contexthandlers;

import app.dmarts.java.lib.ContextHandler;
import app.dmarts.java.lib.HttpRequest;
import app.dmarts.java.sslserver.Server;

import java.io.File;

public class FileContextHandler implements ContextHandler {
    private String FILE;

    public FileContextHandler(String filepath){
        this.FILE  = filepath;
    }

    @Override
    public void handle(HttpRequest request) {
        if(Server.CONTEXTHANDLERS.containsKey(request.getContextPath())) {
            if(new File(this.FILE).exists()){
                System.out.println("File " + this.FILE + " exists");
            }
        }
        // If I am here, it means either context doesn't exist or actual physical file doesn't
        // either way, its 404.


    }
}
