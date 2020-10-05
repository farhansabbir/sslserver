package app.dmarts.java.lib.contexthandlers;

import app.dmarts.java.lib.ContextHandler;
import app.dmarts.java.lib.HttpRequest;
import app.dmarts.java.lib.HttpResponse;
import app.dmarts.java.sslserver.Server;

import java.io.*;
import java.util.Base64;

public class FileContextHandler implements ContextHandler {
    private String FILE;

    public FileContextHandler(String filepath){
        this.FILE  = filepath;
    }

    @Override
    public void handle(HttpRequest request) throws IOException {
        if(Server.CONTEXTHANDLERS.containsKey(request.getContextPath())) {
            synchronized (Server.STATUS) {
                Server.STATUS.get(request.getContextPath()).getAsJsonArray().add(request.getClientSocket().getRemoteSocketAddress().toString());
            }

            if(new File(this.FILE).exists()){
                HttpResponse response = null;
                if(this.FILE.endsWith(".html")){
                    response = new HttpResponse.HttpResponseBuilder()
                            .addHeader("Content-type","text/html")
                            .addHeader("Content-length","" + new File(this.FILE).length())
                            .setOKResponseLine(request.getHttpVersion())
                            .build();
                }
                else if(this.FILE.endsWith(".jpg")){
                    response = new HttpResponse.HttpResponseBuilder()
                            .addHeader("Content-type","image/jpeg")
                            .addHeader("Content-length","" + new File(this.FILE).length())
                            .addHeader("Set-Cookie","hello=world")
                            .setOKResponseLine(request.getHttpVersion())
                            .build();
                }
                else if(this.FILE.endsWith(".png")){
                    response = new HttpResponse.HttpResponseBuilder()
                            .addHeader("Content-type","image/png")
                            .addHeader("Content-length","" + new File(this.FILE).length())
                            .addHeader("Set-Cookie","hello=world")
                            .setOKResponseLine(request.getHttpVersion())
                            .build();
                }
                else if(this.FILE.endsWith(".jpeg")){
                    response = new HttpResponse.HttpResponseBuilder()
                            .addHeader("Content-type","image/jpeg")
                            .addHeader("Content-length","" + new File(this.FILE).length())
                            .addHeader("Set-Cookie","hello=world")
                            .setOKResponseLine(request.getHttpVersion())
                            .build();
                }
                else if(this.FILE.endsWith(".js")){
                    response = new HttpResponse.HttpResponseBuilder()
                            .addHeader("Content-type","text/javascript")
                            .addHeader("Content-length","" + new File(this.FILE).length())
                            .setOKResponseLine(request.getHttpVersion())
                            .build();
                }
                else if(this.FILE.endsWith(".css")){
                    response = new HttpResponse.HttpResponseBuilder()
                            .addHeader("Content-type","text/css")
                            .addHeader("Content-length","" + new File(this.FILE).length())
                            .setOKResponseLine(request.getHttpVersion())
                            .build();
                }
                else {
                        response = new HttpResponse.HttpResponseBuilder()
                                .addHeader("Content-type","text/txt")
                                .addHeader("Content-length","" + new File(this.FILE).length())
                                .setOKResponseLine(request.getHttpVersion())
                                .build();
                }
                BufferedInputStream IN = new BufferedInputStream(new FileInputStream(this.FILE));
                BufferedOutputStream OUT = new BufferedOutputStream(request.getClientSocket().getOutputStream());
                if(request.getRequestHeaders().containsKey("Cookie")){
                    System.out.println("Contains cookie: " + request.getRequestHeaders().get("Cookie") + ". Resetting.");
                    response.setHeader("Set-Cookie", "hello='" + Base64.getEncoder().encodeToString("world".getBytes()) + "'; Max-Age=10000000");
                }
                else{
                    System.out.println("No cookie. Setting it...");
                    response.setHeader("Set-Cookie", "hello='" + Base64.getEncoder().encodeToString("world".getBytes()) + "'; Max-Age=10000000");
                }
                OUT.write(response.toString().getBytes());
                byte[] buffer = new byte[8192];
                while (IN.read(buffer)!=-1){
                    OUT.write(buffer);
                    OUT.flush();
                }
                OUT.close();
                IN.close();
                return;
            }
        }
        System.out.println("Context doesn't exist" + request.getContextPath());
        // If I am here, it means either context doesn't exist or actual physical file doesn't
        // either way, its 404.
        HttpResponse response = HttpResponse.getNotFoundHttpResponse(request);
        BufferedOutputStream OUT = new BufferedOutputStream(request.getClientSocket().getOutputStream());
        OUT.write(response.toString().getBytes());
        OUT.close();
        System.out.println(request.getClientSocket().isClosed());
    }
}
