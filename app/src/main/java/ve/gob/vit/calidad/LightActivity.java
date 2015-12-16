package ve.gob.vit.calidad;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LightActivity extends AppCompatActivity {

    TextView textLIGHT_available, textLIGHT_reading;
    ProgressBar progress;
    public String report = "- <strong>Sensor de Luz:</strong> <font color='#cc0000'>No se detectó actividad</font><br>\n";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        textLIGHT_available
                = (TextView)findViewById(R.id.LIGHT_available);
        textLIGHT_available.setText(Html.fromHtml("Prueba de Sensor de Luz</h1>"));
        textLIGHT_reading
                = (TextView)findViewById(R.id.LIGHT_reading);
        progress = (ProgressBar)findViewById(R.id.progressBar);

        SensorManager mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        Sensor LightSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mySensorManager.registerListener(
                LightSensorListener,
                LightSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    private final SensorEventListener LightSensorListener
            = new SensorEventListener(){

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_LIGHT){
                int val =  Math.round(event.values[0]);
                int max = Math.round(event.sensor.getMaximumRange());
                int perc = Math.round((val * 100) / max);
                progress.setProgress(perc);
                textLIGHT_reading.setText("Nivel de Luz: " + val);
                report = "- <strong>Sensor de Luz:</strong> <font color='#00cc00'>Se detectó actividad</font><br>\n";
            }
        }

    };
    public void btnSiguiente(View view) {
        WriteReadFile.writeToFile(report);
        Intent intent = new Intent(this, MainActivity.pruebas[MainActivity.actual]);
        MainActivity.actual++;
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {}
}