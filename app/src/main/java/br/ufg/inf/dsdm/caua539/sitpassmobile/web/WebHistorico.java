package br.ufg.inf.dsdm.caua539.sitpassmobile.web;

import okhttp3.Response;

public class WebHistorico extends WebConnect {
    private static final String SERVICE = "historico";

    public WebHistorico() {
        super(SERVICE);
    }

    @Override
    String getRequestContent() {
        return null;
    }

    @Override
    void handleResponse(Response response) {

    }
}
