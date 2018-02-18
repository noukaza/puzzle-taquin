package upec.projetandroid2017_2018;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    private   Intent intent;
    public static boolean VIBRATION = true , SOND = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

    }

    public void letsGo(View view) {
        VibrationAndClicSound();
        intent = new Intent(MainActivity.this,GameActivity.class);
        startActivity(intent);
    }


    public void changeVibration(View view){
        VIBRATION = !VIBRATION;
        if(VIBRATION)
            view.animate().rotation(0);
        else
            view.animate().rotation(90);
        VibrationAndClicSound();
    }
    public void changeSond(View view){
        SOND = !SOND;
        if(SOND)
            view.animate().rotation(0);
        else
            view.animate().rotation(90);
        VibrationAndClicSound();
    }
    public void help(View view){
        VibrationAndClicSound();
    }
    private void Vibration (){
        if(VIBRATION){
            Vibrator vib = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vib.vibrate(50);
        }
    }
    private void clicSound(){
        if (SOND){
            final MediaPlayer clicSound = MediaPlayer.create(this,R.raw.clic);
            clicSound.start();
        }
    }
    public void VibrationAndClicSound(){
        Vibration ();
        clicSound();
    }


}
