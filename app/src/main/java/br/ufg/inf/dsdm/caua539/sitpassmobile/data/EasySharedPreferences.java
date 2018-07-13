package br.ufg.inf.dsdm.caua539.sitpassmobile.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class EasySharedPreferences {

    public static String KEY_NAME = "nome";
    public static String KEY_CPF = "cpf";
    public static String KEY_SALDO = "saldo";
    public static String KEY_SESSION = "session";
    public static String KEY_LOGGEDIN = "loggedin";
    public static String KEY_IMGURL = "imgurl";

    public static String getStringFromKey(Context context, String key){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(key,"");
    }

    public static void setStringToKey(Context context, String key, String value){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key,value);
        editor.commit();
    }


    public static double getDoubleFromKey(Context context, String key) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return Double.longBitsToDouble(pref.getLong(key, Double.doubleToLongBits(0.00)));
    }

    public static void setDoubleToKey(Context context, String key, double value){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(key,Double.doubleToRawLongBits(value));
        editor.commit();
    }



    public static void setBooleanToKey(Context context, String key, boolean value){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    public static boolean getBooleanFromKey(Context context, String key){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getBoolean(key, false);
    }
}
