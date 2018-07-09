package br.ufg.inf.dsdm.caua539.sitpassmobile.data.DAOs;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import br.ufg.inf.dsdm.caua539.sitpassmobile.data.Entities.Cartao;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface CartaoDAO {

    @Query("select * from Cartao ORDER BY id asc")
    LiveData<List<Cartao>> listAllCartoes();

    @Query("SELECT * FROM Cartao WHERE pan=:pan")
    Cartao getCartaoByCodigo(String pan);

    @Query("DELETE FROM Cartao")
    void deleteAll();

    @Insert(onConflict = IGNORE)
    void insertCartao(Cartao cartao);
}