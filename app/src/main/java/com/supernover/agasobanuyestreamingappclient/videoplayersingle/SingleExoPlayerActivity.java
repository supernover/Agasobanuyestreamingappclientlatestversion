package com.supernover.agasobanuyestreamingappclient.videoplayersingle;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.supernover.agasobanuyestreamingappclient.R;

public class SingleExoPlayerActivity extends AppCompatActivity {
    private String VIDEO_TITLE;
    private String VIDEO_URL;
    private PlayerView playerView;
    ExoPlayer exoPlayer;

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_movie_player);
        this.VIDEO_URL = getIntent().getStringExtra("vid");
        this.VIDEO_TITLE = getIntent().getStringExtra("title");
        setSupportActionBar((Toolbar) findViewById(R.id.toolbarMovie));
        getSupportActionBar().setTitle(this.VIDEO_TITLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onStart() {
        super.onStart();
        this.playerView = (PlayerView) findViewById(R.id.exo_player_view);
        SimpleExoPlayer newSimpleInstance = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter.Builder(getApplicationContext()).build())));
        this.exoPlayer = newSimpleInstance;
        this.playerView.setPlayer(newSimpleInstance);
        this.exoPlayer.prepare(new ExtractorMediaSource.Factory(new DefaultDataSourceFactory(this, Util.getUserAgent(this, "appname"))).createMediaSource(Uri.parse(this.VIDEO_URL)));
        this.exoPlayer.setPlayWhenReady(true);
    }

    /* access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onDestroy() {
        super.onDestroy();
        this.exoPlayer.release();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}