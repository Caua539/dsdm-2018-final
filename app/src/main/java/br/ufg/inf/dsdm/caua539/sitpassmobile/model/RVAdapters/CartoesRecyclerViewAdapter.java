package br.ufg.inf.dsdm.caua539.sitpassmobile.model.RVAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.ufg.inf.dsdm.caua539.sitpassmobile.R;
import br.ufg.inf.dsdm.caua539.sitpassmobile.data.Entities.Cartao;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.recarga.RecargaFragment;
import br.ufg.inf.dsdm.caua539.sitpassmobile.presenter.recarga.RecargaFragment.OnListFragmentInteractionListener;

public class CartoesRecyclerViewAdapter extends RecyclerView.Adapter<CartoesRecyclerViewAdapter.ViewHolder> {

    private final List<Cartao> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final RecargaFragment fragment;

    public CartoesRecyclerViewAdapter(RecargaFragment fragment, List<Cartao> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cartao, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        if (holder.mItem.id == 9999) {
            holder.nomeCartao.setText("Novo...");
            holder.newCartao.setVisibility(View.VISIBLE);
            holder.newCartao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        mListener.onNewCardInteraction();
                    }
                }
            });

        }
        else {
            holder.nomeCartao.setText(String.format("%s %s", "Final", holder.mItem.end));
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        holder.selected = fragment.selectedCard(holder.cartaoView, holder.deleteCartao, holder.mItem, holder.selected);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView nomeCartao;
        public final ImageView newCartao;
        public final ImageView deleteCartao;
        public final ImageView cartaoView;
        public Cartao mItem;
        public boolean selected;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            nomeCartao = view.findViewById(R.id.text_cartaoname);
            newCartao = view.findViewById(R.id.icon_newcard);
            deleteCartao = view.findViewById(R.id.icon_deletecard);
            cartaoView = view.findViewById(R.id.cartaoView);
            selected = false;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + nomeCartao.getText() + "'";
        }
    }
}
