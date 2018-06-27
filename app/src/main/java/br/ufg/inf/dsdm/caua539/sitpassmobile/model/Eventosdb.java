package br.ufg.inf.dsdm.caua539.sitpassmobile.model;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.util.Date;
import java.util.List;

public class Eventosdb extends SugarRecord{

    @Unique
    public int codigo;
    public String local;
    public double valor;
    public Date dia;

    // True para recarga, False para viagens
    public boolean tipo;

    public Eventosdb(){

    }

    public Eventosdb(int codigo, String local, double valor, Date dia, boolean tipo) {
        super();
        this.codigo = codigo;
        this.local = local;
        this.valor = valor;
        this.dia = dia;
        this.tipo = tipo;
    }
}
