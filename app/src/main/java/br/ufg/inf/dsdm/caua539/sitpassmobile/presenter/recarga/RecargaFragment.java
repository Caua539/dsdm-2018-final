package br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.recarga;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.text.DecimalFormatSymbols;
import java.util.List;

import br.ufg.inf.dsdm.caua539.sitpassmobile.R;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.DAOs.CartaoDAO;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.Entities.Cartao;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.TheDatabase;
import br.ufg.inf.dsdm.caua539.sitpassmobile.model.RVAdapters.CartoesRecyclerViewAdapter;
import br.ufg.inf.dsdm.caua539.sitpassmobile.model.ViewModel.CartoesViewModel;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.BaseFragment;


public class RecargaFragment extends BaseFragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_NAVITEM = "navigation_item";


    private OnFragmentInteractionListener mListener;
    private OnListFragmentInteractionListener listListener;
    private OnButtonInteractionListener bttnListener;
    private CartoesViewModel viewModel;
    private EditText input;

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
        viewModel = ViewModelProviders.of(this).get(CartoesViewModel.class);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recarga, container, false);

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_cartoes);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        subscribeUiCartoes();

        //Definição de separador de decimais por localidade
        input = view.findViewById(R.id.input_valor);
        char separator = DecimalFormatSymbols.getInstance().getDecimalSeparator();
        input.setKeyListener(DigitsKeyListener.getInstance("0123456789" + separator));

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public Cartao newCartao(){
        Cartao cartao = new Cartao(9999);
        return cartao;
    }

    private void subscribeUiCartoes() {
        viewModel.cartoes.observe(this, new Observer<List<Cartao>>() {
            @Override
            public void onChanged(@Nullable List<Cartao> cartoes) {
                showCartoesInUI(cartoes);
            }
        });
    }

    private void showCartoesInUI(final @NonNull List<Cartao> cartoes){
        boolean newCardAlready = false;
        for (int i =0; i < cartoes.size(); i++){
            if (cartoes.get(i).id == 9999){
                newCardAlready = true;
                break;
            }
        }
        if (!newCardAlready){
            cartoes.add(newCartao());
        }

        RecyclerView recyclerView = getActivity().findViewById(R.id.recyclerview_cartoes);
        recyclerView.setAdapter(new CartoesRecyclerViewAdapter(RecargaFragment.this, cartoes, listListener));
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

        if (context instanceof OnListFragmentInteractionListener){
            listListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
        if (context instanceof OnButtonInteractionListener){
            bttnListener = (OnButtonInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnButtonInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        listListener = null;
    }

    public boolean selectedCard(ImageView cartaoView, ImageView deleteCartao, final Cartao card, boolean selected){
        if (selected == false){
            deleteCartao.setVisibility(View.VISIBLE);
            cartaoView.setBackgroundColor(Color.GRAY);
            deleteCartao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Deletar Cartão")
                            .setMessage("Deseja realmente deletar o cartão?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    new DeleteAsyncTask(getActivity(), getContext(), card).execute();
                                    toggleBotaoPagar(false, null);
                                }})
                            .setNegativeButton(android.R.string.no, null).show();
                }
            });
            toggleBotaoPagar(true, card);
            return true;
        }
        else {
            deleteCartao.setVisibility(View.INVISIBLE);
            cartaoView.setBackgroundColor(Color.WHITE);
            toggleBotaoPagar(false, null);
            return false;
        }

    }

    public void toggleBotaoPagar(boolean status, final Cartao card){
        Button botao = getActivity().findViewById(R.id.button_pagar);
        botao.setEnabled(status);
        if (status){
            botao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String valortext = input.getText().toString();
                    valortext = valortext.replace(",", ".");
                    try{
                        double valor = Double.parseDouble(valortext);
                        bttnListener.onPagarInteraction(valor, card);
                    } catch (Exception e){
                        Toast.makeText(getActivity(), "Valor inválido.", Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onNewCardInteraction ();
    }

    public interface OnButtonInteractionListener {
        void onPagarInteraction(double valor, Cartao cartao);
    }

    private static class DeleteAsyncTask extends AsyncTask<Void, Void, Integer> {

        //Prevent leak
        private WeakReference<Activity> weakActivity;
        private Cartao cartao;
        private Context context;


        public DeleteAsyncTask(Activity activity, Context context, Cartao cartao) {
            weakActivity = new WeakReference<>(activity);
            this.cartao = cartao;
            this.context = context;

        }

        @Override
        protected Integer doInBackground(Void... params) {
            CartaoDAO dao = TheDatabase.getDatabase(context).cartaoDAO();
            dao.deleteCartao(cartao.id);
            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            Activity activity = weakActivity.get();
            if(activity == null) {
                return;
            }
            Toast.makeText(activity, "Cartão deletado com sucesso!", Toast.LENGTH_LONG).show();

        }
    }
}
