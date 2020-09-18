package app.dmarts.java.sslserver;
/**
 * Author: Farhan Sabbir Siddique
 * Email: fsabbir@gmail.com
 * Web: github.com/farhansabbir
 */

import app.dmarts.java.lib.Defs;
import app.dmarts.java.lib.HttpClient;

import javax.net.ssl.SSLEngine;
import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private final Logger LOGGER = Logger.getLogger(Main.class.getName());
    public static void main(String[] args) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, KeyManagementException, UnrecoverableKeyException {

        Server server = new Server(Defs.HTTP_SERVER_DEFAULT_PORT,20);
        server.startServer();
    }

}

