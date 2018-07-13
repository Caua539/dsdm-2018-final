package br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.InputStream;

import br.ufg.inf.dsdm.caua539.sitpassmobile.R;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.EasySharedPreferences;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.BaseFragment;
import br.ufg.inf.dsdm.caua539.sitpassmobile.web.WebSaldo;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment {

    private static final String ARG_NAVITEM = "navigation_item";

    private double saldo;
    private String nome;
    public final double valorPassagem = 4.05;


    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(int navigation_item) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_NAVITEM, navigation_item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            navigation_item = getArguments().getInt(ARG_NAVITEM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        if (!EasySharedPreferences.getBooleanFromKey(
                getContext(), EasySharedPreferences.KEY_LOGGEDIN)) {
            return;
        }
        nome = EasySharedPreferences.getStringFromKey(getContext(), EasySharedPreferences.KEY_NAME);
        substitueTextVariable(nome, R.id.text_hello, R.string.home_hello);
        setSaldo();
        setProfilepic();

        EventBus.getDefault().register(this);
        requestSaldo();

    }

    @Override
    public void onStop(){
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void requestSaldo(){

        WebSaldo webSaldo = new WebSaldo();
        webSaldo.call("get");
    }

    public void setSaldo() {

        saldo = EasySharedPreferences.getDoubleFromKey(getContext(), EasySharedPreferences.KEY_SALDO);
        String saldoValue = String.format("%.2f", saldo);
        int numPassagens = (int)(saldo / valorPassagem);
        String passagensValue = String.format("%d", numPassagens);

        substitueTextVariable(saldoValue, R.id.text_saldo, R.string.home_saldo);
        substitueTextVariable(passagensValue, R.id.text_numeropass, R.string.home_numpass);
    }

    public void setProfilepic(){
        ImageView profilepic = (ImageView) getActivity().findViewById(R.id.img_fotousuario) ;
        String imgurl = EasySharedPreferences.getStringFromKey(getContext(), EasySharedPreferences.KEY_IMGURL);
        if (imgurl.trim().length() != 0){
            Picasso.get()
                    .load(imgurl)
                    .placeholder(R.drawable.ic_person)
                    .error(R.drawable.ic_person)
                    .into(profilepic);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void substitueTextVariable(String text, int textview, int string){

        TextView textView = (TextView) getView().findViewById(textview);
        String textBefore = getResources().getString(string);
        String textNow = String.format(textBefore, text);
        textView.setText(textNow);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Double saldo) {
        EasySharedPreferences.setDoubleToKey(getContext(),EasySharedPreferences.KEY_SALDO,saldo);
        setSaldo();
    }

    public interface OnFragmentInteractionListener {
        void onButtonFragmentInteraction();
    }

}
