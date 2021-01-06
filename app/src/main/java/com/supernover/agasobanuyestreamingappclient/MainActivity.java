package com.supernover.agasobanuyestreamingappclient;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //.addTestDevice("2B8A66841577BC8BDE80A595867FC2A4") // Enter your test device id here from Logcat
                .build();
        adViewMessage.loadAd(adRequest);
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
        this.imageUpdate = (ImageView) findViewById( R.id.image_update);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("Agasobanuye");
        this.imageUpdate.setOnClickListener(new View.OnClickListener() {
            /* class com.birjulabsinc.birjutv.MainActivity.AnonymousClass1 */

            public void onClick(View v) {
                MainActivity.this.appUpdate();
            }
        });
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
    private void appUpdate() {
        final Context context = getApplicationContext();
        String myVersionName = "";
        try {
            myVersionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String finalMyVersionName = myVersionName;
        FirebaseDatabase.getInstance().getReference().child("Update").child("version").addValueEventListener( new ValueEventListener() {
            /* class com.birjulabsinc.birjutv.MainActivity.AnonymousClass2 */

            @Override // com.google.firebase.database.ValueEventListener
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (finalMyVersionName.equals((String) dataSnapshot.getValue(String.class))) {
                    Toast.makeText(context, "Already Updated", Toast.LENGTH_SHORT).show();
                    return;
                }
                MainActivity.this.downloadApk();
                Toast.makeText(context, "Download starts", Toast.LENGTH_SHORT).show();
            }

            @Override // com.google.firebase.database.ValueEventListener
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void downloadApk() {
        if (Build.VERSION.SDK_INT < 23) {
            startDownloading();
        } else if (checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 100);
        } else {
            startDownloading();
        }
    }

    private void startDownloading() {
        FirebaseDatabase.getInstance().getReference().child("Update").child("apk").addValueEventListener(new ValueEventListener() {
            /* class com.birjulabsinc.birjutv.MainActivity.AnonymousClass3 */
            static final /* synthetic */ boolean $assertionsDisabled = false;

            @SuppressLint("WrongConstant")
            @Override // com.google.firebase.database.ValueEventListener
            public void onDataChange(DataSnapshot dataSnapshot) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse((String) dataSnapshot.getValue(String.class)));
                request.setAllowedNetworkTypes(3);
                request.setTitle("Birju TV.apk");
                request.setDescription("Downloading file.....");
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(1);
                request.setDestinationInExternalPublicDir("/IT Guru", "Birju TV.apk");
                ((DownloadManager) MainActivity.this.getSystemService("download")).enqueue(request);
            }

            @Override // com.google.firebase.database.ValueEventListener
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override // androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback, androidx.fragment.app.FragmentActivity
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults[0] == 0) {
                startDownloading();
                Toast.makeText(this, "Permission granrted", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "Permission not granrted", Toast.LENGTH_SHORT).show();
        }
    }

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
