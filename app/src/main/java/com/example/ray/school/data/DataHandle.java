package com.example.ray.school.data;

import android.content.Context;

/**
 * Created by ray on 8/3/18.
 */

public class DataHandle
{
    public static boolean check_saved_login(Context context)
    {
        DManager dm = getD(context);
        String logged=dm.get_setting("login");
        if(logged==null) return false;
        if(logged.equals("true"))
        {
            return true;
        }
        return false;

    }
    public static void change_saved_login(Context context, boolean status)
    {
        DManager dm = getD(context);
        if(status) dm.save_setting("login", "true");
        else dm.save_setting("login", "false");
    }
    private static DManager getD(Context c)
    {
        return new DManager(c, "db1.db", null, 1);
    }
}
