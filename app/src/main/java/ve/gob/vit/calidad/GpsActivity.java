package ve.gob.vit.calidad;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class GpsActivity extends AppCompatActivity {
    public static TextView txtReport;
    public static LocationManager locationManager;
    public static String report = "- <strong>GPS:</strong> <font color='#cc0000'>No se determinó ubicación</font><br>\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        TextView txtTitle = (TextView)findViewById(R.id.txtGpsTitle);
        txtTitle.setText(Html.fromHtml("<h1>Prueba de GPS</h1>"));
        txtReport = (TextView)findViewById(R.id.txtGpsReport);

        turnGPSOn();

        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new MyLocationListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, locationListener);
    }
    public void btnSiguiente(View view) {
        WriteReadFile.writeToFile(report);
        Intent intent = new Intent(this, MainActivity.pruebas[MainActivity.actual]);
        MainActivity.actual++;
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {}

    public void turnGPSOn(){
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(!provider.contains("gps")){ //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }

    public void turnGPSOff(){
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(provider.contains("gps")){ //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }

}

class MyLocationListener implements LocationListener {

    @Override
    public void onLocationChanged(Location loc) {
        double lat = loc.getLatitude();
        double lon = loc.getLongitude();
        GpsActivity.txtReport.setText("Latitud: " + lat + "\nLongitud: " + lon);
        GpsActivity.report = "- <strong>GPS:</strong> <font color='#00cc00'>Latitud: " + lat + "\nLongitud: " + lon + "</font><br>\n";
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}