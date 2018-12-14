package com.example.ando.cameratag.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ando.cameratag.R;
import com.example.ando.cameratag.database.entity.PrescriprionEntity;

import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.PrecriptionItemViewHolder> {

    private final OnClickListener clickListener;
    private List<PrescriprionEntity> precriptionList;

    public class PrecriptionItemViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public PrecriptionItemViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
        }
    }

    public ListViewAdapter(List<PrescriprionEntity> moviesList, OnClickListener listener) {
        this.precriptionList = moviesList;
        this.clickListener = listener;
    }

    @Override
    public PrecriptionItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new PrecriptionItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PrecriptionItemViewHolder holder, final int position) {
        final PrescriprionEntity data = precriptionList.get(position);
        holder.title.setText("Precription " + position);
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return precriptionList.size();
    }

    public interface OnClickListener {
        void onClick(PrescriprionEntity position);
    }
}
