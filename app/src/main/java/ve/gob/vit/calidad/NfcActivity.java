package ve.gob.vit.calidad;

import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

@SuppressLint("NewApi")
public class NfcActivity extends AppCompatActivity {
    NfcAdapter adapter;
    PendingIntent pendingIntent;
    IntentFilter writeTagFilters[];
    Tag myTag;
    TextView txtReport;
    String report = "- <strong>NFC:</strong> <font color='#cc0000'>No se detectó lectura</font><br>\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        txtReport = (TextView)findViewById(R.id.txtNfcReport);
        TextView txtTitle = (TextView)findViewById(R.id.txtNfcTitle);
        txtTitle.setText(Html.fromHtml("<h1>Prueba de NFC</h1>"));
        txtReport.setText("Coloque una etiqueta NFC en el lector");

        adapter = NfcAdapter.getDefaultAdapter(this);
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writeTagFilters = new IntentFilter[]{tagDetected};
    }

    @SuppressLint("NewApi") protected void onNewIntent(Intent intent){
        if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
            myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            txtReport.setText(myTag.toString());
            report = "- <strong>NFC:</strong> <font color='#00cc00'>" + myTag.toString() + "</font><br>\n";
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