package com.amazonaws.compute.ap_northeast_2.ec2_54_180_85_255.contactbackup;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {
    TextView detail_tv_name;
    TextView detail_tv_number;
    ImageView detail_iv_call;
    ImageView detail_iv_sms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        detail_tv_name = findViewById(R.id.detail_tv_name);
        detail_tv_number = findViewById(R.id.detail_tv_number);
        detail_iv_call = findViewById(R.id.detail_iv_call);
        detail_iv_sms = findViewById(R.id.detail_iv_sms);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        final String number = intent.getStringExtra("number");

        detail_tv_name.setText(name);
        detail_tv_number.setText(number);

        detail_tv_number.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipboardManager clip = (ClipboardManager)DetailActivity.this.getSystemService(DetailActivity.this.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("label", number);
                clip.setPrimaryClip(clipData);
                Toast.makeText(DetailActivity.this,"전화번호가 복사 되었습니다.",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        detail_iv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callintent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + number));
                startActivity(callintent);
            }
        });

        detail_iv_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent smsintent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:" + number));
                //smsintent.putExtra("sms_body", "Press send to send me");
                //sms_body는 키값으로 해당 키값 뒤에 넣는 String이 메세지 내용에 자동으로 들어간다.
                startActivity(smsintent);
            }
        });
    }
}
