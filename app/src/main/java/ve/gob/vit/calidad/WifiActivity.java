package ve.gob.vit.calidad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class WifiActivity extends AppCompatActivity {
    WifiManager wifi;
    List<ScanResult> wifiList;
    TextView txtWifiReport;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        txtWifiReport = (TextView)findViewById(R.id.txtWifiReport);
        btnNext = (Button)findViewById(R.id.btnWifiNext);

        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        registerReceiver(mWifiScanReceiver,
                new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        iniciarPrueba();

    }

    private void iniciarPrueba(){
        txtWifiReport.append(Html.fromHtml("<h1>Prueba de Wi-Fi</h1>"));
        if (!wifi.isWifiEnabled()) {
            txtWifiReport.append(Html.fromHtml("<font color='#cccc00'>El Wi-Fi está desactivado.</font><br>Activando...<br>"));
            wifi.setWifiEnabled(true);
        }

        wifi.startScan();
    }

    private final BroadcastReceiver mWifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                wifiList = wifi.getScanResults();
                if (wifiList.size() > 1) {
                    txtWifiReport.append(Html.fromHtml("<h2><font color='green'>Se han detectado " + wifiList.size() + " redes (Pasó)</font></h2>"));
                    WriteReadFile.writeToFile("- <strong>Wi-Fi:</strong> <font color='#00cc00'>Se detectarón redes inalámbricas</font><br>\n");
                    btnNext.setEnabled(true);
                } else if (wifiList.size() == 1) {
                    txtWifiReport.append(Html.fromHtml("<h2><font color='green'>Se ha detectado " + wifiList.size() + " red (Pasó)</font></h2>"));
                    WriteReadFile.writeToFile("- <strong>Wi-Fi:</strong> <font color='#00cc00'>Se detectarón redes inalámbricas</font><br>\n");
                    btnNext.setEnabled(true);
                }else {
                    txtWifiReport.append(Html.fromHtml("<h2><font color='red'>No se ha detectado ninguna red,<br>compruebe que hayan redes disponibles e intente de nuevo</font></h2>"));
                    WriteReadFile.writeToFile("- <strong>Wi-Fi:</strong> <font color='#cc0000'>No se detectarón redes inalámbricas</font><br>\n");
                }
                wifi.setWifiEnabled(false);
            }
        }
    };
    public void btnSiguiente(View view) {
        Intent intent = new Intent(this, MainActivity.pruebas[MainActivity.actual]);
        MainActivity.actual++;
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {}
}
