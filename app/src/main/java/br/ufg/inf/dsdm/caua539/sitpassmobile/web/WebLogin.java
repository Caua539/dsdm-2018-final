package br.ufg.inf.dsdm.caua539.sitpassmobile.web;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import br.ufg.inf.dsdm.caua539.sitpassmobile.data.Entities.Usuario;
import okhttp3.Response;

public class WebLogin extends WebConnect {

    private static final String SERVICE = "login";
    private String cpf;
    private String senha;

    public WebLogin(String cpf, String senha) {
        super(SERVICE);
        this.cpf = cpf;
        this.senha = senha;
    }

    @Override
    public String getRequestContent() {
        Map<String,String> requestMap = new HashMap<>();
        requestMap.put("cpf", cpf);
        requestMap.put("senha", senha);

        JSONObject json = new JSONObject(requestMap);
        String jsonString = json.toString();

        return  jsonString;
    }

    @Override
    void handleResponse(Response response) {
        String responseBody = null;
        try {
            responseBody = response.body().string();
            Usuario user = new Usuario();
            JSONObject object = new JSONObject(responseBody);
            user.setNome(object.getString("name"));
            user.setProfilepicurl(object.getString("profilepic"));
            user.setCpf(cpf);
            user.setSession(object.getString("session"));
            EventBus.getDefault().post(user);
        } catch (IOException e) {
            EventBus.getDefault().post(e);
        } catch (JSONException e) {
            EventBus.getDefault().post(new Exception("A resposta do servidor não é válida"));
        }

    }
}
