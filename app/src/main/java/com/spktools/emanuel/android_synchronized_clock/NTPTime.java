package com.spktools.emanuel.android_synchronized_clock;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;

/***
 * 
 */
public class NTPTime extends TimeController{

    public long getTime() {
        return this.getTime("pool.ntp.org");
    }

    public long getTime( String hostName ) {

        TimeInfo timeInfo;
        InetAddress hostAddr;

        NTPUDPClient client = new NTPUDPClient();

        try {
            client.open();
        } catch(SocketException e) {
            System.out.println( e.toString() );
            System.out.println("SocketException");
            return 0;
        }

        // use host name or IP address of target NTPTime server
        try {
            hostAddr = InetAddress.getByName(hostName);
        } catch(UnknownHostException e) {
            System.out.println("UnknownHostException");
            return 0;
        }
        try {
            timeInfo = client.getTime(hostAddr);

        } catch(IOException e) {
            System.out.println("IOException");
            return 0;
        }
        timeInfo.computeDetails(); // compute offset/delay if not already done
        Long offsetValue = timeInfo.getOffset();
        Long delayValue = timeInfo.getDelay();
        String delay = (delayValue == null) ? "N/A" : delayValue.toString();
        String offset = (offsetValue == null) ? "N/A" : offsetValue.toString();
        long serverTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();   //server time

        System.out.println(" Roundtrip delay(ms)=" + delay
                + ", clock offset(ms)=" + offset); // offset in ms
        client.close();

        Date time = new Date(serverTime);

        System.out.println("Server Time="+time.toString());

        return time.getTime() / 1000L;
    }
}

