package upec.projetandroid2017_2018;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class Level extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_level);
    }
    public void selectLevel(View view) {
        VibrationAndClicSound();
        Intent intent = new Intent(this, GameActivity.class);
        switch (view.getId()) {
            case R.id.level1:
                intent.putExtra("level",2);
                break;
            case R.id.level2:
                intent.putExtra("level",3);
                break;
            case R.id.level3:
                intent.putExtra("level",4);
                break;
            case R.id.level4:
                intent.putExtra("level",5);
                break;
            case R.id.level5:
                intent.putExtra("level",6);
                break;
            default:
                intent.putExtra("level",2);
                break;
        }

        startActivity(intent);
    }



    private void Vibration (){
        if(MainActivity.VIBRATION){
            Vibrator vib = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vib.vibrate(50);
        }
    }
    private void clicSound(){
        if (MainActivity.SOND){
            final MediaPlayer clicSound = MediaPlayer.create(this,R.raw.clic);
            clicSound.start();
        }
    }
    public void VibrationAndClicSound(){
        Vibration ();
        clicSound();
    }
}
