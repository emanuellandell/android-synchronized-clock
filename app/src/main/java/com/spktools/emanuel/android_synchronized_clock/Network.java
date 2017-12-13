package com.spktools.emanuel.android_synchronized_clock;

import java.io.IOException;
import java.net.InetAddress;

/***
 *
 */
final class Network {

    /***
     * ping
     *
     * @param   hostName
     * @return  bool
     * @TODO    implement more reliable ping functionality
     */
    static boolean ping(String hostName) {
        boolean reachable;

        try {
            reachable = InetAddress.getByName(hostName).isReachable(100);
        } catch(IOException e) {
            return false;
        }

        return reachable;
    }
}
