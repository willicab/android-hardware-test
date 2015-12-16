package ve.gob.vit.calidad;

import android.content.Context;
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

public class ProxActivity extends AppCompatActivity {
    /** Called when the activity is first created. */

    TextView txtTitle, ProximityReading;

    SensorManager mySensorManager;
    Sensor myProximitySensor;
    public String report = "- <strong>Sensor de Proximidad:</strong> <font color='#cc0000'>No se detectó actividad</font><br>\n";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prox);
        txtTitle = (TextView)findViewById(R.id.txtProxTitle);
        txtTitle.setText(Html.fromHtml("<h1>Prueba de Sensor de Proximidad</h1>"));
        ProximityReading = (TextView)findViewById(R.id.proximityReading);

        mySensorManager = (SensorManager)getSystemService(
                Context.SENSOR_SERVICE);
        myProximitySensor = mySensorManager.getDefaultSensor(
                Sensor.TYPE_PROXIMITY);

        if (myProximitySensor == null){
            //ProximitySensor.setText("No Proximity Sensor!");
            report = "- <strong>Sensor de Proximidad:</strong> <font color='#cc0000'>No se detectó sensor</font><br>\n";
        }else{
            //ProximitySensor.setText(myProximitySensor.getName());
            //ProximityMax.setText("Maximum Range: "
            //        + String.valueOf(myProximitySensor.getMaximumRange()));
            mySensorManager.registerListener(proximitySensorEventListener,
                    myProximitySensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    SensorEventListener proximitySensorEventListener
            = new SensorEventListener(){

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            // TODO Auto-generated method stub

            if(event.sensor.getType()==Sensor.TYPE_PROXIMITY){
                report = "- <strong>Sensor de Proximidad:</strong> <font color='#00cc00'>Se detectó actividad</font><br>\n";
                ProximityReading.setText("Lectura de sensor: "
                        + String.valueOf(event.values[0]));
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