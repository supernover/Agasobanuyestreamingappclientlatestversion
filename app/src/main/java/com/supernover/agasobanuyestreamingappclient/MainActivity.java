package com.supernover.agasobanuyestreamingappclient;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.supernover.agasobanuyestreamingappclient.details.FeaturedAdapter;
import com.supernover.agasobanuyestreamingappclient.details.FeaturedModel;
import com.supernover.agasobanuyestreamingappclient.originals.OriginalsAdapter;
import com.supernover.agasobanuyestreamingappclient.originals.OriginalsModel;
import com.supernover.agasobanuyestreamingappclient.slider.SliderAdapter;
import com.supernover.agasobanuyestreamingappclient.slider.SliderModel;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_STORAGE_CODE = 100;
    private SliderAdapter adapter;
    FirebaseDatabase database;
    private FeaturedAdapter featuredAdapter;
    private List<FeaturedModel> featuredModelList;
    private ImageView imageUpdate;
    private InterstitialAd interstitialAd;
    DatabaseReference myRef;
    private OriginalsAdapter originalsAdapter;
    private List<OriginalsModel> originalsModelList;
    private RecyclerView recyclerView;
    private RecyclerView recycler_originals;
    private List<SliderModel> slideList;

    private AppOpenAdManager appOpenAdManager;

    private int numActivityRestarted = 0;

    private AdView adViewMessage;

    public MainActivity() {
        FirebaseDatabase instance = FirebaseDatabase.getInstance();
        this.database = instance;
        this.myRef = instance.getReference();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        adViewMessage = findViewById(R.id.adViewMessage);


        adViewMessage = (AdView)findViewById(R.id.adViewMessage);
        AdRequest request = new AdRequest.Builder().build();
        adViewMessage.loadAd(request);
        adViewMessage.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });


        appOpenAdManager = ((MyApplication) getApplication()).getAppOpenAdManager();

        forceUpdate();
        this.imageUpdate = (ImageView) findViewById( R.id.image_update);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("Agasobanuye");


        MobileAds.initialize(this);
        loadAds();
        FirebaseApp.initializeApp(this);
        SliderView sliderView = (SliderView) findViewById(R.id.slider_pager);
        SliderAdapter sliderAdapter = new SliderAdapter(this, this.interstitialAd);
        this.adapter = sliderAdapter;
        sliderView.setSliderAdapter(sliderAdapter);
        this.recycler_originals = (RecyclerView) findViewById(R.id.rv_originals);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(0);
        sliderView.setScrollTimeInSec(6);
        sliderView.startAutoCycle();
        renewItems(sliderView);
        loadSliderFirebase();
        loadFeatureFirebase();
    }

    private void loadAds() {
        InterstitialAd interstitialAd2 = new InterstitialAd(this);
        this.interstitialAd = interstitialAd2;
        interstitialAd2.setAdUnitId(getResources().getString(R.string.interstitialAd_id));
        this.interstitialAd.loadAd(new AdRequest.Builder().build());
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */



    private void loadFeatureFirebase() {
        DatabaseReference RvRef = this.database.getReference("featured");
        this.recyclerView = (RecyclerView) findViewById(R.id.rv_featured_movies);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        this.recyclerView.setLayoutManager(layoutManager);
        ArrayList arrayList = new ArrayList();
        this.featuredModelList = arrayList;
        FeaturedAdapter featuredAdapter2 = new FeaturedAdapter(arrayList);
        this.featuredAdapter = featuredAdapter2;
        this.recyclerView.setAdapter(featuredAdapter2);
        RvRef.addListenerForSingleValueEvent(new ValueEventListener() {
            /* class com.birjulabsinc.birjutv.MainActivity.AnonymousClass4 */

            @Override // com.google.firebase.database.ValueEventListener
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot contentSnapShot : dataSnapshot.getChildren()) {
                    MainActivity.this.featuredModelList.add((FeaturedModel) contentSnapShot.getValue(FeaturedModel.class));
                }
                MainActivity.this.featuredAdapter.notifyDataSetChanged();
            }

            @Override // com.google.firebase.database.ValueEventListener
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                MainActivity.this.finish();
            }
        });
        loadOriginalsFirebase();
    }

    private void loadOriginalsFirebase() {
        DatabaseReference RvOr = this.database.getReference("originals");
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        this.recycler_originals.setLayoutManager(layoutManager1);
        ArrayList arrayList = new ArrayList();
        this.originalsModelList = arrayList;
        OriginalsAdapter originalsAdapter2 = new OriginalsAdapter(arrayList);
        this.originalsAdapter = originalsAdapter2;
        this.recycler_originals.setAdapter(originalsAdapter2);
        RvOr.addListenerForSingleValueEvent(new ValueEventListener() {
            /* class com.birjulabsinc.birjutv.MainActivity.AnonymousClass5 */

            @Override // com.google.firebase.database.ValueEventListener
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot contentSnapShot : dataSnapshot.getChildren()) {
                    MainActivity.this.originalsModelList.add((OriginalsModel) contentSnapShot.getValue(OriginalsModel.class));
                }
                MainActivity.this.originalsAdapter.notifyDataSetChanged();
            }

            @Override // com.google.firebase.database.ValueEventListener
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                MainActivity.this.finish();
            }
        });
    }

    public void renewItems(View view) {
        this.slideList = new ArrayList();
        this.slideList.add(new SliderModel());
        this.adapter.renewItems(this.slideList);
        this.adapter.deleteItem(0);
    }

    private void loadSliderFirebase() {
        this.myRef.child("trailer").addListenerForSingleValueEvent(new ValueEventListener() {
            /* class com.birjulabsinc.birjutv.MainActivity.AnonymousClass6 */

            @Override // com.google.firebase.database.ValueEventListener
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot contentSnapShot : dataSnapshot.getChildren()) {
                    MainActivity.this.slideList.add((SliderModel) contentSnapShot.getValue(SliderModel.class));
                }
                MainActivity.this.adapter.notifyDataSetChanged();
            }

            @Override // com.google.firebase.database.ValueEventListener
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                MainActivity.this.finish();
            }
        });
    }
    public void forceUpdate()
    {
        PackageManager packageManager = this.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo =  packageManager.getPackageInfo(getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String currentVersion = packageInfo.versionName;
        new ForceUpdateAsync(currentVersion,MainActivity.this).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {


            case R.id.Rate:

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getString(R.string.packegname)));
                startActivity(intent);

                break;
            default:
                //  break;

            case R.id.sharemenu:


                FirebaseDatabase.getInstance().getReference().child("ver").child("updatelink").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String url = dataSnapshot.getValue().toString();

                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);


                        sharingIntent.setType("text/plain");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "*Please Download  Agasobanuye App here! and watch movies for free!  *\n\n------------------------------\n\n*Download Now* - \n\n" + url);
                        //sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
                        sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        //startActivity(Intent.createChooser(sharingIntent, "Share using"));

                        PackageManager packageManager = getPackageManager();
                        if (sharingIntent.resolveActivity(packageManager) != null) {
                            startActivity(sharingIntent);
                            // Broadcast the Intent.
                            startActivity(Intent.createChooser(sharingIntent, "Share to"));
                        }



                    }

                    @Override public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                break;


        }


        return true;
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        numActivityRestarted++;

        if (canShowAppOpenAd()) {
            appOpenAdManager.showAdIfAvailable();
        }
    }

    private boolean canShowAppOpenAd() {
        return numActivityRestarted % 3 == 0;
    }
}
