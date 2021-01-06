package com.supernover.agasobanuyestreamingappclient.originals;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.supernover.agasobanuyestreamingappclient.R;
import com.supernover.agasobanuyestreamingappclient.details.FeatureDetailsActivity;

import java.util.List;

public class OriginalsAdapter extends RecyclerView.Adapter<OriginalsAdapter.viewHolder> {
    private List<OriginalsModel> modelList;

    public OriginalsAdapter(List<OriginalsModel> modelList2) {
        this.modelList = modelList2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate( R.layout.item_featured, parent, false));
    }

    public void onBindViewHolder(viewHolder holder, int position) {
        holder.setData(this.modelList.get(position).getFcast(), this.modelList.get(position).getTlink(), this.modelList.get(position).getFcover(), this.modelList.get(position).getFlink(), this.modelList.get(position).getFthumb(), this.modelList.get(position).getFdesc(), this.modelList.get(position).getFtitle());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.modelList.size();
    }

    /* access modifiers changed from: package-private */
    public class viewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title;

        public viewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.f_thumb);
            this.title = (TextView) itemView.findViewById(R.id.item_featured_title);
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private void setData(final String f_cast, final String t_link, final String f_cover, final String f_link, final String f_thumb, final String f_des, final String f_title) {
            this.title.setText(f_title);
            Glide.with(this.itemView.getContext()).load(f_thumb).into(this.imageView);
            this.itemView.setOnClickListener(new View.OnClickListener() {
                /* class com.birjulabsinc.birjutv.originals.OriginalsAdapter.viewHolder.AnonymousClass1 */

                public void onClick(View v) {
                    Intent featuredDetails = new Intent(viewHolder.this.imageView.getContext(), FeatureDetailsActivity.class);
                    featuredDetails.putExtra("f_title", f_title);
                    featuredDetails.putExtra("f_link", f_link);
                    featuredDetails.putExtra("f_cover", f_cover);
                    featuredDetails.putExtra("f_thumb", f_thumb);
                    featuredDetails.putExtra("f_des", f_des);
                    featuredDetails.putExtra("f_cast", f_cast);
                    featuredDetails.putExtra("t_link", t_link);
                    viewHolder.this.itemView.getContext().startActivity(featuredDetails, ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) viewHolder.this.itemView.getContext(), viewHolder.this.imageView, "imageMain").toBundle());
                }
            });
        }
    }
}
