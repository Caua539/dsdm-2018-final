package br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.home;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import br.ufg.inf.dsdm.caua539.sitpassmobile.R;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.EasySharedPreferences;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_NAVITEM = "navigation_item";

    private double saldo;
    private String nome;


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

        ImageButton button = (ImageButton) view.findViewById(R.id.button_atualizasaldo);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onButtonFragmentInteraction();
                }
            }
        });

        return view;
    }

    @Override
    public void onStart(){
        setNome(nome);
        setSaldo(saldo);
        super.onStart();

    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;

        String saldoValue = String.format("%.2f", saldo);
        int numPassagens = (int)(saldo / MainActivity.valorPassagem);
        String passagensValue = String.format("%d", numPassagens);

        substitueTextVariable(saldoValue, R.id.text_saldo, R.string.home_saldo);
        substitueTextVariable(passagensValue, R.id.text_numeropass, R.string.home_numpass);
    }

    public void setNome (String nome){
        this.nome = nome;
        substitueTextVariable(this.nome, R.id.text_hello, R.string.home_hello);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onButtonFragmentInteraction();
    }
}
