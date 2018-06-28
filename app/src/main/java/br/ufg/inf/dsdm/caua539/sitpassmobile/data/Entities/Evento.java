package br.ufg.inf.dsdm.caua539.sitpassmobile.data.Entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class Evento {

    @PrimaryKey
    public int codigo;
    public String local;
    public double valor;
    public Date dia;

    // True para recarga, False para viagens
    public boolean tipo;

    public Evento(int codigo, String local, double valor, Date dia, boolean tipo) {
        this.codigo = codigo;
        this.local = local;
        this.valor = valor;
        this.dia = dia;
        this.tipo = tipo;
    }
}
