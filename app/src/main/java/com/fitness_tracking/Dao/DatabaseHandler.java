package com.fitness_tracking.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_USER =
            "CREATE TABLE IF NOT EXISTS USER (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, " +
                    "email TEXT, " +
                    "password TEXT, " +
                    "weight DOUBLE, " +
                    "height DOUBLE, " +
                    "sex TEXT" +
                    ");";

    private static final String CREATE_TABLE_EXERCISE =
            "CREATE TABLE IF NOT EXISTS EXERCISE (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, " +
                    "path TEXT, " +
                    "description TEXT, " +
                    "id_user INTEGER, " +
                    "FOREIGN KEY (id_user) REFERENCES USER(id)" +
                    ");";

    private static final String CREATE_TABLE_PRODUIT =
            "CREATE TABLE IF NOT EXISTS PRODUIT (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, " +
                    "calorie DOUBLE, " +
                    "proteine DOUBLE, " +
                    "carbe DOUBLE, " +
                    "fate DOUBLE, " +
                    "id_user INTEGER, " +
                    "FOREIGN KEY (id_user) REFERENCES USER(id)" +
                    ");";

    private static final String CREATE_TABLE_REPAT =
            "CREATE TABLE IF NOT EXISTS REPAT (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "id_produit INTEGER, " +
                    "date TEXT, " +
                    "id_user INTEGER, " +
                    "FOREIGN KEY (id_produit) REFERENCES PRODUIT(id), " +
                    "FOREIGN KEY (id_user) REFERENCES USER(id)" +
                    ");";

    private static final String CREATE_TABLE_WORKOUT =
            "CREATE TABLE IF NOT EXISTS WORKOUT (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "id_exercice INTEGER, " +
                    "weight DOUBLE, " +
                    "serie INTEGER, " +
                    "repetition INTEGER, " +
                    "date TEXT, " +
                    "id_user INTEGER, " +
                    "FOREIGN KEY (id_exercice) REFERENCES EXERCICE(id), " +
                    "FOREIGN KEY (id_user) REFERENCES USER(id)" +
                    ");";

    public DatabaseHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {

    }
}
