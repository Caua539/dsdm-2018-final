package br.ufg.inf.dsdm.caua539.sitpassmobile.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import br.ufg.inf.dsdm.caua539.sitpassmobile.data.DAOs.EventoDAO;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.Converters.DateConverter;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.Entities.Evento;

@Database(entities = {Evento.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class TheDatabase extends RoomDatabase {

    public abstract EventoDAO eventoDAO();

    private static TheDatabase INSTANCE;


    public static TheDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TheDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TheDatabase.class, "database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new CleanDbAsync(INSTANCE).execute();
                }
            };


    private static class CleanDbAsync extends AsyncTask<Void, Void, Void> {

        private final EventoDAO mDao;

        CleanDbAsync(TheDatabase db) {
            mDao = db.eventoDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            return null;
        }
    }
}
