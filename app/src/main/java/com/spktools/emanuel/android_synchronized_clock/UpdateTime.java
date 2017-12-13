package com.spktools.emanuel.android_synchronized_clock;

import android.widget.TextView;

class UpdateTime extends Thread implements Runnable {

    private TextView m_view;
    private NTPTime m_ntp_time = null;
    private LocalTime m_local_time = null;
    private boolean m_bFallback = false;

    public void setView(TextView field) {
        m_view = field;
    }

    @Override
    public void run() {
        this.update();
    }

    private TimeController getNtpTime() {
        if(m_ntp_time==null) {
            m_ntp_time = new NTPTime();
        }

        return m_ntp_time;
    }

    private TimeController getLocalTime() {
        if(m_local_time==null) {
            m_local_time = new LocalTime();
        }

        return m_local_time;
    }

    public boolean isFallback() {
        return this.m_bFallback;
    }

    private void update() {

        TimeController d = getLocalTime();

        long current_time = this.getNtpTime().getTime();
        if(current_time == 0) {
            m_bFallback = true;
            System.out.println("UpdateTime:Fallback to device time: ");
            current_time = this.getLocalTime().getTime();
        } else {
            System.out.println("UpdateTime:NTP to device time: ");
            m_bFallback = false;
        }

        String newTime = d.formatMilitaryTime(current_time);
        System.out.println("UpdateTime:Update: "+newTime);

        m_view.setText(newTime);
    }
}
