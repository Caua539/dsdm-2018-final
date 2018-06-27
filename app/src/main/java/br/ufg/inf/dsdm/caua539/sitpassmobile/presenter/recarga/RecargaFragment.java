package br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.recarga;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.text.DecimalFormatSymbols;

import br.ufg.inf.dsdm.caua539.sitpassmobile.R;
import br.ufg.inf.dsdm.caua539.sitpassmobile.dummy.DummyContent;
import br.ufg.inf.dsdm.caua539.sitpassmobile.model.CartoesRecyclerViewAdapter;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.BaseFragment;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.historico.HistoricoFragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecargaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecargaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecargaFragment extends BaseFragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_NAVITEM = "navigation_item";


    private OnFragmentInteractionListener mListener;
    private OnListFragmentInteractionListener listListener;

    public RecargaFragment() {
        // Required empty public constructor
    }

    public static RecargaFragment newInstance(int navigation_item) {
        RecargaFragment fragment = new RecargaFragment();
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
        View view = inflater.inflate(R.layout.fragment_recarga, container, false);


        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_cartoes);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerView.setAdapter(new CartoesRecyclerViewAdapter(DummyContent.ITEMS, listListener));

        //Definição de separador de decimais por localidade
        EditText input = view.findViewById(R.id.input_valor);
        char separator = DecimalFormatSymbols.getInstance().getDecimalSeparator();
        input.setKeyListener(DigitsKeyListener.getInstance("0123456789" + separator));



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction (DummyContent.DummyItem item);
    }
}
