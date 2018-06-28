package br.ufg.inf.dsdm.caua539.sitpassmobile.data.Converters;

import android.arch.persistence.room.TypeConverter;

import org.greenrobot.eventbus.EventBus;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    @TypeConverter
    public static Date toDate(String s) {
        try {
            return new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(s);
        } catch (ParseException e) {
            EventBus.getDefault().post(new Exception("A resposta do servidor não é válida"));
            return null;
        }
    }

    @TypeConverter
    public static String toString(Date d){

        return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(d);
    }
}
