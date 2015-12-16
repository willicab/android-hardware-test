package ve.gob.vit.calidad;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    TextView txtMainReport;
    String report;
    public static Class pruebas[] = new Class[20];
    public static int cuenta = 0;
    public static int actual = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtMainReport = (TextView)findViewById(R.id.txtMainReport);
        PackageManager pm = getPackageManager();


        if (pm.hasSystemFeature(PackageManager.FEATURE_WIFI) == true) {
            pruebas[cuenta] = WifiActivity.class;
            cuenta++;
        }
        if (pm.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH) == true) {
            pruebas[cuenta] = BtActivity.class;
            cuenta++;
        }
        if (pm.hasSystemFeature(PackageManager.FEATURE_MICROPHONE) == true) {
            pruebas[cuenta] = MicActivity.class;
            cuenta++;
        }
        if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT) == true || pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) == true) {
            pruebas[cuenta] = CamActivity.class;
            cuenta++;
        }
        if (pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY_GSM) == true) {
            pruebas[cuenta] = GsmActivity.class;
            cuenta++;
        }
        if (pm.hasSystemFeature(PackageManager.FEATURE_NFC) == true) {
            pruebas[cuenta] = NfcActivity.class;
            cuenta++;

        }
        if (pm.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS) == true) {
            pruebas[cuenta] = GpsActivity.class;
            cuenta++;
        }
        if (pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER) == true) {
            pruebas[cuenta] = AccelActivity.class;
            cuenta++;
        }
        if (pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_COMPASS) == true) {
            pruebas[cuenta] = CompassActivity.class;
            cuenta++;
        }
        if (pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_GYROSCOPE) == true) {
            pruebas[cuenta] = GyroActivity.class;
            cuenta++;
        }
        if (pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_LIGHT) == true) {
            pruebas[cuenta] = LightActivity.class;
            cuenta++;
        }
        if (pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_PROXIMITY) == true) {
            pruebas[cuenta] = ProxActivity.class;
            cuenta++;
        }
        if (pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_BAROMETER) == true) {
            pruebas[cuenta] = BarActivity.class;
            cuenta++;
        }
        if (pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER) == true) {
            pruebas[cuenta] = StepActivity.class;
            cuenta++;
        }
        pruebas[cuenta] = FinalActivity.class;

        //report = String.valueOf(pruebas.length) + " " + String.valueOf(cuenta);

        //for (int i = 0; i <= cuenta; i++) {
        //    report += String.valueOf(pruebas[i]) + "<br>";
        //}

        report = "<h1>Hardware Detectado</h1>";
        report += pm.hasSystemFeature(PackageManager.FEATURE_WIFI) == true ? "- Tarjeta Wi-Fi<br>" : "";
        report += pm.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH) == true ? "- Bluetooth<br>" : "";
        report += pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) == true ? "- Cámara Trasera<br>" : "";
        report += pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT) == true ? "- Cámara Frontal<br>" : "";
        report += pm.hasSystemFeature(PackageManager.FEATURE_MICROPHONE) == true ? "- Micrófono<br>" : "";
        report += pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY_GSM) == true ? "- Puerto GSM<br>" : "";
        report += pm.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS) == true ? "- GPS<br>" : "";
        report += pm.hasSystemFeature(PackageManager.FEATURE_NFC) == true ? "- Lector NFC<br>" : "";

        report += "<br><h1>Sensores Detectados</h1>";
        report += pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER) == true ? "- Acelerómetro<br>" : "";
        report += pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_COMPASS) == true ? "- Brújula<br>" : "";
        report += pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_GYROSCOPE) == true ? "- Giroscopio<br>" : "";
        report += pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_LIGHT) == true ? "- Luz<br>" : "";
        report += pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_PROXIMITY) == true ? "- Proximidad<br>" : "";
        report += pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_BAROMETER) == true ? "- Barómetro<br>" : "";
        report += pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER) == true ? "- Contador de pasos<br>" : "";
        report += pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_DETECTOR) == true ? "- Detector de pasos<br>" : "";

        report += pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_RELATIVE_HUMIDITY) == true ? "- Humedad relativa<br>" : "";
        report += pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_AMBIENT_TEMPERATURE) == true ? "- Temperatura<br>" : "";
        report += pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_HEART_RATE) == true ? "- Ritmo cardíaco<br>" : "";
        report += pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_HEART_RATE_ECG) == true ? "- Electrocardiograma<br>" : "";

        txtMainReport.setText(Html.fromHtml(report), TextView.BufferType.SPANNABLE);

        WriteReadFile.removeFile("");
        WriteReadFile.writeToFile("<h1>Reporte de Pruebas de Hardware</h1>");

    }

    public void iniciarPruebas(View view) {
        Intent intent = new Intent(this, pruebas[actual]);
        actual++;
        startActivity(intent);
        //Intent intent = new Intent(Intent.ACTION_DELETE);
        //intent.setData(Uri.parse("package:ve.gob.vit.calidad"));
        //startActivity(intent);
    }
    @Override
    public void onBackPressed() {}
}