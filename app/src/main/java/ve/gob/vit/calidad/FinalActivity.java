package ve.gob.vit.calidad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FinalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
    }
    public void btnTestReport(View view) {
        ReportActivity.reportType = "calidad";
        Intent intent = new Intent(this, ReportActivity.class);
        startActivity(intent);
        //Intent intent = new Intent(Intent.ACTION_DELETE);
        //intent.setData(Uri.parse("package:ve.gob.vit.calidad"));
        //startActivity(intent);
    }
    public void btnFinish(View view) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {}
}
