# OpenCVforAndroid
android本地化集成OpenCV机器视觉库,只需compile添加一下依赖即可完成OpenCV的集成.


## sample

一个使用OpenCV进行图片高斯模糊的例子.  

## 使用方法  

[![](https://jitpack.io/v/miqt/OpenCVforAndroid.svg)](https://jitpack.io/#miqt/OpenCVforAndroid)  

Add it in your root build.gradle at the end of repositories:  
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency  

```
	dependencies {
	        compile 'com.github.miqt:OpenCVforAndroid:v2.4.13'
	}
```  

## 代码示例

```java
    @Override
    public void onResume() {
        super.onResume();
        //使用前必须要loader成功才可以.
        if (!OpenCVLoader.initDebug()) {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_13, this, mLoaderCallback);
        } else {
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");

                    //loader成功,在这里完成你的工作.

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
```
