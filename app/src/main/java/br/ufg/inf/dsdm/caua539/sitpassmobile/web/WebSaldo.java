package br.ufg.inf.dsdm.caua539.sitpassmobile.web;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

public class WebSaldo extends WebConnect {

    private static final String SERVICE = "saldo";
    private String session;

    public WebSaldo() {
        super(SERVICE);
    }

    @Override
    public String getRequestContent() {
        return "";
    }

    @Override
    void handleResponse(Response response) {
        String responseBody = null;
        try {
            responseBody = response.body().string();
            JSONObject object = new JSONObject(responseBody);
            double saldo = (double) object.get("saldo");
            EventBus.getDefault().post(saldo);
        } catch (IOException e) {
            EventBus.getDefault().post(e);
        } catch (JSONException e) {
            EventBus.getDefault().post(new Exception("A resposta do servidor não é válida"));
        }

    }
}
