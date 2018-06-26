package br.ufg.inf.dsdm.caua539.sitpassmobile.web;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public abstract class WebConnect {

    private static final String BASE_URL = "http://private-d3b5a6-sitpassmobile.apiary-mock.com";
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private String serviceName;

    public WebConnect(String serviceName){
        this.serviceName = "/"+serviceName;
    }

    public void call(String type) {
        OkHttpClient client = new OkHttpClient();

        String url = getUrl();
        
        Request request = null;
        
        if (type.equals("get")){
            request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
        }
        else if (type.equals("post")){
            RequestBody body = RequestBody.create(JSON, getRequestContent());
            request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();            
        }
        

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new Exception("Verifique sua conexão"));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handleResponse(response);
            }
        });
    }

    public String getUrl(){
        return BASE_URL + serviceName;
    }

    abstract String getRequestContent();

    abstract void handleResponse(Response response);
}
