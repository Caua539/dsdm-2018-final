package br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import br.ufg.inf.dsdm.caua539.sitpassmobile.R;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.EasySharedPreferences;
import br.ufg.inf.dsdm.caua539.sitpassmobile.model.FormProblemException;
import br.ufg.inf.dsdm.caua539.sitpassmobile.model.Usuario;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.BaseActivity;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.home.HomeActivity;
import br.ufg.inf.dsdm.caua539.sitpassmobile.web.WebLogin;

public class LoginActivity extends BaseActivity {

    private final int MIN_PASSWORD = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setStringFromEdit(R.id.input_email,EasySharedPreferences.getStringFromKey(
                this, EasySharedPreferences.KEY_CPF));
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        if (EasySharedPreferences.getBooleanFromKey(
                this, EasySharedPreferences.KEY_LOGGEDIN)) {
            goToHome();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void login(View v){

        hideKeyboard();

        try{
            checkEmail();
            checkPassword();
        } catch (FormProblemException e){
            showAlert(e.getMessage());
            return;
        }

        String password = getStringFromEdit(R.id.input_pass);
        String email = getStringFromEdit(R.id.input_email);

        showDialogWithMessage(getString(R.string.load_login));

        tryLogin(password,email);
    }

    private void checkPassword() throws FormProblemException{
        String password = getStringFromEdit(R.id.input_pass);
        if("".equals(password)){
            throw new FormProblemException(getString(R.string.error_password_empty));
        }

        if (password.length() < MIN_PASSWORD){
            throw new FormProblemException(getString(R.string.error_password_small));
        }
    }

    private void checkEmail() throws FormProblemException{
        String email = getStringFromEdit(R.id.input_email);
        if("".equals(email)){
            throw new FormProblemException(getString(R.string.error_password_empty));
        }
    }

    private void tryLogin(String password, String email) {
        WebLogin webLogin = new WebLogin(email,password);
        webLogin.call();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Usuario user) {
        dismissDialog();
        storeCredentials(user);
        EasySharedPreferences.setBooleanToKey(this,EasySharedPreferences.KEY_LOGGEDIN,true);
        goToHome();
    }

    private void storeCredentials(Usuario user){
        EasySharedPreferences.setStringToKey(this,EasySharedPreferences.KEY_CPF,user.getNome());
        EasySharedPreferences.setStringToKey(this,EasySharedPreferences.KEY_SESSION,String.valueOf(user.getSession()));
    }

    private void goToHome() {
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Exception exception) {
        dismissDialog();
        showAlert(exception.getMessage());
    }
}
