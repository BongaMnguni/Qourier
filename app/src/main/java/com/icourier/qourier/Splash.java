package com.icourier.qourier;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class Splash extends FragmentActivity {
    SharedPreferences sharedpreferences;
    ImageView IMAGE_VIEW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        /*int resID=getResources().getIdentifier("welcomeaudio", "raw", getPackageName());

        MediaPlayer mediaPlayer=MediaPlayer.create(this,resID);
        mediaPlayer.start();*/

        sharedpreferences = getSharedPreferences("Packages", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();

        IMAGE_VIEW = (ImageView)findViewById(R.id.imageView7);

        Glide.with(getApplicationContext()).load(R.drawable.deliver).into(new GlideDrawableImageViewTarget(IMAGE_VIEW));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            startActivity(new Intent(getApplicationContext(),TabActivity.class));
                finish();
            }
        }, 9000);
    }

}
