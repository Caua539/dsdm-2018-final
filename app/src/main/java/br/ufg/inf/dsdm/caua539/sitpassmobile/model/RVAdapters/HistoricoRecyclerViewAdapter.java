package br.ufg.inf.dsdm.caua539.sitpassmobile.model.RVAdapters;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import br.ufg.inf.dsdm.caua539.sitpassmobile.R;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.Entities.Evento;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.historico.HistoricoFragment.OnListFragmentInteractionListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class HistoricoRecyclerViewAdapter extends RecyclerView.Adapter<HistoricoRecyclerViewAdapter.ViewHolder> {

    private final List<Evento> mValues;
    private final OnListFragmentInteractionListener mListener;
    private boolean colorSelector = true;
    private String red = "#d32f2f";
    private String green = "#1B5E20";

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

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        String data = df.format(holder.mItem.dia);

        holder.mData.setText(data);


        if (holder.mItem.tipo){
            holder.mTipo.setText("REC:");
            holder.mTipo.setTextColor(Color.parseColor(green));
            holder.mValor.setText(String.format("+ R$ %.2f", holder.mItem.valor));
            holder.mValor.setTextColor(Color.parseColor(green));
            holder.mLocal.setText(String.format("Local: %s",holder.mItem.local));
        }
        else {
            holder.mTipo.setText("DÉB:");
            holder.mTipo.setTextColor(Color.parseColor(red));
            holder.mValor.setText(String.format("- R$ %.2f", holder.mItem.valor));
            holder.mValor.setTextColor(Color.parseColor(red));
            holder.mLocal.setText(String.format("Ônibus: %s",holder.mItem.local));
        }

        if (colorSelector) {
            colorSelector = false;
            holder.mView.setBackgroundResource(R.drawable.image_border_color);
            holder.mData.setTextColor(Color.WHITE);
            holder.mLocal.setTextColor(Color.WHITE);
        }
        else {
            colorSelector = true;
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
        public final TextView mTipo;
        public final TextView mData;
        public final TextView mLocal;
        public final TextView mValor;
        public Evento mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTipo = (TextView) view.findViewById(R.id.item_tipotransac);
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
