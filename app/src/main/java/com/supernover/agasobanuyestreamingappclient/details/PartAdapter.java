package com.supernover.agasobanuyestreamingappclient.details;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.supernover.agasobanuyestreamingappclient.R;
import com.supernover.agasobanuyestreamingappclient.videoplayersingle.SingleExoPlayerActivity;

import java.util.List;

public class PartAdapter extends RecyclerView.Adapter<PartAdapter.viewHolder> {
    private Context context;
    private InterstitialAd interstitialAd;
    private List<PartModel> partModels;

    public PartAdapter(List<PartModel> partModels2, Context context2, InterstitialAd interstitialAd2) {
        this.partModels = partModels2;
        this.context = context2;
        this.interstitialAd = interstitialAd2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.parts_card, parent, false));
    }

    public void onBindViewHolder(viewHolder holder, int position) {
        holder.setData(this.partModels.get(position).getUrl(), this.partModels.get(position).getPart(), this.partModels.get(position).getVidUrl());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.partModels.size();
    }

    /* access modifiers changed from: package-private */
    public class viewHolder extends RecyclerView.ViewHolder {
        private ImageView part_image;
        private TextView part_no;

        public viewHolder(View itemView) {
            super(itemView);
            this.part_no = (TextView) itemView.findViewById(R.id.part_no);
            this.part_image = (ImageView) itemView.findViewById(R.id.part_image);
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private void setData(String url, final String name, final String vidUrl) {
            this.part_no.setText(name);
            Glide.with(this.itemView.getContext()).load(url).into(this.part_image);
            this.itemView.setOnClickListener(new View.OnClickListener() {
                /* class com.birjulabsinc.birjutv.details.PartAdapter.viewHolder.AnonymousClass1 */

                public void onClick(final View v) {
                    PartAdapter.this.interstitialAd.setAdListener(new AdListener() {
                        /* class com.birjulabsinc.birjutv.details.PartAdapter.viewHolder.AnonymousClass1.AnonymousClass1 */

                        @Override // com.google.android.gms.ads.AdListener
                        public void onAdClosed() {
                            super.onAdClosed();
                            Intent gotoSingleExoPlayer = new Intent(PartAdapter.this.context, SingleExoPlayerActivity.class);
                            gotoSingleExoPlayer.putExtra("title", name);
                            gotoSingleExoPlayer.putExtra("vid", vidUrl);
                            v.getContext().startActivity(gotoSingleExoPlayer);
                        }
                    });
                    if (PartAdapter.this.interstitialAd.isLoaded()) {
                        PartAdapter.this.interstitialAd.show();
                        return;
                    }
                    Intent gotoSingleExoPlayer = new Intent(PartAdapter.this.context, SingleExoPlayerActivity.class);
                    gotoSingleExoPlayer.putExtra("title", name);
                    gotoSingleExoPlayer.putExtra("vid", vidUrl);
                    v.getContext().startActivity(gotoSingleExoPlayer);
                }
            });
        }
    }
}
