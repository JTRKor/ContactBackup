package com.amazonaws.compute.ap_northeast_2.ec2_54_180_85_255.contactbackup.service;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.compute.ap_northeast_2.ec2_54_180_85_255.contactbackup.data.AddressOne;
import com.amazonaws.compute.ap_northeast_2.ec2_54_180_85_255.contactbackup.db.AddressDBmanager;

public class BootService extends Service {
    private IBinder binder = new LocalBinder();
    AddressDBmanager addressDBmanager;

    public class LocalBinder extends Binder {
        public BootService getService() {
            return BootService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class BSTask extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            addressDBmanager = new AddressDBmanager(BootService.this, "addrdb.db", null, 1);
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            String[] colList = new String[]{ContactsContract.Contacts._ID,ContactsContract.Contacts.DISPLAY_NAME_PRIMARY};
            String[] argList = new String[]{};

            Cursor c = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                    colList,
                    null,
                    argList,
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " asc");

            while(c.moveToNext()) {
                String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));

                Cursor phoneCursor = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                        null, null);

                String number = "";
                if (phoneCursor.moveToNext()) {
                    number = phoneCursor.getString(phoneCursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                number = number.replace("-", "");
                AddressOne dbin = new AddressOne(name, number);
                addressDBmanager.insertData(dbin);
                phoneCursor.close();
            }
            c.close();
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            Toast.makeText(BootService.this, "Boot ContactBackup Complete", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        BSTask dbTask = new BSTask();
        dbTask.execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
