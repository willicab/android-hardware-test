package ve.gob.vit.calidad;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GsmActivity extends AppCompatActivity {
    LinearLayout layout;
    TextView textView7;
    TextView txtReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gsm);
        Context context = this;
        layout = (LinearLayout) findViewById(R.id.MyLinearLayout);

        TextView txtTitle = (TextView)findViewById(R.id.txtGsmTitle);
        txtTitle.setText(Html.fromHtml("<h1>Prueba de GSM</h1>"));

        txtReport = (TextView)findViewById(R.id.txtGsmReport);
        txtReport.setText("");
        TelephonyInfo telephonyInfo = TelephonyInfo.getInstance(context);

        String imeiSIM1 = telephonyInfo.getImeiSIM1();
        String imeiSIM2 = telephonyInfo.getImeiSIM2();
        boolean isSIM1Ready = telephonyInfo.isSIM1Ready();
        boolean isSIM2Ready = telephonyInfo.isSIM2Ready();
        boolean isDualSIM = telephonyInfo.isDualSIM();
        if (isDualSIM == false) {
            txtReport.append(Html.fromHtml("- <b>IMEI:</b> " + imeiSIM1 + "<br>"));
            txtReport.append(Html.fromHtml("- <b>Está activa la tarjeta SIM: " + (isSIM1Ready == true ? "<font color='#00cc00'>Si</font></b><br>" : "<font color='#cc0000'>No</font></b><br>")));
            WriteReadFile.writeToFile("- <strong>GSM:</strong> <font color='#00cc00'>Se detectó un puerto SIM</font><br>\n");
        } else {
            txtReport.append(Html.fromHtml("- <b>IMEI 1:</b> " + imeiSIM1 + "<br>"));
            txtReport.append(Html.fromHtml("- <b>Está activa la tarjeta SIM 1: " + (isSIM1Ready == true ? "<font color='#00cc00'>Si</font></b><br>" : "<font color='#cc000'>No</font></b><br>")));
            txtReport.append(Html.fromHtml("- <b>IMEI 2:</b> " + imeiSIM2 + "<br>"));
            txtReport.append(Html.fromHtml("- <b>Está activa la tarjeta SIM 2: " + (isSIM2Ready == true ? "<font color='#00cc00'>Si</font></b><br>" : "<font color='#cc0000'>No</font></b><br>")));
            WriteReadFile.writeToFile("- <strong>GSM:</strong> <font color='#00cc00'>Se detectaron dos puertos SIM</font><br>\n");
        }

        if (isSIM1Ready == true) {
            TelephonyManager telephonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String SimNumber = telephonyManager.getSimSerialNumber();
            String NetworkName = telephonyManager.getNetworkOperatorName();
            txtReport.append(Html.fromHtml("- <b>Numero de serial:</b> " + SimNumber + "<br>"));
            txtReport.append(Html.fromHtml("- <b>Operador:</b> " + NetworkName + "<br>"));
        }

    }
    public void btnSiguiente(View view) {
        //Intent intent = new Intent(this, WifiActivity.class);
        Intent intent = new Intent(this, MainActivity.pruebas[MainActivity.actual]);
        MainActivity.actual++;
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {}
}