package br.ufg.inf.dsdm.caua539.sitpassmobile.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;


import br.ufg.inf.dsdm.caua539.sitpassmobile.data.DAOs.CartaoDAO;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.DAOs.EventoDAO;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.Converters.DateConverter;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.Entities.Cartao;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.Entities.Evento;

@Database(entities = {Evento.class, Cartao.class}, version = 9)
@TypeConverters(DateConverter.class)
public abstract class TheDatabase extends RoomDatabase {

    public abstract EventoDAO eventoDAO();
    public abstract CartaoDAO cartaoDAO();

    private static TheDatabase INSTANCE;


    public static TheDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TheDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TheDatabase.class, "database")
                            .fallbackToDestructiveMigration()
                            .build();

                }
            }
        }
        return INSTANCE;
    }

}
