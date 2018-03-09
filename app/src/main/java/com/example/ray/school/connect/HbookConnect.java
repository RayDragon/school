package com.example.ray.school.connect;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ray.school.data.DManager;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ray on 14/2/18.
 */

public class HbookConnect{
    String origin;
    RequestQueue requestQueue;
    String CSRFToken, cookiesToken,cookie;
    DManager dm;
    Context context;
    boolean processing=false;
    public HbookConnect(Context context, String origin)
    {

        requestQueue = Volley.newRequestQueue(context);
        this.context = context;
        this.origin = origin;
        dm = new DManager(context, "db1.db", null, 1);
        cookie = DManager.get_cookie(context);
        Log("Cookie found, cookie:"+cookie);
        cookiesToken = extract_cookiecsrf(cookie);
        if(cookiesToken==null)
        {
            Log("Cookie not saved, updating from net");
            getCookies();

        }
        else{
            cookie = DManager.get_cookie(context);
            Log("Cookie found, cookie:"+cookie);
            cookiesToken = extract_cookiecsrf(cookie);


        }

    }
    public void getCookies()
    {
        processing=true;
        StringRequest cookieget=new StringRequest(
                Request.Method.GET,
                origin + "get_val",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        CSRFToken = response;
                        processing=false;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        processing=false;
                    }
                }
        ){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                Map<String, String> responseHeaders = response.headers;
                String rawCookies = responseHeaders.get("Set-Cookie");
                if(rawCookies==null){
                    rawCookies = responseHeaders.get("set-cookie");
                }
                Log("rawCook ::"+response.headers);
                if(rawCookies==null){processing=false;return super.parseNetworkResponse(response);}
                DManager.set_cookie(rawCookies, context);
                cookie = DManager.get_cookie(context);
                cookiesToken = extract_cookiecsrf(cookie);
                processing=false;
                return super.parseNetworkResponse(response);

            }
        };
        requestQueue.add(cookieget);

    }
    public void add(
            final int Method,
            final String url,
            final Response.Listener<String> listener,
            final Response.ErrorListener errorListener,
            final Map<String, String> params){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    int i=0;
                    while (processing && i<8){
                        this.wait(100);
                        Log("Still in progress");
                        i+=1000;
                    }
                }catch (Exception e){Log(e.getMessage());}
                finally {
                    requestQueue.add(new StringRequest(Method, url, listener, errorListener){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            if(Method!=Request.Method.POST) return super.getParams();
                            else return params;
                        }

                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            processing=true;
                            params.put("cookie", cookie);
                            params.put("X-CSRFToken", cookiesToken);
                            Log( "__-__c+t:"+cookie+" "+cookiesToken);
                            return params;
                        }
                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            Map<String, String> responseHeaders = response.headers;
                            String rawCookies = responseHeaders.get("Set-Cookie");
                            if(rawCookies==null){
                                rawCookies = responseHeaders.get("set-cookie");
                            }
                            Log("rawCook ::"+response.headers);
                            if(rawCookies==null){processing=false;return super.parseNetworkResponse(response);}
                            DManager.set_cookie(rawCookies, context);
                            cookie = DManager.get_cookie(context);
                            cookiesToken = extract_cookiecsrf(cookie);
                            processing=false;
                            return super.parseNetworkResponse(response);
                        }

                    });
                }
            }
        }).start();

    }
    public void Log(String data){
        Log.d("HBOOKSCHOOL", "HBOOKCONNECT: "+data);
    }
    public String extract_cookiecsrf(String cookie) {
        String[] c=cookie.split(";");
        for(int i=0;i<c.length; i++)
        {
            if(!c[i].contains("=")) continue;
            String[] d=c[i].split("=");
            if(d[0].contains("csrftoken")){
                //cookiesToken = d[1];
                return d[1];
            }
        }
        return null;
    }
}