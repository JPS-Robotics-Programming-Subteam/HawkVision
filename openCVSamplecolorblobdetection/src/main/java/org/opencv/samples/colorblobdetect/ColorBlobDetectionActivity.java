package org.opencv.samples.colorblobdetect;

        import org.opencv.android.BaseLoaderCallback;
        import org.opencv.android.CameraBridgeViewBase;
        import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
        import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
        import org.opencv.android.LoaderCallbackInterface;
        import org.opencv.android.OpenCVLoader;
        import org.opencv.core.*;
        import org.opencv.core.MatOfPoint;
        import org.opencv.imgproc.Imgproc;

        import android.os.Bundle;
        import android.app.Activity;
        import android.util.Log;
        import android.view.SurfaceView;
        import android.view.WindowManager;
        import java.util.ArrayList;
        import java.util.List;

        import static org.opencv.imgproc.Imgproc.CHAIN_APPROX_SIMPLE;
        import static org.opencv.imgproc.Imgproc.RETR_TREE;

public class ColorBlobDetectionActivity extends Activity implements CvCameraViewListener2 {

    private static final String TAG = "OCVSample::Activity";
    private CameraBridgeViewBase mOpenCvCameraView;
    private Scalar lower_blue = new Scalar(40,55,55);
    private Scalar upper_blue = new Scalar(80,255,255);
    private Scalar drawColor = new Scalar(255,0,0);
    private Rect boundingRect;
    private List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
    private Mat hierarchy;
    private Mat mRgba;
    private Mat mHSV;
    private Mat mFinal;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public ColorBlobDetectionActivity() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.color_blob_detection_surface_view);

        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.tutorial1_activity_java_surface_view);
        mOpenCvCameraView.setMaxFrameSize(512,384);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);

        mOpenCvCameraView.setCvCameraViewListener(this);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }


    public void onCameraViewStarted(int width, int height) {
        mHSV = new Mat();
        mRgba = new Mat();
        mFinal = new Mat();
        hierarchy = new Mat();
    }

    public void onCameraViewStopped() {
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
/*
        mRgba = inputFrame.rgba();
        contours.clear();
        Imgproc.cvtColor(mRgba, mHSV, Imgproc.COLOR_RGB2HSV);
        Imgproc.GaussianBlur(mHSV,mFinal, new Size(3,3), 0, 0);
        Core.inRange(mHSV, lower_blue, upper_blue, mFinal);
        Imgproc.findContours(mFinal,contours, hierarchy, RETR_TREE, CHAIN_APPROX_SIMPLE);
        for(int i = 0; i < contours.size(); i++) {
            if( Imgproc.boundingRect(contours.get(i)).area() > 1000){
                Rect rect = Imgproc.boundingRect(contours.get(i));
                Core.rectangle(mRgba, new Point(rect.x, rect.y), new Point (rect.x + rect.width, rect.y + rect.height), drawColor, 1 );
            }
        }
        return mRgba;
        */
        mRgba = inputFrame.rgba();
        contours.clear();
        Imgproc.cvtColor(mRgba, mHSV, Imgproc.COLOR_RGB2HSV);
        Imgproc.GaussianBlur(mHSV,mFinal, new Size(3,3), 0, 0);
        Core.inRange(mHSV, lower_blue, upper_blue, mFinal);
        Imgproc.findContours(mFinal, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        for (int i = 0; i < contours.size(); i++) {
            Imgproc.drawContours(mRgba,contours,i, drawColor,-1);
        }
        return mRgba;
    }

    public MatOfPoint hull2Points(MatOfInt hull, MatOfPoint contour) {
        List<Integer> indexes = hull.toList();
        List<Point> points = new ArrayList<Point>();
        MatOfPoint point= new MatOfPoint();
        for(Integer index:indexes) {
            points.add(contour.toList().get(index));
        }
        point.fromList(points);
        //hellotestingifgithubisworkinglalalala
        return point;
    }
}