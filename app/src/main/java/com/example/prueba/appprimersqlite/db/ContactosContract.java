package com.example.prueba.appprimersqlite.db;

import android.provider.BaseColumns;

public class ContactosContract {

    public static abstract class ContactoEntry implements BaseColumns {
        public static final String TABLE_NAME = "CONTACTO";
        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "NOMBRE";
        public static final String COLUMN_MAIL = "EMAIL";

    }
}
