package br.ufg.inf.dsdm.caua539.sitpassmobile.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.ufg.inf.dsdm.caua539.sitpassmobile.R;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.historico.HistoricoFragment.OnListFragmentInteractionListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class HistoricoRecyclerViewAdapter extends RecyclerView.Adapter<HistoricoRecyclerViewAdapter.ViewHolder> {

    private final List<Eventosdb> mValues;
    private final OnListFragmentInteractionListener mListener;

    public HistoricoRecyclerViewAdapter(List<Eventosdb> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_historico, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String data = df.format(holder.mItem.dia);

        holder.mData.setText(data);
        holder.mLocal.setText(holder.mItem.local);
        holder.mValor.setText(String.valueOf(holder.mItem.valor));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mData;
        public final TextView mLocal;
        public final TextView mValor;
        public Eventosdb mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mData = (TextView) view.findViewById(R.id.item_data);
            mLocal = (TextView) view.findViewById(R.id.item_local);
            mValor = (TextView) view.findViewById(R.id.item_valor);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mData.getText() + "'";
        }
    }
}
