package com.fitness_tracking.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.fitness_tracking.entities.Exercice;
import com.fitness_tracking.entities.Produit;
import com.fitness_tracking.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Boolean checkEmail(String email) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM user WHERE email = ?", new String[]{email});
        return cursor.getCount() > 0;
    }

    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM users WHERE email = ? AND password = ?", new String[]{email, password});
        return cursor.getCount() > 0;
    }

    private ContentValues getUserContentValues(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", user.getEmail());
        contentValues.put("name", user.getName());
        contentValues.put("password", user.getPassword());
        contentValues.put("weight", user.getWeight());
        contentValues.put("height", user.getHeight());
        contentValues.put("sex", user.getSex());
        return contentValues;
    }

    public Optional<User> getUserByEmailAndPassword(String email, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = null;

        try {
            cursor = sqLiteDatabase.rawQuery("select * from user where email = ? and password = ?", new String[]{email, password});

            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex("id");
                int nameIndex = cursor.getColumnIndex("name");
                int emailIndex = cursor.getColumnIndex("email");
                int passwordIndex = cursor.getColumnIndex("password");
                int weightIndex = cursor.getColumnIndex("weight");
                int heightIndex = cursor.getColumnIndex("height");
                int sexIndex = cursor.getColumnIndex("sex");

                if (nameIndex != -1 && weightIndex != -1 && passwordIndex != -1 && emailIndex != -1 && heightIndex != -1 && sexIndex!= -1) {
                    Long userId = cursor.getLong(idIndex);
                    User user = new User(
                            userId,
                            cursor.getString(emailIndex),
                            cursor.getString(nameIndex),
                            cursor.getString(passwordIndex),
                            cursor.getDouble(weightIndex),
                            cursor.getDouble(heightIndex),
                            cursor.getString(sexIndex)
                    );
                    return Optional.of(user);
                } else {
                    Log.e("DatabaseHandler", "One or more columns not found in the result set");
                    return Optional.empty();
                }
            } else {
                Log.d("DatabaseHandler", "No user found with the given email and password");
                return Optional.empty();
            }
        } catch (Exception e) {
            Log.e("DatabaseHandler", "Error while retrieving user", e);
            return Optional.empty();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            sqLiteDatabase.close();
        }
    }

    public boolean saveUser(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = getUserContentValues(user);

        try {
            long result = sqLiteDatabase.insert("user", null, contentValues);

            if (result != -1) {
                Log.d("DatabaseHandler", "User saved successfully");
                return true;
            } else {
                Log.e("DatabaseHandler", "Error while saving user: " + result);
                return false;
            }
        } catch (Exception e) {
            Log.e("DatabaseHandler", "Error while saving user", e);
            return false;
        } finally {
            sqLiteDatabase.close();
        }
    }

    public void updateUser(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = getUserContentValues(user);

        try {
            int rowsAffected = sqLiteDatabase.update("USER", contentValues, "id = ?", new String[]{String.valueOf(user.getId())});

            if (rowsAffected > 0) {
                Log.d("DatabaseHandler", "User updated successfully");
            } else {
                Log.d("DatabaseHandler", "Failed to update user");
            }
        } catch (Exception e) {
            Log.e("DatabaseHandler", "Error while updating user", e);
        } finally {
            sqLiteDatabase.close();
        }
    }

    public void updateExercice(Exercice exercice) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", exercice.getName());
        contentValues.put("path", exercice.getPath());
        contentValues.put("description", exercice.getDescription());
        contentValues.put("id_user", exercice.getIdUser());

        sqLiteDatabase.update("EXERCICE", contentValues, "id = ?", new String[]{String.valueOf(exercice.getId())});

        sqLiteDatabase.close();
    }

    public long addExercice(Exercice exercice) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", exercice.getName());
        contentValues.put("path", exercice.getPath());
        contentValues.put("description", exercice.getDescription());
        contentValues.put("id_user", exercice.getIdUser());

        long id = sqLiteDatabase.insert("EXERCICE", null, contentValues);

        sqLiteDatabase.close();
        return id;
    }

    public void deleteExercice(long id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.delete("EXERCICE", "id = ?", new String[]{String.valueOf(id)});

        sqLiteDatabase.close();
    }

    @SuppressLint("Range")
    public List<Exercice> getAllExercices() {
        List<Exercice> exercicesList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query("EXERCICE", null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Exercice exercice = new Exercice(
                        cursor.getLong(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getString(cursor.getColumnIndex("path")),
                        cursor.getString(cursor.getColumnIndex("description")),
                        cursor.getLong(cursor.getColumnIndex("id_user"))
                );
                exercicesList.add(exercice);
            } while (cursor.moveToNext());

            cursor.close();
        }

        sqLiteDatabase.close();
        return exercicesList;
    }

    public long addProduit(Produit produit) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", produit.getName());
        contentValues.put("calorie", produit.getCalorie());
        contentValues.put("proteine", produit.getProteine());
        contentValues.put("carbe", produit.getCarbe());
        contentValues.put("fate", produit.getFate());
        contentValues.put("id_user", produit.getIdUser());

        long id = sqLiteDatabase.insert("PRODUIT", null, contentValues);

        sqLiteDatabase.close();
        return id;
    }

    public void updateProduit(Produit produit) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", produit.getName());
        contentValues.put("calorie", produit.getCalorie());
        contentValues.put("proteine", produit.getProteine());
        contentValues.put("carbe", produit.getCarbe());
        contentValues.put("fate", produit.getFate());
        contentValues.put("id_user", produit.getIdUser());

        sqLiteDatabase.update("PRODUIT", contentValues, "id = ?", new String[]{String.valueOf(produit.getId())});

        sqLiteDatabase.close();
    }

    public void deleteProduit(long id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        sqLiteDatabase.delete("PRODUIT", "id = ?", new String[]{String.valueOf(id)});

        sqLiteDatabase.close();
    }

    @SuppressLint("Range")
    public List<Produit> getAllProduitsForUser(long userId) {
        List<Produit> produitsList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query("PRODUIT", null, "id_user = ?", new String[]{String.valueOf(userId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Produit produit = new Produit(
                        cursor.getLong(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getDouble(cursor.getColumnIndex("calorie")),
                        cursor.getDouble(cursor.getColumnIndex("proteine")),
                        cursor.getDouble(cursor.getColumnIndex("carbe")),
                        cursor.getDouble(cursor.getColumnIndex("fate")),
                        cursor.getLong(cursor.getColumnIndex("id_user"))
                );
                produitsList.add(produit);
            } while (cursor.moveToNext());

            cursor.close();
        }

        sqLiteDatabase.close();
        return produitsList;
    }

}
