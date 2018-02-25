package upec.projetandroid2017_2018;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private   Intent intent;
    public static boolean VIBRATION = true , SOND = true;
    static ImageButton vibrationButton , soundButton ,helpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        vibrationButton = (ImageButton) findViewById(R.id.vibButton);
        soundButton = (ImageButton) findViewById(R.id.soundButton);
        helpButton = (ImageButton) findViewById(R.id.helpButton);
    }

    public void letsGo(View view) {
        VibrationAndClicSound();
        intent = new Intent(MainActivity.this,Level.class);
        startActivity(intent);
    }


    public  void changeVibration(View view){
        VIBRATION = !VIBRATION;
        if(VIBRATION)
            vibrationButton.setImageResource(R.mipmap.ic_vibration_white_24dp);
        else
            vibrationButton.setImageResource(R.mipmap.ic_phonelink_erase_white_24dp );
        VibrationAndClicSound();
    }
    public void changeSond(View view){
        SOND = !SOND;
        if(SOND)
            soundButton.setImageResource(R.mipmap.ic_queue_music_white_24dp);
        else
            soundButton.setImageResource(R.mipmap.ic_volume_mute_white_24dp);
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
