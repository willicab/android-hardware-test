package ve.gob.vit.calidad;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

public class ReportActivity extends AppCompatActivity {
    public static String reportType = "calidad";
    public TextView txtReport;
    public String uri = "";
    public static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/calidad_report.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        txtReport = (TextView)findViewById(R.id.txtReport);

        if (reportType == "calidad") {
            uri = Environment.getExternalStorageDirectory().getAbsolutePath() + "/calidad_report.txt";
        } else if (reportType == "stress") {
            uri = Environment.getExternalStorageDirectory().getAbsolutePath() + "/stress_report.txt";
        }

        txtReport.setText(Html.fromHtml(WriteReadFile.readFromFile(uri)));
    }
    public void btnAtras(View view) {
        Intent intent = new Intent(this, FinalActivity.class);
        startActivity(intent);
    }
}
