package upec.projetandroid2017_2018;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private   Intent intent;
    public static boolean VIBRATION = true , SOND = true;
    static ImageButton vibrationButton , soundButton ,helpButton;
    DbGame dbGame;
    LinearLayout starLayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        vibrationButton = findViewById(R.id.vibButton);
        soundButton = findViewById(R.id.soundButton);
        helpButton = findViewById(R.id.helpButton);
        dbGame = new DbGame(this);
        starLayout = findViewById(R.id.starLayout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        starLayout.removeAllViews();
        for (int i=0 ;i<4;++i){
            ImageView imageView = new ImageButton(this);
            if (i< dbGame.winLevel()){
                imageView.setImageResource(R.mipmap.ic_star_white_24dp);
            }else{
                imageView.setImageResource(R.mipmap.ic_star_border_white_24dp);
            }
            imageView.setBackground(null);
            starLayout.addView(imageView);
        }
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
