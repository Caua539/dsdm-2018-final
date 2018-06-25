package br.ufg.inf.dsdm.caua539.sitpassmobile.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Calendar;

@Table(name = "Eventos")
public class DBEventos extends Model {

    @Column(name = "Codigo")
    public long codigo;

    @Column(name = "Local")
    public String local;

    @Column(name = "Valor")
    public double valor;

    @Column(name = "Data")
    public Calendar dia;

    public DBEventos(long codigo, String local, double valor, Calendar dia) {
        super();
        this.codigo = codigo;
        this.local = local;
        this.valor = valor;
        this.dia = dia;
    }
}
