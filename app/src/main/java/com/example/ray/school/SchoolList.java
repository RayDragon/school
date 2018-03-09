package com.example.ray.school;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.ray.school.connect.HbookConnect;
import com.example.ray.school.data.DManager;
import com.example.ray.school.mDrawable.MDrawable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Vector;

public class SchoolList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final LinearLayout list = (LinearLayout)findViewById(R.id.listll);
        /*
        *   Get all the Schools created by the user from website
        **/
        HbookConnect hb = new HbookConnect(this, "http://192.168.43.182:8000");
        hb.add( Request.Method.GET,
                "http://192.168.43.182:8000/apischool/schools/?format=json",
                new Response.Listener<String>()
                {

                    @Override
                    public void onResponse(String response) {
                        Log("got:"+response);
                        try {
                            JSONArray school_list = new JSONArray(response);
                            for(int i=0;i<school_list.length();i++)
                            {
                                final JSONObject school = school_list.getJSONObject(i);
                                final String sname=school.getString("name");
                                Log(sname);

                                LinearLayout ll = MDrawable.getSimpleClickable(
                                        SchoolList.this,
                                        sname,
                                        new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Log("clicked on "+sname);
                                                Intent i = new Intent(SchoolList.this, Syncronize.class);
                                                startActivity(i);
                                            }
                                        }
                                );
                                list.addView(ll);
                            }
                        }
                        catch (Exception e)
                        {
                            DManager.Log(e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log("got e:"+error.getMessage());
                    }
                },
                null

        );

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MDrawable.AlertDialogForm(SchoolList.this){
                    @Override
                    public void onSubmit(Vector<String> values) {
                        for(int i=0;i<values.size();i++)
                        {
                            Log.d("HBOOKSCHOOL", values.get(i));
                        }

                    }
                }
                .addItem(0,1, "School Name")
                .build("Create School", "Create").show();
            }
        });

    }
    private void Log(String s)
    {
        Log.d("HBOOKSCHOOL","schoollist_"+s);
    }

}
