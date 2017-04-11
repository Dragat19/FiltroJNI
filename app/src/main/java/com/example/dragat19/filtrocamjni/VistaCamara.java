package com.example.dragat19.filtrocamjni;


import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.ImageView;

import java.io.IOException;

/**
 * Created by Dragat19 on 10/04/2017.
 */

public class VistaCamara implements SurfaceHolder.Callback, Camera.PreviewCallback{
    private Camera mCamara;
    private ImageView visualizacionCamara = null;
    private Bitmap bitmap = null;
    private int[] pixels = null;
    private byte[] datosMarco = null;
    private int formatoImagen;
    private int visulizacionAncho;
    private int visualizacionAlto;
    private boolean procesando;

    Handler mHandler = new Handler(Looper.getMainLooper());

    public VistaCamara(int layoutAncho, int layoutAlto , ImageView vistaCamara) {

        visualizacionAlto = layoutAlto;
        visulizacionAncho = layoutAncho;
        visualizacionCamara = vistaCamara;

        bitmap = Bitmap.createBitmap(visulizacionAncho, visualizacionAlto, Bitmap.Config.ARGB_8888);
        pixels = new int[visulizacionAncho * visualizacionAlto];
    }



    public void onPause()
    {
        mCamara.stopPreview();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        mCamara = Camera.open();

        try {
            mCamara.setPreviewDisplay(holder);
            mCamara.setPreviewCallback(this);

        } catch (IOException e) {
            mCamara.release();
            mCamara = null;
        }


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Camera.Parameters parameters;

        parameters = mCamara.getParameters();
        parameters.setPreviewSize(visulizacionAncho,visualizacionAlto);
        parameters.setRotation(90);
        formatoImagen = parameters.getPreviewFormat();
        mCamara.setParameters(parameters);
        mCamara.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        mCamara.setPreviewCallback(null);
        mCamara.stopPreview();
        mCamara.release();
        mCamara = null;
    }

    public native boolean ProcesamientoImagen(int ancho,int alto,
                                              byte[] formatoNV21, int[] pixeles);
    static {
        System.loadLibrary("native-lib");
    }


    private Runnable Procesando = new Runnable(){

        public void run(){
            Log.i("Vista de Imagen", "Procesando...:");
            procesando = true;
            ProcesamientoImagen(visulizacionAncho,visualizacionAlto,datosMarco,pixels);

            bitmap.setPixels(pixels,0,visulizacionAncho, 0, 0, visulizacionAncho, visualizacionAlto);
            visualizacionCamara.setImageBitmap(bitmap);
            procesando = false;
        }

    };

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {

        if (formatoImagen == ImageFormat.NV21){

            if (!procesando){
                datosMarco = data;
                mHandler.post(Procesando);

            }
        }

    }

}
