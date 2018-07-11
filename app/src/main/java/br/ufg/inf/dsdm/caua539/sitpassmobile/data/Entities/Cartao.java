package br.ufg.inf.dsdm.caua539.sitpassmobile.data.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Cartao {

    public static int geraId = 0;
    @PrimaryKey
    public int id;
    public String pan;
    public String end;
    public String nome;
    public int cvv;
    public String validade;

    public Cartao(String pan, String nome, String end, int cvv, String validade) {
        geraId++;
        this.id = geraId;
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
