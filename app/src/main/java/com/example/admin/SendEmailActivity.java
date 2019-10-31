package com.example.admin;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SendEmailActivity extends AppCompatActivity {
    EditText etTo,etSubject,etMessage;
    Button btSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        getSupportActionBar().setTitle("Sent  Email");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etTo=(EditText)findViewById(R.id.et_to);
        etSubject=(EditText)findViewById(R.id.et_subject);
        etMessage=(EditText)findViewById(R.id.et_message);

        btSend=(Button)findViewById(R.id.btn_send);

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW
                        , Uri.parse("mailto:"+etTo.getText().toString()));
                intent.putExtra(Intent.EXTRA_SUBJECT,etSubject.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT,etMessage.getText().toString());
                startActivity(intent);
            }
        });
    }
}
