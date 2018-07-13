package br.ufg.inf.dsdm.caua539.sitpassmobile.model.RVAdapters;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.ufg.inf.dsdm.caua539.sitpassmobile.R;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.Entities.Evento;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class HistoricoRecyclerViewAdapter extends RecyclerView.Adapter<HistoricoRecyclerViewAdapter.ViewHolder> {

    private final List<Evento> mValues;
    private boolean colorSelector = true;
    private String red = "#d32f2f";
    private String green = "#1B5E20";

    public HistoricoRecyclerViewAdapter(List<Evento> items) {
        mValues = items;
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
            holder.mTipo.setColorFilter(Color.parseColor(green));
            holder.mValor.setText(String.format("+ R$ %.2f", holder.mItem.valor));
            holder.mValor.setTextColor(Color.parseColor(green));
            holder.mLocal.setText(String.format("Local: %s",holder.mItem.local));
        }
        else {
            holder.mTipo.setColorFilter(Color.parseColor(red));
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
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mTipo;
        public final TextView mData;
        public final TextView mLocal;
        public final TextView mValor;
        public Evento mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTipo = (ImageView) view.findViewById(R.id.status_historico);
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
