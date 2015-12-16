package ve.gob.vit.calidad;

import java.io.IOException;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CamActivity extends AppCompatActivity implements SurfaceHolder.Callback{

    public Camera camera;
    public SurfaceView surfaceView;
    public SurfaceHolder surfaceHolder;
    public TextView txtReport;
    public Button btnRear;
    public Button btnFront;
    public Button btnNext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);

        btnRear = (Button)findViewById(R.id.btnRearCam);
        btnFront = (Button)findViewById(R.id.btnFrontcam);
        btnNext = (Button)findViewById(R.id.btnCamNext);

        getWindow().setFormat(PixelFormat.UNKNOWN);
        surfaceView = (SurfaceView)findViewById(R.id.surfaceview);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        txtReport = (TextView)findViewById(R.id.txtCamReport);
        txtReport.setText(Html.fromHtml("<h1>Prueba de Cámara</h1>"));
        if (camera.getNumberOfCameras() > 1) {
            txtReport.append(Html.fromHtml("<font color='#00cc00'>Se han detectado " + String.valueOf(camera.getNumberOfCameras()) + " cámaras</font><br>"));
            WriteReadFile.writeToFile("- <strong>Cámara:</strong> <font color='#00cc00'>Se han detectado " + String.valueOf(camera.getNumberOfCameras()) + " cámaras</font><br>\n");
        } else if (camera.getNumberOfCameras() == 1) {
            txtReport.append(Html.fromHtml("<font color='#cccc00'>Se ha detectado 1 cámara</font><br>"));
            WriteReadFile.writeToFile("- <strong>Cámara:</strong> <font color='#00cc00'>Se ha detectado 1 cámara</font><br>\n");
        } else {
            txtReport.append(Html.fromHtml("<font color='#cc0000'>No se ha detectado ninguna cámara</font><br>"));
            WriteReadFile.writeToFile("- <strong>Cámara:</strong> <font color='#cc0000'>No se ha detectado ninguna cámara</font><br>\n");
        }

    }

    public void rearCamera(View v){
        // TODO Auto-generated method stub
        btnRear.setVisibility(View.GONE);
        btnFront.setVisibility(View.VISIBLE);
        camera = Camera.open(0);
        camera.setDisplayOrientation(90);

        if (camera != null){
            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
                txtReport.append(Html.fromHtml("<font color='#0000cc'>Probando cámara trasera</font><br>"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void frontCamera(View v){
        // TODO Auto-generated method stub
        btnFront.setVisibility(View.GONE);
        btnNext.setVisibility(View.VISIBLE);
        camera.stopPreview();
        camera.release();
        camera = null;
        camera = Camera.open(1);
        camera.setDisplayOrientation(270);

        if (camera != null){
            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
                txtReport.append(Html.fromHtml("<font color='#0000cc'>Probando cámara frontal</font><br>"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void btnSiguiente(View view) {
        Intent intent = new Intent(this, MainActivity.pruebas[MainActivity.actual]);
        MainActivity.actual++;
        startActivity(intent);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub

    }
    @Override
    public void onBackPressed() {}
}