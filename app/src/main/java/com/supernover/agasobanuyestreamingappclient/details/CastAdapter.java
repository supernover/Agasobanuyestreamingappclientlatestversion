package com.supernover.agasobanuyestreamingappclient.details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.supernover.agasobanuyestreamingappclient.R;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.viewHolder> {
    private List<CastModel> castModels;

    public CastAdapter(List<CastModel> castModels2) {
        this.castModels = castModels2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cast, parent, false));
    }

    public void onBindViewHolder(viewHolder holder, int position) {
        holder.setData(this.castModels.get(position).getCurl(), this.castModels.get(position).getCname());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.castModels.size();
    }

    /* access modifiers changed from: package-private */
    public class viewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title;

        public viewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.img_cast);
            this.title = (TextView) itemView.findViewById(R.id.name_cast);
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private void setData(String c_url, String c_name) {
            Glide.with(this.itemView.getContext()).load(c_url).into(this.imageView);
            this.title.setText(c_name);
        }
    }
}
