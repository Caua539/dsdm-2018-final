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

import java.security.SecureRandom;
import java.util.Random;

import br.ufg.inf.dsdm.caua539.sitpassmobile.R;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.EasySharedPreferences;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.Entities.Cartao;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.TheDatabase;
import br.ufg.inf.dsdm.caua539.sitpassmobile.dummy.DummyContent;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.Entities.Evento;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.BaseActivity;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.BaseFragment;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.historico.HistoricoFragment;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.login.LoginActivity;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.recarga.NewCardActivity;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.recarga.RecargaFragment;
import br.ufg.inf.dsdm.caua539.sitpassmobile.web.WebHistorico;

public class MainActivity extends BaseActivity implements HomeFragment.OnFragmentInteractionListener, RecargaFragment.OnFragmentInteractionListener, RecargaFragment.OnListFragmentInteractionListener {

    private HomeFragment homefrag;
    private RecargaFragment recargafrag;
    private HistoricoFragment historicofrag;
    private TheDatabase busdb;

    Random secure = new SecureRandom();
    public static char[] key;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    replaceView(homefrag);
                    getSupportActionBar().setTitle("Início");
                    return true;
                case R.id.navigation_recarga:
                    replaceView(recargafrag);
                    getSupportActionBar().setTitle("Recarga");
                    return true;
                case R.id.navigation_historico:
                    replaceView(historicofrag);
                    getSupportActionBar().setTitle("Histórico de Transações");
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        key = EasySharedPreferences.getCharFromKey(getApplicationContext(), EasySharedPreferences.KEY_PASS);
        if (key == null){
            String alphabet = "abcdefghijklmnopqrstuvxywz1234567890";
            char[] c = new char[20];
            for (int i = 0; i < 20; i++){
                int j = secure.nextInt(alphabet.length());
                c[i] = alphabet.charAt(j);
            }

            key = c;
            EasySharedPreferences.setCharToKey(getApplicationContext(), EasySharedPreferences.KEY_PASS, key);
        }

        initToolbar(R.id.toolbar, "Início");

        TheDatabase.getDatabase(getApplicationContext());

        homefrag = HomeFragment.newInstance(R.id.navigation_home);
        recargafrag = RecargaFragment.newInstance(R.id.navigation_recarga);
        historicofrag = HistoricoFragment.newInstance(R.id.navigation_historico, 1);

        initView(homefrag);

        BottomNavigationView navigation = findViewById(R.id.navigation);
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
        EventBus.getDefault().register(this);
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
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onNewCardInteraction() {
        System.out.println("Cartão escolhido!");
        Intent intent = new Intent(this, NewCardActivity.class);
        startActivity(intent);
    }
}
