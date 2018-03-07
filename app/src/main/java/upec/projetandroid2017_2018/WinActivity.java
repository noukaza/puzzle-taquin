package upec.projetandroid2017_2018;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WinActivity extends AppCompatActivity {
    Intent intent,getIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        getIntent = getIntent();
    }

    public void home(View view) {
        intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void next(View view) {
        intent = new Intent(this,GameActivity.class);
        switch (getIntent.getIntExtra("level",2)) {
            case 2:
                intent.putExtra("level",3);
                break;
            case 3:
                intent.putExtra("level",4);
                break;
            case 5:
                intent.putExtra("level",5);
                break;
            case 4:
                intent.putExtra("level",6);
                break;
            default:
                intent.putExtra("level",2);
                break;
        }
        startActivity(intent);

    }
}