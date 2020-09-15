package app.dmarts.java.lib;
/**
 * Author: Farhan Sabbir Siddique
 * Email: fsabbir@gmail.com
 * Web: github.com/farhansabbir
 */

public interface Context {
    public String getContextPath();
    public void setContextPath(String contextpath);
    public static String[] getAllowedMethods(){
        return Defs.HTTP_CONTEXT_ALLOWED_METHODS;
    }
}
