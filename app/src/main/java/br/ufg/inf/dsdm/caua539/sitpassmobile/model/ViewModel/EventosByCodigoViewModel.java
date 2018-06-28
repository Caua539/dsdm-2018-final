package br.ufg.inf.dsdm.caua539.sitpassmobile.model.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import br.ufg.inf.dsdm.caua539.sitpassmobile.data.Entities.Evento;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.TheDatabase;

public class EventosByCodigoViewModel extends AndroidViewModel {

    private TheDatabase busdb;
    public final LiveData<List<Evento>> eventos;

    public EventosByCodigoViewModel(@NonNull Application application) {
        super(application);
        createDb();

        eventos = busdb.eventoDAO().listAllEventosByCodigo();
    }

    public void createDb() {
        busdb = TheDatabase.getDatabase(this.getApplication());
    }
}
