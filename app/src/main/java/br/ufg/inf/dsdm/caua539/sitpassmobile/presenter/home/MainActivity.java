package br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import br.ufg.inf.dsdm.caua539.sitpassmobile.R;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.EasySharedPreferences;
import br.ufg.inf.dsdm.caua539.sitpassmobile.dummy.DummyContent;
import br.ufg.inf.dsdm.caua539.sitpassmobile.model.Eventosdb;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.BaseActivity;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.BaseFragment;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.historico.HistoricoFragment;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.login.LoginActivity;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.recarga.RecargaFragment;
import br.ufg.inf.dsdm.caua539.sitpassmobile.web.WebHistorico;
import br.ufg.inf.dsdm.caua539.sitpassmobile.web.WebSaldo;

public class MainActivity extends BaseActivity implements HomeFragment.OnFragmentInteractionListener, RecargaFragment.OnFragmentInteractionListener, RecargaFragment.OnListFragmentInteractionListener, HistoricoFragment.OnListFragmentInteractionListener {

    private HomeFragment homefrag;
    private RecargaFragment recargafrag;
    private HistoricoFragment historicofrag;

    public static final double valorPassagem = 4.05;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    replaceView(homefrag);
                    return true;
                case R.id.navigation_recarga:
                    replaceView(recargafrag);
                    return true;
                case R.id.navigation_historico:
                    replaceView(historicofrag);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initToolbar();

        homefrag = HomeFragment.newInstance(R.id.navigation_home);
        recargafrag = RecargaFragment.newInstance(R.id.navigation_recarga);
        historicofrag = HistoricoFragment.newInstance(R.id.navigation_historico, 1);

        initView(homefrag);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!EasySharedPreferences.getBooleanFromKey(
                this, EasySharedPreferences.KEY_LOGGEDIN)) {
            goToLogin();
            return;
        }
        getHistorico();


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Exception exception) {
        dismissDialog();
        showAlert(exception.getMessage());
    }

    private void getHistorico(){

        WebHistorico webHistorico = new WebHistorico();
        webHistorico.call("get");
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(String ok) {
        System.out.println(ok);
    }


    private void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite));
    }

    public void initView(HomeFragment fragment){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.container_frag, fragment);
        fragmentTransaction.commit();
    }

    public void replaceView (BaseFragment fragment){

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_frag, fragment).addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void goToLogin() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onButtonFragmentInteraction() {

    }

    @Override
    public void onListFragmentInteraction(Eventosdb item) {

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
