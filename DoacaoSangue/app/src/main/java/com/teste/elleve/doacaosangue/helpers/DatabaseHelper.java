package com.teste.elleve.doacaosangue.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.teste.elleve.doacaosangue.model.Request;
import com.teste.elleve.doacaosangue.model.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper _instance = null;
    public static final String DB_NAME = "doacao.sqlite";
    private static final int DB_VERSION = 1;
    private Context context;

    public static DatabaseHelper getInstance(Context context) {
        if (_instance == null) {
            _instance = new DatabaseHelper(context.getApplicationContext());
        }
        return _instance;
    }

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS users (" +
                "email TEXT PRIMARY KEY, " +
                "password TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS  requests (" +
                "id INTEGER PRIMARY KEY, " +
                "hospital TEXT, " +
                "patient TEXT, " +
                "blood_type TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean createUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("email", user.getEmail());
            values.put("password", user.getPassword());
            Cursor cursor = db.query("users", null, "email=?", new String[]{user.getEmail()}, null, null, null);
            if (!cursor.moveToFirst()) {
                db.insert("users", "", values);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return false;
    }

    public boolean auth(String email, String password) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("email", email);
            values.put("password", password);
            Cursor cursor = db.query("users", null, "email=? AND password=?", new String[]{email, password}, null, null, null);
            if (cursor.moveToFirst()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return false;
    }

    public boolean createRequest(Request request) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("hospital", request.getHospital());
            values.put("patient", request.getPatient());
            values.put("blood_type", request.getBloodType());
            long id = db.insert("requests", "", values);
            request.setId((int) id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return false;
    }

    public ArrayList<Request> getRequests() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Request> requests = new ArrayList();
        try {
            Cursor cursor = db.query("requests", null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                Request request = new Request(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                requests.add(request);
            }
        } catch (Exception e) {

        } finally {
            db.close();
        }
        return requests;
    }
}