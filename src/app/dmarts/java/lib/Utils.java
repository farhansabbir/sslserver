package app.dmarts.java.lib;
/**
 * Author: Farhan Sabbir Siddique
 * Email: fsabbir@gmail.com
 * Web: github.com/farhansabbir
 */

import com.google.gson.JsonObject;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

public class Utils {
    public static String getUUID() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InetAddress[] address = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());
            for (InetAddress add : address) {
                stringBuilder.append(add.toString());
            }
        }catch (UnknownHostException unknownhostex){
            System.err.println(unknownhostex);
        }
        stringBuilder.append(Runtime.getRuntime().totalMemory());
        return UUID.nameUUIDFromBytes(stringBuilder.toString().getBytes()).toString();
    }

    public static JsonObject getUUIDAsJson(){
        JsonObject ret = new JsonObject();
        ret.addProperty("UUID",Utils.getUUID());
        return ret;
    }
}
