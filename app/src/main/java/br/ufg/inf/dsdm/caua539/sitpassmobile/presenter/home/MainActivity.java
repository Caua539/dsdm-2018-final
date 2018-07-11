package br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;

import br.ufg.inf.dsdm.caua539.sitpassmobile.R;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.DAOs.CartaoDAO;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.DAOs.EventoDAO;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.EasySharedPreferences;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.EncUtil;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.Entities.Cartao;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.TheDatabase;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.BaseActivity;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.BaseFragment;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.historico.HistoricoFragment;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.login.LoginActivity;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.recarga.NewCardActivity;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.recarga.RecargaFragment;

public class MainActivity extends BaseActivity implements HomeFragment.OnFragmentInteractionListener, RecargaFragment.OnFragmentInteractionListener, RecargaFragment.OnListFragmentInteractionListener, RecargaFragment.OnButtonInteractionListener {

    private HomeFragment homefrag;
    private RecargaFragment recargafrag;
    private HistoricoFragment historicofrag;
    private ImageView logout;


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

        EncUtil.createKeyStore(getApplicationContext());

        initToolbar(R.id.toolbar, "Início");

        TheDatabase.getDatabase(getApplicationContext());

        homefrag = HomeFragment.newInstance(R.id.navigation_home);
        recargafrag = RecargaFragment.newInstance(R.id.navigation_recarga);
        historicofrag = HistoricoFragment.newInstance(R.id.navigation_historico, 1);

        initView(homefrag);

        logout = findViewById(R.id.img_logouticon);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Log Out")
                        .setMessage("Deseja realmente sair?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dologout();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

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

    private void dologout(){
        EasySharedPreferences.setStringToKey(this,EasySharedPreferences.KEY_CPF,"");
        EasySharedPreferences.setStringToKey(this,EasySharedPreferences.KEY_NAME,"");
        EasySharedPreferences.setDoubleToKey(this,EasySharedPreferences.KEY_SALDO,0);
        EasySharedPreferences.setStringToKey(this,EasySharedPreferences.KEY_SESSION,"");
        EasySharedPreferences.setBooleanToKey(this,EasySharedPreferences.KEY_LOGGEDIN,false);
        new CleanDbAsync(TheDatabase.getDatabase(getApplicationContext()));
        goToLogin();
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

    @Override
    public void onPagarInteraction(double valor, Cartao cartao) {
        Toast.makeText(this, "Não implementado :(", Toast.LENGTH_LONG).show();

    }

    private static class CleanDbAsync extends AsyncTask<Void, Void, Void> {

        private final EventoDAO fDao;
        private final CartaoDAO sDao;

        CleanDbAsync(TheDatabase db) {
            fDao = db.eventoDAO();
            sDao = db.cartaoDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            fDao.deleteAll();
            sDao.deleteAll();
            return null;
        }
    }
}
