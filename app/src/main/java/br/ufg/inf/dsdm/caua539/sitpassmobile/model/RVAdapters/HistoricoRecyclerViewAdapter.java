package br.ufg.inf.dsdm.caua539.sitpassmobile.model.RVAdapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.ufg.inf.dsdm.caua539.sitpassmobile.R;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.Entities.Evento;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.historico.HistoricoFragment.OnListFragmentInteractionListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class HistoricoRecyclerViewAdapter extends RecyclerView.Adapter<HistoricoRecyclerViewAdapter.ViewHolder> {

    private final List<Evento> mValues;
    private final OnListFragmentInteractionListener mListener;

    public HistoricoRecyclerViewAdapter(List<Evento> items, OnListFragmentInteractionListener listener) {
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
        holder.mValor.setText(String.format("R$ %.2f", holder.mItem.valor));

        if (holder.mItem.tipo){
            holder.mData.setTextColor(Color.GREEN);
            holder.mLocal.setTextColor(Color.GREEN);
            holder.mValor.setTextColor(Color.GREEN);
        }
        else {
            holder.mData.setTextColor(Color.RED);
            holder.mLocal.setTextColor(Color.RED);
            holder.mValor.setTextColor(Color.RED);
        }


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
        public Evento mItem;

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
