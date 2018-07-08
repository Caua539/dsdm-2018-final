package br.ufg.inf.dsdm.caua539.sitpassmobile.data.DAOs;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import br.ufg.inf.dsdm.caua539.sitpassmobile.data.Entities.Evento;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface EventoDAO {

    @Query("select * from Evento")
    LiveData<List<Evento>> listAllEventos();

    @Query("select * from Evento order by codigo desc")
    LiveData<List<Evento>> listAllEventosByCodigo();

    @Query("SELECT * FROM Evento WHERE codigo=:codigo")
    Evento getEventoByCodigo(int codigo);

    @Query("DELETE FROM Evento")
    void deleteAll();

    @Insert(onConflict = IGNORE)
    void insertEvento(Evento evento);
}
