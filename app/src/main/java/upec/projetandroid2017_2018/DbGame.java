package upec.projetandroid2017_2018;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Button;
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
        String CreatTable = "create table ButtonOrder ( id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " level INTEGER,buttonNB INTEGER,buttontxt INTEGER,empty INTEGER)" ;
        sqLiteDatabase.execSQL(CreatTable);
        String CreatTables = "create table timeLevels ( id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "level INTEGER,timeLevel INTEGER)";
        sqLiteDatabase.execSQL(CreatTables);


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
            contentValues.put("buttonNB",myButtons.get(i).getButton().getId());
            contentValues.put("buttontxt",myButtons.get(i).getButton().getText().toString());
            if (myButtons.get(i).isEempty())
                contentValues.put("empty",1);
            else
                contentValues.put("empty",0);

            db.insert("ButtonOrder",null,contentValues);
        }
    }


    public int test(int Level){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from ButtonOrder WHERE level="+Level,null);
        return res.getCount();
    }

    public ArrayList<MyButton> getData(int Level,Context context){
        ArrayList<MyButton> myButtons = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from ButtonOrder WHERE level="+Level,null);
        res.moveToFirst();
        while (!res.isAfterLast()){
            int txtButton = res.getInt(res.getColumnIndex("buttontxt"));
            int idButton = res.getInt(res.getColumnIndex("buttonNB"));
            int emptyButton = res.getInt(res.getColumnIndex("empty"));
            MyButton myButton = new MyButton(new Button(context),txtButton);
            myButton.getButton().setId(idButton);
            if (emptyButton == 1)
                 myButton.setEempty(true);
            else
                myButton.setEempty(false);
            myButtons.add(myButton);
            res.moveToNext();
        }
        return myButtons;
    }
    public boolean thereIsAData(int Level){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor  ress = db.rawQuery("SELECT * FROM ButtonOrder WHERE level =  "+Level,null);
        return ress.getCount() != 0;
    }

    public void deleteData(int Level){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete("ButtonOrder","level=?",new String[]{Integer.toString(Level)});
    }
    public void deleteTime(int Level){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete("timeLevel","level=?",new String[]{Integer.toString(Level)});
    }
    public void saveTime(int Level,long time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("level",Level);
        contentValues.put("timeLevel",time);
        db.insert("timeLevels",null,contentValues);

    }
    public long getDatas(int Level) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from timeLevels WHERE level=" + Level, null);
        res.moveToFirst();
        return res.getLong(res.getColumnIndex("timeLevel"));
        //return res.getCount();
    }
    public boolean thereistime(int Level){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor  ress = db.rawQuery("SELECT * FROM timeLevels WHERE level =  "+Level,null);
        return ress.getCount() != 0;
    }
}
