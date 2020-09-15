package app.dmarts.java.lib;
/**
 * Author: Farhan Sabbir Siddique
 * Email: fsabbir@gmail.com
 * Web: github.com/farhansabbir
 */

public interface RequestContext {
    public String getContextPath();
    public static String[] getAllowedMethods(){
        return Defs.HTTP_CONTEXT_ALLOWED_METHODS;
    }
}
