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

public class AccelActivity extends AppCompatActivity implements SensorEventListener {

    private float lastX, lastY, lastZ;
    private SensorManager sensorManager;
    private Sensor accelerometer;

    private float deltaXMax = 0;
    private float deltaYMax = 0;
    private float deltaZMax = 0;

    private float deltaX = 0;
    private float deltaY = 0;
    private float deltaZ = 0;

    private TextView currentX, currentY, currentZ, maxX, maxY, maxZ;

    public String report = "- <strong>Acelerómetro:</strong> <font color='#cc0000'>No se detectó actividad</font><br>\n";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accel);
        initializeViews();

        TextView txtTitle = (TextView)findViewById(R.id.txtAccelTitle);
        txtTitle.setText(Html.fromHtml("<h1>Prueba de Acelerómetro</h1>"));
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            report = "- <strong>Acelerómetro:</strong> <font color='#cc0000'>No se detectó acelerómetro</font><br>\n";
        }
    }

    public void initializeViews() {
        currentX = (TextView) findViewById(R.id.currentX);
        currentY = (TextView) findViewById(R.id.currentY);
        currentZ = (TextView) findViewById(R.id.currentZ);

        maxX = (TextView) findViewById(R.id.maxX);
        maxY = (TextView) findViewById(R.id.maxY);
        maxZ = (TextView) findViewById(R.id.maxZ);
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        deltaX = Math.abs(lastX - event.values[0]);
        deltaY = Math.abs(lastY - event.values[1]);
        deltaZ = Math.abs(lastZ - event.values[2]);

        report = "- <strong>Acelerómetro:</strong> <font color='#00cc00'>Se detectó actividad</font><br>\n";

        if (deltaX < 2)
            deltaX = 0;
        if (deltaY < 2)
            deltaY = 0;

        displayCleanValues();
        displayCurrentValues();
        displayMaxValues();
    }

    public void displayCleanValues() {
        currentX.setText("0.0");
        currentY.setText("0.0");
        currentZ.setText("0.0");
    }

    public void displayCurrentValues() {
        currentX.setText(String.format("%.2f", (deltaX)));
        currentY.setText(String.format("%.2f", (deltaY)));
        currentZ.setText(String.format("%.2f", (deltaZ)));
    }

    public void displayMaxValues() {
        if (deltaX > deltaXMax) {
            deltaXMax = deltaX;
            maxX.setText(String.format("%.2f", (deltaXMax)));
        }
        if (deltaY > deltaYMax) {
            deltaYMax = deltaY;
            maxY.setText(String.format("%.2f", (deltaYMax)));
        }
        if (deltaZ > deltaZMax) {
            deltaZMax = deltaZ;
            maxZ.setText(String.format("%.2f", (deltaZMax)));
        }
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

