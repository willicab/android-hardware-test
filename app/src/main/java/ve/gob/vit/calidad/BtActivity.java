package ve.gob.vit.calidad;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BtActivity extends AppCompatActivity {
    TextView txtReport;
    Button btnNext;
    BluetoothAdapter btAdapter;
    String report = "- <strong>Bluetooth:</strong> <font color='#cc0000'>No se detectarón Dispositivos</font><br>\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bt);
        txtReport = (TextView)findViewById(R.id.txtBtReport);
        btnNext = (Button)findViewById(R.id.btnBtNext);
        txtReport.append(Html.fromHtml("<h1>Prueba de Bluetooth</h1>"));

        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!btAdapter.isEnabled()) {
            btAdapter.enable();
            txtReport.append(Html.fromHtml("<font color='#cccc00'>El bluetooth está desactivado.</font><br>Activando...<br>"));
            SystemClock.sleep(3000);
        }
        btAdapter.startDiscovery();

        final BroadcastReceiver bReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    txtReport.append(Html.fromHtml("<h2><font color='#00cc00'>Se ha detectado por lo menos un dispositivo bluetooh (Pasó)</font></h2>"));
                    report = "- <strong>Bluetooth:</strong> <font color='#00cc00'>Se detectarón Dispositivos</font><br>\n";
                    btAdapter.cancelDiscovery();
                    btAdapter.disable();
                    btnNext.setEnabled(true);
                } else {
                    txtReport.append(Html.fromHtml("<h2><font color='#cc0000'>No se ha detectado ningún dispositivo (Falló)</font></h2>"));
                    report = "- <strong>Bluetooth:</strong> <font color='#cc0000'>No se detectarón Dispositivos</font><br>\n";
                }
            }
        };
        registerReceiver(bReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
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
