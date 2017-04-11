package com.example.dragat19.filtrocamjni;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.view.ViewGroup.LayoutParams;


public class FiltroCamJni extends AppCompatActivity {

    private VistaCamara camaraVista;
    private ImageView visualizacionCamara = null;
    private FrameLayout layoutPrincipal;
    private int visulizacionAncho = 640;
    private int visualizacionAlto = 480;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_filtro_cam_jni);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        visualizacionCamara = new ImageView(this);
        SurfaceView camView = new SurfaceView(this);
        SurfaceHolder camHolder = camView.getHolder();
        camaraVista = new VistaCamara(visulizacionAncho,visualizacionAlto,visualizacionCamara);

        camHolder.addCallback(camaraVista);
        camHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        layoutPrincipal = (FrameLayout) findViewById(R.id.frameLayout1);
        layoutPrincipal.addView(camView, new LayoutParams(visulizacionAncho*2,visualizacionAlto*2));
        layoutPrincipal.addView(visualizacionCamara, new LayoutParams( visulizacionAncho*2,visualizacionAlto*2));

    }

}
