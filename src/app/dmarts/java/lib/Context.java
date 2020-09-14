package app.dmarts.java.lib;

public interface Context {
    public String getContextPath();
    public void setContextPath(String contextpath);
    public static String[] getAllowedMethods(){
        return Defs.HTTP_CONTEXT_ALLOWED_METHODS;
    }
}
