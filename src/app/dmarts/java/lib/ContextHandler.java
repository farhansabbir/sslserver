package app.dmarts.java.lib;

import java.io.IOException;

public interface ContextHandler {
    public void handle(HttpRequest request) throws IOException;
}
