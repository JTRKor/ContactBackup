package com.amazonaws.compute.ap_northeast_2.ec2_54_180_85_255.contactbackup.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.amazonaws.compute.ap_northeast_2.ec2_54_180_85_255.contactbackup.service.BootService;
import com.amazonaws.compute.ap_northeast_2.ec2_54_180_85_255.contactbackup.service.DateService;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            int api = Build.VERSION.SDK_INT;
            Intent intent1 = new Intent(context, BootService.class);
            if (api >= 26) {
                context.startForegroundService(intent1);
            }
            else {
                context.startService(intent1);
            }

        }
    }
}
