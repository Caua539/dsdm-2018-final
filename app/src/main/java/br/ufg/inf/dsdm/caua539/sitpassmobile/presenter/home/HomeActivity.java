package br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.home;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import br.ufg.inf.dsdm.caua539.sitpassmobile.R;
import br.ufg.inf.dsdm.caua539.sitpassmobile.dummy.DummyContent;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.BaseFragment;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.home.fragments.HistoricoView;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.home.fragments.HomeView;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.home.fragments.RecargaView;

public class HomeActivity extends AppCompatActivity implements HomeView.OnFragmentInteractionListener, RecargaView.OnFragmentInteractionListener, HistoricoView.OnListFragmentInteractionListener {

    private HomeView homefrag;
    private RecargaView recargafrag;
    private HistoricoView historicofrag;

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

        homefrag = HomeView.newInstance(R.id.navigation_home);
        recargafrag = RecargaView.newInstance(R.id.navigation_recarga);
        historicofrag = HistoricoView.newInstance(R.id.navigation_historico, 1);
        initView(homefrag);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite));
    }

    public void initView(HomeView fragment){
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
