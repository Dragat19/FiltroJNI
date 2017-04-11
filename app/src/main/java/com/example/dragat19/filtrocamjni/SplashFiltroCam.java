package com.example.dragat19.filtrocamjni;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import gr.net.maroulis.library.EasySplashScreen;

/**
 * Created by Dragat19 on 11/04/2017.
 */

public class SplashFiltroCam extends Activity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen config = new EasySplashScreen(SplashFiltroCam.this)
                .withFullScreen()
                .withTargetActivity(FiltroCamJni.class)
                .withSplashTimeOut(8000)
                .withBackgroundResource(android.R.color.holo_blue_dark)
                .withHeaderText("Desarrollador Android Albert Sánchez")
                .withFooterText("CAMONAPP 2017")
                .withBeforeLogoText("Filtro JNI (Carry)")
                .withLogo(R.drawable.filtrocam)
                .withAfterLogoText("Aplicación para Prueba Tecnica");


        //set your own animations
        myCustomTextViewAnimation(config.getAfterLogoTextView());
        myCustomTextViewAnimation(config.getBeforeLogoTextView());
        myCustomTextViewAnimation(config.getHeaderTextView());
        myCustomTextViewAnimation(config.getFooterTextView());


        //customize all TextViews
        Typeface pacificoFont = Typeface.createFromAsset(getAssets(),"FtraBd_0.ttf");
        Typeface pacificoFont2 = Typeface.createFromAsset(getAssets(),"FtraBk_0.ttf");

        config.getAfterLogoTextView().setTypeface(pacificoFont);
        config.getBeforeLogoTextView().setTypeface(pacificoFont);
        config.getHeaderTextView().setTypeface(pacificoFont2);
        config.getFooterTextView().setTypeface(pacificoFont2);

        config.getAfterLogoTextView().setTextColor(Color.parseColor("#212121"));
        config.getBeforeLogoTextView().setTextColor(Color.parseColor("#212121"));
        config.getHeaderTextView().setTextColor(Color.WHITE);
        config.getFooterTextView().setTextColor(Color.WHITE);

        //create the view
        View easySplashScreenView = config.create();
        setContentView(easySplashScreenView);
    }

    private void myCustomTextViewAnimation(TextView tv){
        Animation animation=new TranslateAnimation(300,0,0,0);
        animation.setDuration(1200);
        tv.startAnimation(animation);
    }
}

