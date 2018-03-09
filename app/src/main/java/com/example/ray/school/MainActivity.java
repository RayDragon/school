package com.example.ray.school;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ray.school.connect.ConnManager;
import com.example.ray.school.connect.HbookConnect;
import com.example.ray.school.data.DManager;

import java.util.Map;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        ((TextView)findViewById(R.id.maintv1)).setText(i.getStringExtra("email"));
        String id = i.getStringExtra("id");
        final HbookConnect hb = new HbookConnect(this, "http://192.168.43.182:8000/");
        Button bt1 = (Button)findViewById(R.id.mainBt1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"clicked",Toast.LENGTH_LONG).show();
                new ConnManager.check_login_status(MainActivity.this){
                    @Override
                    public void onResult(boolean logged) {
                        if(logged)
                            Toast.makeText(MainActivity.this,"yess we are logged",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"nope not logged",Toast.LENGTH_LONG).show();
                    }
                };
            }
        });
    }



}
