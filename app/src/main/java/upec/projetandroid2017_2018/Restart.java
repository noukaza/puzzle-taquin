package upec.projetandroid2017_2018;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class Restart extends AppCompatActivity {
    public DbGame dbGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restart);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dbGame = new DbGame(this);
    }

    public void newGame(View view) {
        Intent intents = getIntent();
        dbGame.deleteData(intents.getIntExtra("level",2));
        Intent intent = new Intent(this,GameActivity.class);
        intent.putExtra("level",intents.getIntExtra("level",2));
        startActivity(intent);

    }


    public void restart(View view) {
        Intent intents = getIntent();
        Intent intent = new Intent(this,GameActivity.class);
        intent.putExtra("level",intents.getIntExtra("level",2));
        startActivity(intent);
    }
}
