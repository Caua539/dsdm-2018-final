package br.ufg.inf.dsdm.caua539.sitpassmobile.web;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.ufg.inf.dsdm.caua539.sitpassmobile.model.Eventosdb;
import okhttp3.Response;

public class WebHistorico extends WebConnect {
    private static final String SERVICE = "historico";

    public WebHistorico() {
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
            JSONArray viagens = object.getJSONArray("viagens");
            JSONArray recargas = object.getJSONArray("recargas");

            extractEventoFromJson(recargas, true);
            extractEventoFromJson(viagens, false);

            EventBus.getDefault().post("Histórico Recuperado!");

        } catch (IOException e) {
            EventBus.getDefault().post(e);
        } catch (JSONException e) {
            EventBus.getDefault().post(new Exception("A resposta do servidor não é válida"));
        }

    }

    void extractEventoFromJson (JSONArray json, boolean tipo) {

        try {
            for (int i = 0; i < json.length(); i++){
                JSONObject item = (JSONObject) json.get(i);
                String local = item.getString("local");
                double valor = item.getDouble("valor");
                Date data = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(item.getString("data"));
                Eventosdb evento = new Eventosdb(local, valor, data, tipo);
                evento.save();
            }
        } catch (ParseException e) {
            EventBus.getDefault().post(new Exception("A resposta do servidor não é válida"));
        } catch (JSONException e) {
            EventBus.getDefault().post(new Exception("A resposta do servidor não é válida"));
        }

    }
}
