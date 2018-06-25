package br.ufg.inf.dsdm.caua539.sitpassmobile.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

@Table(name="Cartoes")
public class DBCartoes extends Model {

    @Column(name="Nome")
    public String nome;

    @Column(name="Numero")
    public long numero;

    @Column(name="CVV")
    public int digito;

    @Column(name="Titular")
    public String titular;

    @Column(name="Vencimento")
    public Date vencimento;
}
