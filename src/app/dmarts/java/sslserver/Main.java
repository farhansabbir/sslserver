package app.dmarts.java.sslserver;

import app.dmarts.java.lib.Defs;

import javax.net.ssl.*;
import java.io.*;
import java.net.Socket;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, KeyManagementException, UnrecoverableKeyException {

        Server server = new Server(Defs.HTTP_SERVER_DEFAULT_PORT,20);
        server.startServer();
        /*
        Socket client = sslServerSocket.accept();
        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        HashMap<String, String> headers = new HashMap();
        HashMap<String, String> body = new HashMap();
        StringBuilder INIT = new StringBuilder();
        try {
            int data;
            while ((data = reader.read())>=0){
                if ((char)data == '\n') break;
                INIT.append((char)data);
            }
        }catch (javax.net.ssl.SSLHandshakeException hex){
            System.err.println(hex);
        }

        System.out.println(INIT.toString());
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        writer.write("HTTP/1.2 200\n\n");
        writer.flush();


        writer.close();
        reader.close();
        client.close();
*/


    }
}

final class Server implements Runnable{

    private int PORT, BACKLOG;
    private SSLServerSocket sslServerSocket;
    public Server(int port, int backlog) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, IOException, KeyManagementException, KeyStoreException {
        this.BACKLOG = backlog;
        this.PORT = port;
        this.inintializeServer();
    }

    private void inintializeServer() throws IOException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, KeyManagementException, CertificateException {
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
            this.sslServerSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void run() {
        Socket client = null;
        try {
            client = sslServerSocket.accept();

            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

            HashMap<String, String> headers = new HashMap();
            HashMap<String, String> body = new HashMap();
            StringBuilder INIT = new StringBuilder();

            int data;
            while ((data = reader.read())>=0) {
                if ((char) data == '\n') break;
                INIT.append((char) data);
            }

            System.out.println(INIT.toString());

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            writer.write("HTTP/1.2 200\n\n");
            writer.flush();
            writer.close();
            reader.close();
            client.close();
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
}
