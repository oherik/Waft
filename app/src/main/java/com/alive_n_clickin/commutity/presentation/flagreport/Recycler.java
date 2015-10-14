package com.alive_n_clickin.commutity.presentation.flagreport;

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
public class Recycler extends  RecyclerView.Adapter<Recycler.ViewHolder> {
    private List<FlagButton> flagButtonList;

    public Recycler(List<FlagButton> flagButtonList) {
        this.flagButtonList = flagButtonList;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView flagInfo;
        public ImageView flagImg;
        public ViewHolder(View v) {
            super(v); //Is this correct?
            this.flagInfo = (TextView) v.findViewById(R.id.textView);
            this.flagImg = (ImageView) v.findViewById(R.id.imageView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flag_type_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String description = flagButtonList.get(position).getDescription();
        holder.flagInfo.setText(description);
        //holder.flagImg.setImageDrawable(flagButtonList.get(position).getImageID());
    }

    @Override
    public int getItemCount() {
        return flagButtonList.size();
    }

}
