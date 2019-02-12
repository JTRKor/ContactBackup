package com.amazonaws.compute.ap_northeast_2.ec2_54_180_85_255.contactbackup;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.compute.ap_northeast_2.ec2_54_180_85_255.contactbackup.adapter.AddressAdapter;
import com.amazonaws.compute.ap_northeast_2.ec2_54_180_85_255.contactbackup.data.AddressOne;
import com.amazonaws.compute.ap_northeast_2.ec2_54_180_85_255.contactbackup.db.AddressDBmanager;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {
    ArrayList<AddressOne> addressOnes = new ArrayList<>();
    AddressDBmanager addressDBmanager;
    AddressAdapter addressAdapter;
    EditText et_search;
    ImageView iv_search;
    ListView lv;
    TextView tv_total;
    TextView am1_address_tv;

    public class DBTask extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            addressDBmanager = new AddressDBmanager(MainActivity.this, "addrdb.db", null, 1);
            am1_address_tv = findViewById(R.id.am1_address_tv);
            am1_address_tv.setText("주소록 로딩중...");
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
            Toast.makeText(MainActivity.this, "ContactBackup Complete", Toast.LENGTH_LONG).show();
            refresh();
            am1_address_tv.setText("주소록");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_search = findViewById(R.id.am1_et_search);
        iv_search = findViewById(R.id.am1_iv_search);
        lv = findViewById(R.id.am1_lv);
        tv_total = findViewById(R.id.am1_address_tv_total);
        addressAdapter = new AddressAdapter(addressOnes);
        lv.setAdapter(addressAdapter);
        addressDBmanager = new AddressDBmanager(MainActivity.this, "addrdb.db", null, 1);

        refresh();

        DBTask dbTask = new DBTask();

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                DBTask dbTask = new DBTask();
                dbTask.execute();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        TedPermission.with(MainActivity.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_CONTACTS)
                .check();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                addressOnes.clear();
                addressOnes.addAll(addressDBmanager.getDataSearchListAddr(s.toString()));
                tv_total.setText("total : " + addressOnes.size());
                addressAdapter.notifyDataSetChanged();
            }
        };
        et_search.addTextChangedListener(textWatcher);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("name", addressOnes.get(i).getName());
                intent.putExtra("number", addressOnes.get(i).getNumber());
                startActivity(intent);
            }
        });
    }

    public void refresh() {
        addressOnes.clear();
        addressOnes.addAll(addressDBmanager.getDataListAddr());
        tv_total.setText("total : " + addressOnes.size());
        addressAdapter.notifyDataSetChanged();
    }
}