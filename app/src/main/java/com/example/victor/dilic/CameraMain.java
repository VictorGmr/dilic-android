package com.example.victor.dilic;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.media.Image;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class CameraMain extends AppCompatActivity {


    private Camera mCamera;
    private CameraPreview mPreview;
    private Camera.PictureCallback mPicture;
    private Button capture;
    private Context myContext;
    private ConstraintLayout cameraPreview;

    public static Bitmap bitmap;




    @Override
    protected void onCreate(Bundle savedInstanceState) {



        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.camera_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        myContext = this;


        cameraPreview = (ConstraintLayout) findViewById(R.id.cPreview);

        ImageView imgView = (ImageView) findViewById(R.id.imageView3Copy);

        BitmapDrawable drawable = (BitmapDrawable) imgView.getDrawable();
        Bitmap bitmap2 = drawable.getBitmap();
        bitmap2 = bitmap2.copy( Bitmap.Config.ARGB_8888 , true);

        for(int x = 100; x < 110; x++){
            for(int y = 20; y < 30; y++){
                bitmap2.setPixel(x,y,Color.RED);
            }
        }

        for(int x = 100; x < 110; x++){
            for(int y = 150; y < 160; y++){
                bitmap2.setPixel(x,y,Color.RED);
            }
        }

        for(int x = 230; x < 240; x++){
            for(int y = 20; y < 30; y++){
                bitmap2.setPixel(x,y,Color.RED);
            }
        }

        for(int x = 230; x < 240; x++){
            for(int y = 150; y < 160; y++){
                bitmap2.setPixel(x,y,Color.RED);
            }
        }

        for(int x = 360; x < 370; x++){
            for(int y = 20; y < 30; y++){
                bitmap2.setPixel(x,y,Color.RED);
            }
        }

        for(int x = 360; x < 370; x++){
            for(int y = 150; y < 160; y++){
                bitmap2.setPixel(x,y,Color.RED);
            }
        }

        for(int x = 490; x < 500; x++){
            for(int y = 20; y < 30; y++){
                bitmap2.setPixel(x,y,Color.RED);
            }
        }

        for(int x = 490; x < 500; x++){
            for(int y = 150; y < 160; y++){
                bitmap2.setPixel(x,y,Color.RED);
            }
        }

        for(int x = 620; x < 630; x++){
            for(int y = 20; y < 30; y++){
                bitmap2.setPixel(x,y,Color.RED);
            }
        }

        for(int x = 620; x < 630; x++){
            for(int y = 150; y < 160; y++){
                bitmap2.setPixel(x,y,Color.RED);
            }
        }

        for(int x = 750; x < 760; x++){
            for(int y = 20; y < 30; y++){
                bitmap2.setPixel(x,y,Color.RED);
            }
        }

        for(int x = 750; x < 760; x++){
            for(int y = 150; y < 160; y++){
                bitmap2.setPixel(x,y,Color.RED);
            }
        }

        for(int x = 880; x < 890; x++){
            for(int y = 20; y < 30; y++){
                bitmap2.setPixel(x,y,Color.RED);
            }
        }

        for(int x = 880; x < 890; x++){
            for(int y = 150; y < 160; y++){
                bitmap2.setPixel(x,y,Color.RED);
            }
        }



        //largura bloco = 295

        imgView.setImageBitmap(bitmap2);


        mCamera.open();
        mPreview = new CameraPreview(myContext, mCamera);

        cameraPreview.addView(mPreview);


        capture =  findViewById(R.id.btnCam);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.takePicture(null, null, mPicture);
            }
        });



        /*initializeCamera();


        mCamera.startPreview();*/

    }

    /*public void initializeCamera(){

        mCamera = Camera.open();
        mCamera.setDisplayOrientation(90);
        mPicture = getPictureCallback();
        mPreview.refreshCamera(mCamera);

    }*/



    public void onResume() {



        super.onResume();
        if(mCamera == null) {
            mCamera = Camera.open();
            mCamera.setDisplayOrientation(90);
            mPicture = getPictureCallback();
            mPreview.refreshCamera(mCamera);
            Log.d("nu", "null");
        }else {
            Log.d("nu","no null");
        }

    }



    @Override
    protected void onPause() {
        super.onPause();
        //when on Pause, release camera in order to be used from other applications
        releaseCamera();
    }

    private void releaseCamera() {
        // stop and release camera
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private Camera.PictureCallback getPictureCallback() {
        Camera.PictureCallback picture = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {




                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                bitmap = bitmap.copy( Bitmap.Config.ARGB_8888 , true);

                for(int i = 0; i < bitmap.getWidth(); i++){
                    for(int j = 0; j < bitmap.getHeight(); j++){

                        int color = bitmap.getPixel(i, j);

                        int r = Color.red(color);
                        int g = Color.green(color);
                        int b = Color.blue(color);

                        float[] hsv = new float[3];
                        Color.RGBToHSV(r, g, b, hsv);

                        if(hsv[2] * 2 < 1){
                            hsv[2] = hsv[2] * 2;
                        }


                        color = Color.HSVToColor(hsv);
                        bitmap.setPixel(i, j, color);

                    }
                }




                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                final byte[] byteArray = stream.toByteArray();


                Intent returnIntent = new Intent();
                returnIntent.putExtra("image", byteArray);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                overridePendingTransition(0, 0);


            }
        };
        return picture;
    }
}