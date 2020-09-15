package app.dmarts.java.sslserver;
/**
 * Author: Farhan Sabbir Siddique
 * Email: fsabbir@gmail.com
 * Web: github.com/farhansabbir
 */

import app.dmarts.java.lib.Defs;

import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.security.cert.CertificateException;

public class Main {
    public static void main(String[] args) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, KeyManagementException, UnrecoverableKeyException {

        Server server = new Server(Defs.HTTP_SERVER_DEFAULT_PORT,20);
        server.startServer();


    }
}

