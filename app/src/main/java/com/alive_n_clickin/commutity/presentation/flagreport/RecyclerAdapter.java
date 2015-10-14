package com.alive_n_clickin.commutity.presentation.flagreport;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alive_n_clickin.commutity.R;

import java.util.List;

/**
 * Created by OscarEvertsson on 14/10/15.
 */
public class RecyclerAdapter extends  RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<FlagButton> flagButtonList;

    public RecyclerAdapter(List<FlagButton> flagButtonList) {
        this.flagButtonList = flagButtonList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView flagInfo;
        public ImageView flagImg;
        public ViewHolder(View v) {
            super(v);
            flagInfo = (TextView) v.findViewById(R.id.textView);
            flagImg = (ImageView) v.findViewById(R.id.imageView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flag_type_card,parent,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FlagButton button = this.flagButtonList.get(position);
        Context context = holder.flagImg.getContext();

        holder.flagImg.setImageDrawable(context.getResources().getDrawable(button.getImageID()));
        holder.flagInfo.setText(button.getDescription());
    }

    @Override
    public int getItemCount() {
        return flagButtonList.size();
    }

}
