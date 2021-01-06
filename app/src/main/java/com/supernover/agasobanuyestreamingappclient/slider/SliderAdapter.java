package com.supernover.agasobanuyestreamingappclient.slider;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.supernover.agasobanuyestreamingappclient.R;
import com.supernover.agasobanuyestreamingappclient.videoplayersingle.SingleExoPlayerActivity;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {
    private Context context;
    private InterstitialAd interstitialAd;
    private List<SliderModel> mSliderItems = new ArrayList();

    public SliderAdapter(Context context2, InterstitialAd interstitialAd2) {
        this.context = context2;
        this.interstitialAd = interstitialAd2;
    }

    public void renewItems(List<SliderModel> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(0);
        notifyDataSetChanged();
    }

    public void addItem(SliderModel sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override // com.smarteist.autoimageslider.SliderViewAdapter
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        return new SliderAdapterVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item, (ViewGroup) null));
    }

    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        final SliderModel sliderItem = this.mSliderItems.get(position);
        viewHolder.textViewDescription.setText(sliderItem.getTtitle());
        viewHolder.textViewDescription.setTextSize(24.0f);
        viewHolder.textViewDescription.setTextColor(-1);
        Glide.with(viewHolder.itemView).load(sliderItem.getTurl()).into(viewHolder.imageViewBackground);
        viewHolder.playButton.setOnClickListener(new View.OnClickListener() {
            /* class com.birjulabsinc.birjutv.slider.SliderAdapter.AnonymousClass1 */

            public void onClick(final View v) {
                SliderAdapter.this.interstitialAd.setAdListener(new AdListener() {
                    /* class com.birjulabsinc.birjutv.slider.SliderAdapter.AnonymousClass1.AnonymousClass1 */

                    @Override // com.google.android.gms.ads.AdListener
                    public void onAdClosed() {
                        super.onAdClosed();
                        Intent singleVideo = new Intent(SliderAdapter.this.context, SingleExoPlayerActivity.class);
                        singleVideo.putExtra("vid", sliderItem.getTvid());
                        singleVideo.putExtra("title", sliderItem.getTtitle());
                        v.getContext().startActivity(singleVideo);
                    }
                });
                if (SliderAdapter.this.interstitialAd.isLoaded()) {
                    SliderAdapter.this.interstitialAd.show();
                    return;
                }
                Intent singleVideo = new Intent(SliderAdapter.this.context, SingleExoPlayerActivity.class);
                singleVideo.putExtra("vid", sliderItem.getTvid());
                singleVideo.putExtra("title", sliderItem.getTtitle());
                v.getContext().startActivity(singleVideo);
            }
        });
    }

    // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.mSliderItems.size();
    }

    public void add(String description) {
    }

    /* access modifiers changed from: package-private */
    public class SliderAdapterVH extends ViewHolder {
        ImageView imageViewBackground;
        View itemView;
        FloatingActionButton playButton;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView2) {
            super(itemView2);
            this.imageViewBackground = (ImageView) itemView2.findViewById(R.id.slide_img);
            this.textViewDescription = (TextView) itemView2.findViewById(R.id.slider_title);
            this.playButton = (FloatingActionButton) itemView2.findViewById( R.id.floatingActionButton);
            this.itemView = itemView2;
        }
    }
}
