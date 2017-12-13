package com.spktools.emanuel.android_synchronized_clock;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.net.ntp.TimeInfo;

class UpdateTime extends Thread implements Runnable {

    private TextView mTextView = null;
    private ImageView mImageView = null;


    private NTPTime m_ntp_time = null;
    private LocalTime m_local_time = null;
    private boolean m_bFallback = false;
    private long mDiff = 0;
    private long mDeviceTime = 0;
    private int mSyncTimestamp = 0;
    private boolean mPaused = false;
    private Object mPauseLock = null;
    private Drawable mIconOn = null;
    private Drawable mIconOff = null;
    private int mCurrentIcon = 0; // 0 = off, 1 = on

    /**
     * constructor
     *
     * sets the main View where we will render the time
     *
     * @param textView
     */
    public UpdateTime(TextView textView) {
        mPauseLock = new Object();
        mTextView = textView;
    }

    /***
     * set imageview and icon for visualizing if we are synchronized with the NTP source
     *
     * @param imageView
     * @param iconOn
     * @param iconOff
     */
    public void setStatusImage(ImageView imageView, Drawable iconOn, Drawable iconOff) {
        mImageView = imageView;
        mIconOn = iconOn;
        mIconOff = iconOff;
    }

    @Override
    public void run() {

        synchronized (mPauseLock) {
            while (mPaused) {
                try {
                    mPauseLock.wait();
                } catch (InterruptedException e) {
                }
            }
        }

        this.update();
        this.render();
    }

    /**
     * reset timer
     */
    private void reset() {
        mDiff = 0;
        m_bFallback = true;

        if(mImageView != null && mIconOff != null) {
            mImageView.setImageDrawable(mIconOff);
            mCurrentIcon = 0;
        }
    }

    /**
     * Call this on pause.
     */
    public void onPause() {
        System.out.println("UpdateTime:onPause");

        synchronized (mPauseLock) {
            mPaused = true;
        }
    }

    /**
     * Call this on resume.
     */
    public void onResume() {
        System.out.println("UpdateTime:onResume");

        synchronized (mPauseLock) {

            reset();
            mPaused = false;
            mPauseLock.notifyAll();
        }
    }

    /**
     * get instance of NTPTime
     *
     * @return TimeController
     */
    private TimeController getNtpTime() {
        if(m_ntp_time==null) {
            m_ntp_time = new NTPTime();
        }

        return m_ntp_time;
    }

    /**
     *
     * @return TimeController
     */
    private TimeController getLocalTime() {
        if(m_local_time==null) {
            m_local_time = new LocalTime();
        }

        return m_local_time;
    }

    public boolean isFallback() {
        return this.m_bFallback;
    }

    /**
     * update
     *
     * Here it's decided which source to use for time
     */
    private void update() {

        // update the system device time
        mDeviceTime = this.getLocalTime().getTime();

        long remote_time = this.getNtpTime().getTime();

        if(remote_time != 0) {
            mDiff = ((NTPTime)this.getNtpTime()).getDiff();
            System.out.println("UpdateTime:NTP to device time: " +remote_time);
            m_bFallback = false;
        } else {
            mDiff = 0;
            m_bFallback = true;
            System.out.println("UpdateTime:Fallback to device time: ");
        }
    }

    /**
     * Here we update the UI
     */
    private void render() {
        TimeController d = getLocalTime();
        String newTime = d.formatMilitaryTime(mDeviceTime + mDiff);

        //System.out.println("UpdateTime:Render:LocalTime "+localTime);
        //System.out.println("UpdateTime:Render:mDiff "+mDiff);
        //System.out.println("UpdateTime:Render:newTime "+newTime);

        if( mTextView != null ) {
            mTextView.setText(newTime);
        }

        if( mImageView != null) {
            // micro bonus feature, display a green icon if we are connected to NTP
            if( isFallback() && mCurrentIcon != 0 ) {
                mImageView.setImageDrawable(mIconOff);
                mCurrentIcon = 0;
            } else if(!isFallback() && mCurrentIcon != 1) {
                mImageView.setImageDrawable(mIconOn);
                mCurrentIcon = 1;
            }
        }
    }
}
