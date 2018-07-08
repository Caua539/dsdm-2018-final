package br.ufg.inf.dsdm.caua539.sitpassmobile.web;

import android.content.Context;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

import br.ufg.inf.dsdm.caua539.sitpassmobile.data.Converters.DateConverter;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.Entities.Evento;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.TheDatabase;
import okhttp3.Response;

public class WebHistorico extends WebConnect {
    private static final String SERVICE = "historico";
    private TheDatabase busdb;

    public WebHistorico(Context context) {
        super(SERVICE);
        createDb(context);
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

    public void createDb(Context context){
        busdb = (TheDatabase) TheDatabase.getDatabase(context);
    }

    void extractEventoFromJson (JSONArray json, boolean tipo) {

        try {
            for (int i = 0; i < json.length(); i++){
                JSONObject item = (JSONObject) json.get(i);
                int codigo = item.getInt("codigo");
                String local = item.getString("local");
                double valor = item.getDouble("valor");
                Date data = DateConverter.toDate(item.getString("data"));

                if (busdb.eventoDAO().getEventoByCodigo(codigo) != null) {
                    continue;
                }
                Evento evento = new Evento(codigo, local, valor, data, tipo);
                busdb.eventoDAO().insertEvento(evento);

            }
        }  catch (JSONException e) {
            EventBus.getDefault().post(new Exception("A resposta do servidor não é válida"));
        }

    }
}
