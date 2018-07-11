package br.ufg.inf.dsdm.caua539.sitpassmobile.data.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Cartao {

    @PrimaryKey
    public int id;
    public String pan;
    public String end;
    public String nome;
    public String cvv;
    public String validade;

    public Cartao(int id, String pan, String nome, String end, String cvv, String validade) {
        this.id = id;
        this.pan = pan;
        this.end = end;
        this.nome = nome;
        this.cvv = cvv;
        this.validade = validade;
    }
    @Ignore
    public Cartao (int id){
        this.id = id;
    }
}
