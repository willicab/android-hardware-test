package ve.gob.vit.calidad;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.IOException;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MicActivity extends AppCompatActivity {

    private MediaRecorder myRecorder;
    private MediaPlayer myPlayer;
    private String outputFile = null;
    private Button startBtn;
    private Button stopBtn;
    private Button playBtn;
    private Button reiniciarBtn;
    private Button siguienteBtn;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mic);

        text = (TextView) findViewById(R.id.text1);
        text.setText(Html.fromHtml("<h1>Prueba de Micrófono</h1>"));

        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audioTest.3gpp";

        startBtn = (Button)findViewById(R.id.start);
        stopBtn = (Button)findViewById(R.id.stop);
        playBtn = (Button)findViewById(R.id.play);
        reiniciarBtn = (Button)findViewById(R.id.reiniciar);
        siguienteBtn = (Button)findViewById(R.id.siguiente);
    }

    public void start(View view){
        text.append(Html.fromHtml("<font color='#00cc00'>Grabando...</font><br>"));
        startBtn.setVisibility(View.GONE);
        stopBtn.setVisibility(View.VISIBLE);
        try {
            myRecorder = new MediaRecorder();
            myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            myRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            myRecorder.setOutputFile(outputFile);
            myRecorder.prepare();
            myRecorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop(View view){
        try {
            stopBtn.setVisibility(View.GONE);
            playBtn.setVisibility(View.VISIBLE);
            text.append(Html.fromHtml("<font color='#cc00cc'>Grabación detenida</font><br>"));

            myRecorder.stop();
            myRecorder.release();
            myRecorder  = null;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void play(View view) {
        try{
            playBtn.setVisibility(View.GONE);
            reiniciarBtn.setVisibility(View.VISIBLE);
            siguienteBtn.setVisibility(View.VISIBLE);
            text.append(Html.fromHtml("<font color='#00cccc'>Reproduciendo</font><br>"));

            myPlayer = new MediaPlayer();
            myPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    myPlayer.stop();
                    myPlayer.release();
                    myPlayer = null;
                    text.append(Html.fromHtml("<font color='#00cc00'>Fin de la reproducción</font><br>"));
                    WriteReadFile.writeToFile("- <strong>Micrófono:</strong> <font color='#00cc00'>No se detectaron problemas</font><br>\n");
                    reiniciarBtn.setEnabled(true);
                    siguienteBtn.setEnabled(true);
                }
            });
            myPlayer.setDataSource(outputFile);
            myPlayer.prepare();
            myPlayer.start();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void reiniciar(View view){
        reiniciarBtn.setVisibility(View.GONE);
        siguienteBtn.setVisibility(View.GONE);
        startBtn.setVisibility(View.VISIBLE);
        reiniciarBtn.setEnabled(false);
        siguienteBtn.setEnabled(false);
        text.setText(Html.fromHtml("<h1>Prueba de Micrófono</h1>"));
    }

    public void btnSiguiente(View view) {
        File file = new File(outputFile);
        file.delete();
        Intent intent = new Intent(this, MainActivity.pruebas[MainActivity.actual]);
        MainActivity.actual++;
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {}
}