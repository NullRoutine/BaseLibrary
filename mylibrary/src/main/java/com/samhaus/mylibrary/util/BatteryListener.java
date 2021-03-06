package com.samhaus.mylibrary.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by samhaus on 2017/9/13.
 * 电源监听广播
 */

public class BatteryListener {
    private Context mContext;

    private BatteryBroadcastReceiver receiver;

    private BatteryStateListener mBatteryStateListener;

    public BatteryListener(Context context) {
        mContext = context;
        receiver = new BatteryBroadcastReceiver();
    }

    public void register(BatteryStateListener listener) {
        mBatteryStateListener = listener;
        if (receiver != null) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_BATTERY_CHANGED);
            filter.addAction(Intent.ACTION_BATTERY_LOW);
            filter.addAction(Intent.ACTION_BATTERY_OKAY);
            filter.addAction(Intent.ACTION_POWER_CONNECTED);
            filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
            mContext.registerReceiver(receiver, filter);
        }
    }

    public void unregister() {
        if (receiver != null) {
            mContext.unregisterReceiver(receiver);
        }
    }


    public class BatteryBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String acyion = intent.getAction();
                switch (acyion) {
                    case Intent.ACTION_BATTERY_CHANGED://电量发生改变
                        if (mBatteryStateListener != null) {
                            mBatteryStateListener.onStateChanged();
                        }
                        break;
                    case Intent.ACTION_BATTERY_LOW://电量低
                        if (mBatteryStateListener != null) {
                            mBatteryStateListener.onStateLow();
                        }
                        break;
                    case Intent.ACTION_BATTERY_OKAY://电量充满
                        if (mBatteryStateListener != null) {
                            mBatteryStateListener.onStateOkay();
                        }
                        break;
                    case Intent.ACTION_POWER_CONNECTED://接通电源
                        if (mBatteryStateListener != null) {
                            mBatteryStateListener.onStatePowerConnected();
                        }
                        break;

                    case Intent.ACTION_POWER_DISCONNECTED://拔出电源
                        if (mBatteryStateListener != null) {
                            mBatteryStateListener.onStatePowerDisconnected();
                        }
                        break;
                }
            }
        }
    }

    public interface BatteryStateListener {
        public void onStateChanged();

        public void onStateLow();

        public void onStateOkay();

        public void onStatePowerConnected();

        public void onStatePowerDisconnected();
    }
}
