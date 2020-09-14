package app.dmarts.java.sslserver;

import app.dmarts.java.lib.Defs;

import javax.net.ssl.*;
import java.io.*;
import java.net.Socket;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class Main {
    public static void main(String[] args) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, KeyManagementException, UnrecoverableKeyException {

        Server server = new Server(Defs.HTTP_SERVER_DEFAULT_PORT,20);
        server.startServer();



    }
}

