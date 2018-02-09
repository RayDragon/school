package com.example.ray.school.mDrawable;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ray.school.R;

/**
 * Created by ray on 8/2/18.
 */

public class MDrawable
{
    public static View getSimpleClickable(Context context, String string, View.OnClickListener ls){
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.___simple_clickable, null);
        TextView textView = (TextView) v.findViewById(R.id.simpleclblTv);
        textView.setText(string);
        v.setOnClickListener(ls);
        return v;
    }
}
