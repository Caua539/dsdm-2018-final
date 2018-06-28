package br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.historico;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import br.ufg.inf.dsdm.caua539.sitpassmobile.data.EasySharedPreferences;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.Entities.Evento;
import br.ufg.inf.dsdm.caua539.sitpassmobile.model.RVAdapters.HistoricoRecyclerViewAdapter;
import br.ufg.inf.dsdm.caua539.sitpassmobile.R;
import br.ufg.inf.dsdm.caua539.sitpassmobile.model.ViewModel.EventosByCodigoViewModel;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.BaseFragment;
import br.ufg.inf.dsdm.caua539.sitpassmobile.web.WebHistorico;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class HistoricoFragment extends BaseFragment  {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_NAVITEM = "navigation_item";

    private int mColumnCount = 1;
    private EventosByCodigoViewModel viewModel;

    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HistoricoFragment() {
    }


    public static HistoricoFragment newInstance(int navigation_item, int columnCount) {
        HistoricoFragment fragment = new HistoricoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_NAVITEM, navigation_item);
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            navigation_item = getArguments().getInt(ARG_NAVITEM);
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        viewModel = ViewModelProviders.of(this).get(EventosByCodigoViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historico, container, false);

        // Set the adapter
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_historico);
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        subscribeUiEventos();

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        if (!EasySharedPreferences.getBooleanFromKey(
                getContext(), EasySharedPreferences.KEY_LOGGEDIN)) {
            return;
        }
        EventBus.getDefault().register(this);
        requestHistorico();

    }

    @Override
    public void onStop(){
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    private void subscribeUiEventos() {
        viewModel.eventos.observe(this, new Observer<List<Evento>>() {
            @Override
            public void onChanged(@Nullable List<Evento> eventos) {
                showEventosInUi(eventos);
            }
        });
    }

    private void showEventosInUi(final @NonNull List<Evento> eventos) {
        RecyclerView recyclerView = getActivity().findViewById(R.id.fragment_historico);
        recyclerView.setAdapter(new HistoricoRecyclerViewAdapter(eventos, mListener));
    }

    private void requestHistorico(){
        WebHistorico webHistorico = new WebHistorico(getContext());
        webHistorico.call("get");
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(String ok) {

        System.out.println(ok);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Evento item);
    }
}
