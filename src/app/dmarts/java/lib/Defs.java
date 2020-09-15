package app.dmarts.java.lib;
/**
 * Author: Farhan Sabbir Siddique
 * Email: fsabbir@gmail.com
 * Web: github.com/farhansabbir
 */

public interface Defs {
    public final int HTTP_STATUS_OK                         = 200;
    public final int HTTP_STATUS_NOT_FOUND                  = 404;
    public final int HTTP_SERVER_DEFAULT_PORT               = 80;
    public final int HTTP_SERVER_FALLBACK_PORT1             = 8080;
    public final int HTTP_SERVER_FALLBACK_PORT2             = 8081;
    public final String HTTP_STATUS_CONTEXT                 = "/status";
    public final String[] HTTP_CONTEXT_ALLOWED_METHODS      =   new String[]{"POST","GET"};
    public final int HTTP_CLIENT_BACKLOG                    = 20;
}
