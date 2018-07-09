package br.ufg.inf.dsdm.caua539.sitpassmobile.data.Entities;



import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;


@Entity
public class Cartao {

    @PrimaryKey
    public int id;
    public String pan;
    public String nome;
    public int cvv;
    public Date validade;
    public String bandeira;

    public Cartao(int id, String pan, String nome, int cvv, Date validade, String bandeira) {
        this.id = id;
        this.pan = pan;
        this.nome = nome;
        this.cvv = cvv;
        this.validade = validade;
        this.bandeira = bandeira;
    }
}
