package com.miqt.opencvdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "OpenCV_MainActivity";

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");

                    //<editor-fold desc="高斯模糊">
                    ImageView image_src = (ImageView) findViewById(R.id.image_src);
                    ImageView image_result = (ImageView) findViewById(R.id.image_result);

                    Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.image);
                    Mat mat = new Mat(src.getWidth(), src.getHeight(), CvType.CV_8UC4);
                    Utils.bitmapToMat(src, mat);
                    Imgproc.blur(mat, mat, new Size(30, 30));
                    Bitmap bitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(mat, bitmap);
                    image_src.setImageBitmap(src);
                    image_result.setImageBitmap(bitmap);
                    //</editor-fold>
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_13, this, mLoaderCallback);
        } else {
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }


}