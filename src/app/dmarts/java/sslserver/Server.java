package app.dmarts.java.sslserver;
/**
 * Author: Farhan Sabbir Siddique
 * Email: fsabbir@gmail.com
 * Web: github.com/farhansabbir
 */

import app.dmarts.java.lib.Defs;
import app.dmarts.java.lib.HttpClient;
import javax.net.ssl.*;
import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/***
 * No one is allowed to extend this class; It should effectively be a singleton, but let's see where it goes
 */

public final class Server implements Runnable{

    private int PORT, BACKLOG;
    private SSLServerSocket sslServerSocket;
    ExecutorService CLIENTTHREADPOOL;
    public static java.util.concurrent.ConcurrentHashMap<String, String> CONTEXTS = new ConcurrentHashMap<>(); // might need to consider to have nio watcher to add new folders in context, hence concurrenthashmap

    private BufferedReader READER;

    public Server(int port, int backlog) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, IOException, KeyManagementException, KeyStoreException {
        this.BACKLOG = backlog;
        this.PORT = port;
        this.initialize();
    }

    private void initialize() throws IOException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, KeyManagementException, CertificateException {
        DirectoryStream<Path> dirs = Files.newDirectoryStream(Paths.get("www"));
        CONTEXTS.put("/",Paths.get("www").toAbsolutePath().toString());
        for(Path dir: dirs){
            CONTEXTS.put("/" + dir.getFileName(), dir.toAbsolutePath().toString());
        }
        System.setProperty("sun.security.ssl.allowUnsafeRenegotiation","true");
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream("keystore.jks"),"123456".toCharArray());
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(ks,"123456".toCharArray());

        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(keyManagerFactory.getKeyManagers(), new TrustManager[]{ new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }}, new SecureRandom());

        this.sslServerSocket = (SSLServerSocket) sslContext.getServerSocketFactory().createServerSocket(Defs.HTTP_SERVER_DEFAULT_PORT,Defs.HTTP_CLIENT_BACKLOG);
        this.sslServerSocket.setEnabledProtocols(new String[]{"TLSv1", "TLSv1.1", "TLSv1.2", "SSLv3"});
    }

    public void startServer(){
        Thread MYSELF = new Thread(this);
        MYSELF.start();
        this.CLIENTTHREADPOOL = Executors.newFixedThreadPool(Defs.HTTP_CLIENT_BACKLOG);
    }

    public void stopServer(){
        try {
            this.CLIENTTHREADPOOL.shutdown();
            Thread.currentThread().join();
            this.sslServerSocket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // Test code to check client connectivity; we will build on top of this in client thread later

        while (true)
        try {
            HttpClient httpClient = new HttpClient(sslServerSocket.accept());
            Future task = this.CLIENTTHREADPOOL.submit(httpClient);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

