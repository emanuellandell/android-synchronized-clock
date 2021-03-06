package com.spktools.emanuel.android_synchronized_clock;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;


/***
 * NTPTime
 *
 * this class is managing our NTP requests
 */
public class NTPTime implements TimeInterface {

    private NTPUDPClient m_client = null;
    private static final String m_ntp_host = "pool.ntp.org";

    private long mDiff = 0;
    private long mLastSync = 0;
    private int mSyncInterval = 60;

    // to avoid injecting bad NTP time, we reject any time fixes that differ from system time
    // by more than 5 minutes.
    private static final long MAX_NTP_SYSTEM_TIME_OFFSET = 5*60*1000;

    /***
     *
     * @return long
     */
    public long getTime() {
        return this.getTime(m_ntp_host);
    }

    /***
     * return instance of NTPUDPClient
     *
     * @return long
     */
    private NTPUDPClient getClient() {

        if(m_client==null) {
            m_client = new NTPUDPClient();
        }

        m_client.setDefaultTimeout(1000);

        return m_client;
    }

    /**
     * opens a connection and sends an request to NTP server for an TimeInfo object
     *
     * @param hostName
     * @return TimeInfo
     */
    protected TimeInfo getTimeInfo(String hostName) {

        try {
            this.getClient().open();
        } catch(SocketException e) {
            System.out.println( e.toString() );
            System.out.println("SocketException");
            return null;
        }

        InetAddress hostAddress;
        TimeInfo timeInfo = null;

        // use host name or IP address of target NTPTime server
        try {
            hostAddress = InetAddress.getByName(hostName);
        } catch(UnknownHostException e) {
            System.out.println("UnknownHostException");
            return null;
        }

        try {
            timeInfo = this.getClient().getTime(hostAddress);
        } catch(IOException e) {
            System.out.println("IOException");
            return null;
        }

        this.getClient().close();

        return timeInfo;
    }

    /**
     * lastUpdated
     *
     * returns time in seconds when we requested ntp time last
     *
     * @return
     */
    private long lastUpdated() {
        long local = System.currentTimeMillis();

        if((int)((local - mLastSync) / 1000L)>mSyncInterval) {
            return (mLastSync = 0);
        }

        return (local - mLastSync) / 1000L;
    }

    /**
     * getDiff
     *
     * calculates the differance between system clock and ntp device
     *
     * @return long
     */
    public long getDiff() {

        long lastUpdated = lastUpdated();
        System.out.println( "lastUpdated: " +lastUpdated );

        if(lastUpdated > 0 && lastUpdated < mSyncInterval) {
            System.out.println( "returning cached value" );
            return mDiff;
        }

        mDiff = 0;

        System.out.println( "returning cached value" );

        long remoteTime = 0;
        TimeInfo ti = getTimeInfo("pool.ntp.org");
        if(ti != null) {
            remoteTime = ti.getMessage().getTransmitTimeStamp().getTime();
        }

        return this.getDiff(System.currentTimeMillis(),  remoteTime);
    }


    /**
     * getDiff
     *
     * @param local
     * @param remote
     * @return long
     */
    private long getDiff(long local, long remote) {

        mLastSync = local;

        if(mDiff>MAX_NTP_SYSTEM_TIME_OFFSET) {
            return (mDiff = 0);
        }

        return (mDiff = remote - local);
    }

    /**
     * getTime
     *
     * @param hostName NTP server
     * @return long
     */
    private long getTime( String hostName ) {

        TimeInfo timeInfo = this.getTimeInfo(hostName);
        if(timeInfo == null) {
            System.out.println("NTPTime:getTime:0");
            return 0;
        }

        // compute offset/delay
        timeInfo.computeDetails();
        long offsetValue = timeInfo.getOffset();
        long delayValue = timeInfo.getDelay();

        return timeInfo.getMessage().getTransmitTimeStamp().getTime();
    }
}

