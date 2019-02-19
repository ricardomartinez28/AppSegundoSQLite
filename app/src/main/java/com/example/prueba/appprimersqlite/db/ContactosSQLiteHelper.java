package com.example.prueba.appprimersqlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactosSQLiteHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "ContactosDB";
    static final int DATABASE_VERSION = 1;

    static final String CREATE_TABLE_CONTACTOS =
            "CREATE TABLE "+ ContactosContract.ContactoEntry.TABLE_NAME+ "( "+
                    ContactosContract.ContactoEntry.COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                    ContactosContract.ContactoEntry.COLUMN_NAME+" TEXT NOT NULL," +
                    ContactosContract.ContactoEntry.COLUMN_MAIL+" TEXT NOT NULL);";

    public ContactosSQLiteHelper(@androidx.annotation.Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CONTACTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ContactosContract.ContactoEntry.TABLE_NAME);
        onCreate(db);
    }
}
