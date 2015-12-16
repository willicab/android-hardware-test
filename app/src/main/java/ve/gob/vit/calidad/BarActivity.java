package ve.gob.vit.calidad;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class BarActivity extends AppCompatActivity implements SensorEventListener {

    private TextView pressView;

    public String report = "- <strong>Acelerómetro:</strong> <font color='#cc0000'>No se detectó actividad</font><br>\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        pressView = (TextView) findViewById(R.id.txtBarReport);

        // Look for pressure sensor
        SensorManager snsMgr = (SensorManager) getSystemService(Service.SENSOR_SERVICE);

        Sensor pS = snsMgr.getDefaultSensor(Sensor.TYPE_PRESSURE);

        snsMgr.registerListener(this, pS, SensorManager.SENSOR_DELAY_UI);
    }



    @Override
    protected void onResume() {

        super.onResume();
        SensorManager snsMgr = (SensorManager) getSystemService(Service.SENSOR_SERVICE);

        Sensor pS = snsMgr.getDefaultSensor(Sensor.TYPE_PRESSURE);

        snsMgr.registerListener(this, pS, SensorManager.SENSOR_DELAY_UI);
    }



    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        pressView.setText(Html.fromHtml("<h1>Prueba de Barómetro</h1>Presión Atmosférica (en milibares)<br>" + values[0]));
        report = "- <strong>Acelerómetro:</strong> <font color='#00cc00'>Se detectó actividad</font><br>\n";
    }


    public void btnSiguiente(View view) {
        WriteReadFile.writeToFile(report);
        Intent intent = new Intent(this, MainActivity.pruebas[MainActivity.actual]);
        MainActivity.actual++;
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {}

}