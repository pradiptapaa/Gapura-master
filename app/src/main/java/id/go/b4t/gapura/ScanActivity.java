package id.go.b4t.gapura;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import static android.widget.Toast.LENGTH_LONG;

public class ScanActivity extends AppCompatActivity {

    SurfaceView cameraView;
    BarcodeDetector barcode;
    CameraSource cameraSource;
    SurfaceHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        cameraView = (SurfaceView) findViewById(R.id.cameraSurface);
        cameraView.setZOrderMediaOverlay(true);
        holder = cameraView.getHolder();

        //Instansiasi Builder barcode agar dapat dipakai
        barcode = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();
        if (!barcode.isOperational()) {
            Toast.makeText(getApplicationContext(), "Sorry, Couldn't Connect", LENGTH_LONG);
            this.finish();


            //Instansiasi Builder agar camera dapat dipakai
            cameraSource = new CameraSource.Builder(this, barcode)
                    .setAutoFocusEnabled(true)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedFps(64)
                    .setRequestedPreviewSize(1920, 1024)
                    .build();

            //Penambahan fungsi callBack pada pemuat objek kamera
            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                //artinya yang lama akan menimpa fungsi baru yang serupa
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try {
                        //liat nilai
                        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                            cameraSource.start(cameraView.getHolder());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    cameraSource.stop();
                }
            });

            barcode.setProcessor(new Detector.Processor<Barcode>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<Barcode> detections) {
                    final SparseArray<Barcode> barcodeS = detections.getDetectedItems();
                    if (barcodeS.size() > 0) {
                        Intent intent2 = new Intent();
                        intent2.putExtra("barcode", barcodeS.valueAt(0));
                        setResult(RESULT_OK, intent2);
                        finish();
                    }
                }
            });
        }
    }
}
