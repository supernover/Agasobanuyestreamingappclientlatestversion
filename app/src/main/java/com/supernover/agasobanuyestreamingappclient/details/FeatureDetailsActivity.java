package com.supernover.agasobanuyestreamingappclient.details;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.supernover.agasobanuyestreamingappclient.R;
import com.supernover.agasobanuyestreamingappclient.videoplayersingle.SingleExoPlayerActivity;

import java.util.ArrayList;
import java.util.List;

public class FeatureDetailsActivity extends AppCompatActivity {
    private String Fcast;
    private String Fcover;
    private String Fdesc;
    private String Flink;
    private String Frating;
    private String Fstudio;
    private String Fthumb;
    private String Ftitle;
    private String Tlink;
    private CastAdapter castAdapter;
    private List<CastModel> castModelList;
    private ImageView cover;
    private TextView desc;
    private TextView link;
    private InterstitialAd mInterstitialAd;
    private PartAdapter partAdapter;
    private List<PartModel> partModelList;
    private RecyclerView partRecyclerView;
    private FloatingActionButton playButton;
    private TextView rating;
    private RecyclerView recyclerView;
    private TextView studio;
    private ImageView thumb;
    private TextView title;

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_feature_details);
        this.Ftitle = getIntent().getStringExtra("f_title");
        this.Fdesc = getIntent().getStringExtra("f_des");
        this.Fthumb = getIntent().getStringExtra("f_thumb");
        this.Fstudio = getIntent().getStringExtra("f_studio");
        this.Frating = getIntent().getStringExtra("f_rating");
        this.Flink = getIntent().getStringExtra("f_link");
        this.Fcover = getIntent().getStringExtra("f_cover");
        this.Fcast = getIntent().getStringExtra("f_cast");
        this.Tlink = getIntent().getStringExtra("t_link");
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle(this.Ftitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        MobileAds.initialize(this);
        loadAds();
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        this.playButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        this.thumb = (ImageView) findViewById(R.id.f_thumb);
        this.cover = (ImageView) findViewById(R.id.f_cover);
        this.title = (TextView) findViewById(R.id.f_title);
        this.desc = (TextView) findViewById(R.id.f_desc);
        this.title.setText(this.Ftitle);
        this.desc.setText(this.Fdesc);
        Glide.with((FragmentActivity) this).load(this.Fthumb).into(this.thumb);
        Glide.with((FragmentActivity) this).load(this.Fcover).into(this.cover);
        this.cover.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        this.playButton.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        this.playButton.setOnClickListener(new View.OnClickListener() {
            /* class com.birjulabsinc.birjutv.details.FeatureDetailsActivity.AnonymousClass1 */

            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("link").child(FeatureDetailsActivity.this.Tlink).addValueEventListener(new ValueEventListener() {
                    /* class com.birjulabsinc.birjutv.details.FeatureDetailsActivity.AnonymousClass1.AnonymousClass1 */

                    @Override // com.google.firebase.database.ValueEventListener
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Intent gotoVideoPlayer = new Intent(FeatureDetailsActivity.this, SingleExoPlayerActivity.class);
                        gotoVideoPlayer.putExtra("vid", (String) dataSnapshot.getValue(String.class));
                        FeatureDetailsActivity.this.startActivity(gotoVideoPlayer);
                    }

                    @Override // com.google.firebase.database.ValueEventListener
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
        loadFeaturedFirebase();
        loadFeaturedPartFirebase();
    }

    private void loadAds() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        this.mInterstitialAd = interstitialAd;
        interstitialAd.setAdUnitId(getResources().getString(R.string.interstitialAd_id));
        this.mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    private void loadFeaturedFirebase() {
        DatabaseReference RvRef = FirebaseDatabase.getInstance().getReference("cast").child(this.Fcast);
        this.recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL );
        this.recyclerView.setLayoutManager(layoutManager);
        ArrayList arrayList = new ArrayList();
        this.castModelList = arrayList;
        CastAdapter castAdapter2 = new CastAdapter(arrayList);
        this.castAdapter = castAdapter2;
        this.recyclerView.setAdapter(castAdapter2);
        RvRef.addListenerForSingleValueEvent(new ValueEventListener() {
            /* class com.birjulabsinc.birjutv.details.FeatureDetailsActivity.AnonymousClass2 */

            @Override // com.google.firebase.database.ValueEventListener
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot contentSnapShot : dataSnapshot.getChildren()) {
                    FeatureDetailsActivity.this.castModelList.add((CastModel) contentSnapShot.getValue(CastModel.class));
                }
                FeatureDetailsActivity.this.castAdapter.notifyDataSetChanged();
            }

            @Override // com.google.firebase.database.ValueEventListener
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(FeatureDetailsActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                FeatureDetailsActivity.this.finish();
            }
        });
    }

    private void loadFeaturedPartFirebase() {
        DatabaseReference RvRef = FirebaseDatabase.getInstance().getReference("link").child(this.Flink);
        this.partRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_parts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        this.partRecyclerView.setLayoutManager(layoutManager);
        ArrayList arrayList = new ArrayList();
        this.partModelList = arrayList;
        PartAdapter partAdapter2 = new PartAdapter(arrayList, this, this.mInterstitialAd);
        this.partAdapter = partAdapter2;
        this.partRecyclerView.setAdapter(partAdapter2);
        RvRef.addListenerForSingleValueEvent(new ValueEventListener() {
            /* class com.birjulabsinc.birjutv.details.FeatureDetailsActivity.AnonymousClass3 */

            @Override // com.google.firebase.database.ValueEventListener
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot contentSnapShot : dataSnapshot.getChildren()) {
                    FeatureDetailsActivity.this.partModelList.add((PartModel) contentSnapShot.getValue(PartModel.class));
                }
                FeatureDetailsActivity.this.partAdapter.notifyDataSetChanged();
            }

            @Override // com.google.firebase.database.ValueEventListener
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(FeatureDetailsActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                FeatureDetailsActivity.this.finish();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
