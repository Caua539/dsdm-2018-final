package br.ufg.inf.dsdm.caua539.sitpassmobile.model.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import br.ufg.inf.dsdm.caua539.sitpassmobile.data.Entities.Cartao;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.TheDatabase;

public class CartoesViewModel extends AndroidViewModel {

    private TheDatabase busdb;
    public final LiveData<List<Cartao>> cartoes;

    public CartoesViewModel (@NonNull Application application) {
        super(application);
        createDb();

        cartoes = busdb.cartaoDAO().listAllCartoes();
    }

    public void createDb() {
        busdb = TheDatabase.getDatabase(this.getApplication());
    }

}
