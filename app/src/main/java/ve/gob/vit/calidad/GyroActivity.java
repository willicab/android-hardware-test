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
import android.widget.TextView;

public class GyroActivity extends AppCompatActivity implements SensorEventListener {
    //a TextView
    private TextView tv;
    //the Sensor Manager
    private SensorManager sManager;

    public String report = "- <strong>Giroscopio:</strong> <font color='#cc0000'>No se detectó actividad</font><br>\n";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyro);

        //get the TextView from the layout file
        tv = (TextView) findViewById(R.id.txtGyroReport);

        //get a hook to the sensor service
        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }

    //when this Activity starts
    @Override
    protected void onResume()
    {
        super.onResume();
		/*register the sensor listener to listen to the gyroscope sensor, use the
		 * callbacks defined in this class, and gather the sensor information as
		 * quick as possible*/
        sManager.registerListener(this, sManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_FASTEST);
    }

    //When this Activity isn't visible anymore
    @Override
    protected void onStop()
    {
        //unregister the sensor listener
        sManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1)
    {
        //Do nothing
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        //if sensor is unreliable, return void
        if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
        {
            return;
        }
        report = "- <strong>Giroscopio:</strong> <font color='#00cc00'>Se detectó actividad</font><br>\n";
        //else it will output the Roll, Pitch and Yawn values
        tv.setText(Html.fromHtml("<h1>Prueba de Gisroscopio</h1>" +
                "eje X (Roll) :" + String.format("%.2f", (event.values[2])) + "<br>" +
                "eje Y (Pitch) :" + String.format("%.2f", (event.values[1])) + "<br>" +
                "eje Z (Yaw) :" + String.format("%.2f", (event.values[0]))));
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