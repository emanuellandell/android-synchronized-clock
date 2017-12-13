package com.spktools.emanuel.android_synchronized_clock;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/***
 *
 */
final class Network {

    /***
     * ping
     *
     * @param host
     * @return boolean
     *
     * @todo implement more reliable ping functionality
     */
    static boolean ping(String host) {
        boolean reachable;

        try {
            reachable = (boolean)InetAddress.getByName(host).isReachable(100);
        } catch(IOException e) {
            return false;
        }

        return reachable;
    }
}
