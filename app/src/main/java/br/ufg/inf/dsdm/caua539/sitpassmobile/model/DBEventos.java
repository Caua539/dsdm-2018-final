package br.ufg.inf.dsdm.caua539.sitpassmobile.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

@Table(name = "Eventos")
public class DBEventos extends Model {

    @Column(name = "Nome")
    public String nome;

    @Column(name = "Local")
    public String local;

    @Column(name = "Valor")
    public double valor;

    @Column(name = "Data")
    public Date dia;

    @Column(name = "Horario")
    public Date horario;


}
