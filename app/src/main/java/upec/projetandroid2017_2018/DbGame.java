package upec.projetandroid2017_2018;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by NouakazaPc on 03/03/2018.
 */

public class DbGame extends SQLiteOpenHelper {
    public static final String DBname = "dataGame.db";

    public DbGame(Context context) {
        super(context, DBname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       // sqLiteDatabase.execSQL("create table timeLevel ( id INTEGER PRIMARY KEY AUTOINCREMENT, time INTEGER )");
        //sqLiteDatabase.execSQL("create table ButtonOrder ( id INTEGER PRIMARY KEY AUTOINCREMENT, level INTEGER , buttonNB INTEGER , buttonID INTEGER, myButtonID INTEGER)");
        sqLiteDatabase.execSQL("create table ButtonOrder ( id INTEGER PRIMARY KEY AUTOINCREMENT, level INTEGER , buttonNB INTEGER )");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS ButtonOrder");
        onCreate(sqLiteDatabase);
    }
    public void insertData(ArrayList<MyButton> myButtons,int Level){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        for (int i=0;i<myButtons.size();i++){
            contentValues.put("level",Level);
            contentValues.put("buttonNB",myButtons.get(i).getID());

            db.insert("ButtonOrder",null,contentValues);
        }
    }

    public ArrayList getALLButton(){
        ArrayList<Integer> test = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from ButtonOrder",null);
        while (res.isAfterLast()==false ){
            test.add(1);
            res.moveToNext();
        }
        return test;
    }
    public boolean thereIsAData(int Level){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor  ress = db.rawQuery("SELECT * FROM ButtonOrder WHERE level =  "+Level,null);
        if (ress.getCount()==0) return false;
        return true;
    }
    public void deleteData(int Level){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete("ButtonOrder","level=?",new String[]{Integer.toString(Level)});
    }
}
