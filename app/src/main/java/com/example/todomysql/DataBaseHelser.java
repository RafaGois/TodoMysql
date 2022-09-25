package com.example.todomysql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataBaseHelser extends SQLiteOpenHelper {

    private String nomeTabela = "tbl_Tasks";

    public DataBaseHelser(@Nullable Context context) {
        super(context,"dataBase_name",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE "+ nomeTabela
                + "(id INTEGER primary key autoincrement, text TEXT, date TEXT)";

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query = "DROP TABLE IF EXISTS "+ nomeTabela;

        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }

    public void insert (String text, String date) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("text",text);
        contentValues.put("date",date);

        sqLiteDatabase.insertWithOnConflict(nomeTabela,null,contentValues, SQLiteDatabase.CONFLICT_REPLACE);

        sqLiteDatabase.close();
    }

    public void update (String id, String text) {
        SQLiteDatabase database = getWritableDatabase();

        String query = "UPDATE "+ nomeTabela+ " SET text = '"+ text + "' WHERE id = '" + id+"'";

        database.execSQL(query);
        database.close();
    }

    public void delete (String id) {
        SQLiteDatabase database = getWritableDatabase();

        String query = "DELETE FROM " + nomeTabela+" WHERE id = '"+id+"'";

        database.execSQL(query);

        database.close();
    }

    public  void truncate () {
        SQLiteDatabase database = getWritableDatabase();

        String query = "DELETE FROM '" + nomeTabela + "'";

        String query2 = "DELETE FROM sqlite_sequence where name = '" + nomeTabela + "'";

        database.execSQL(query);
        database.execSQL(query2);

        database.close();

    }

    public JSONArray getArray () {
        SQLiteDatabase database = getWritableDatabase();

        JSONArray array = new JSONArray();

        String sQuery = "SELECT * FROM "+ nomeTabela;

        Cursor cursor = database.rawQuery(sQuery, null);

        if (cursor.moveToFirst()) {
            do {
                try {
                    JSONObject object = new JSONObject();
                    object.put("id",cursor.getString(0));
                    object.put("text",cursor.getString(1));
                    object.put("date",cursor.getString(2));

                    array.put(object);
                } catch (JSONException e) {
                    System.out.println(e.getMessage());
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        return array;
    }
}
