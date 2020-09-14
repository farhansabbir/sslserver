package app.dmarts.java.sslserver;

import app.dmarts.java.lib.Defs;

import javax.net.ssl.*;
import java.io.*;
import java.net.Socket;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;

/***
 * No one is allowed to extend this class; It should effectively be a singleton, but let's see where it goes
 */

public final class Server implements Runnable{

    private int PORT, BACKLOG;
    private SSLServerSocket sslServerSocket;

    private BufferedReader READER;

    public Server(int port, int backlog) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, IOException, KeyManagementException, KeyStoreException {
        this.BACKLOG = backlog;
        this.PORT = port;
        this.initialize();
    }

    private void initialize() throws IOException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, KeyManagementException, CertificateException {
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

        this.sslServerSocket = (SSLServerSocket) sslContext.getServerSocketFactory().createServerSocket(Defs.HTTP_SERVER_DEFAULT_PORT,20);
        this.sslServerSocket.setEnabledProtocols(new String[]{"TLSv1", "TLSv1.1", "TLSv1.2", "SSLv3"});
    }

    public void startServer(){
        Thread MYSELF = new Thread(this);
        MYSELF.start();
    }

    public void stopServer(){
        try {
            Thread.currentThread().join();
            this.sslServerSocket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // Test code to check client connectivity; we will build on top of this in client thread later
        Socket client = null;
        try {
            client = sslServerSocket.accept();

            this.READER = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("Request: " + this.getRequestLine());
            System.out.println("Header: " + this.getRequestHeaders());
            //System.out.println("Body: " + this.getRequestBody());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            writer.write("HTTP/1.1 200\n\n");
            writer.flush();
            writer.close();
            this.READER.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String readByLine() throws IOException {
        String line = this.READER.readLine();
        if (line==null) return "";
        return line;
        /*
        StringBuilder INIT = new StringBuilder();
        int data;
        while ((data = this.READER.read())>=0) {
            if ((char) data == '\n') {
                break;
            }
            INIT.append((char) data);
        }
        return INIT.toString();
        */
    }

    public String getRequestLine() throws IOException {
        return this.readByLine();
    }

    public HashMap<String, String> getRequestHeaders() throws IOException {
        HashMap<String,String> ret = new HashMap<>();
        String header;
        while((header = this.readByLine()).length()!=0) {
            ret.put(header.split(":")[0].trim(),header.split(":")[1].trim());
        }
        return ret;
    }

    public String getRequestBody() throws IOException {
        StringBuilder body = new StringBuilder();
        while (true){
            System.out.println(this.readByLine().length());
        }
        //return body.toString();
    }
}

