package com.example.prueba.appprimersqlite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.prueba.appprimersqlite.model.Contacto;

import java.util.ArrayList;

public class ContactosDatasource {

    private ContactosSQLiteHelper csh;
    private Context contexto;

    public ContactosDatasource(Context contexto) {
        this.contexto = contexto;
        csh = new ContactosSQLiteHelper(contexto);
    }

    public SQLiteDatabase openReadable() {
        return csh.getReadableDatabase();
    }
    public SQLiteDatabase openWriteable() {
        return csh.getWritableDatabase();
    }
    public void close(SQLiteDatabase database) {
        database.close();
    }


    public Contacto consultarContacto(int idContacto) {

    SQLiteDatabase sdb=openReadable();

    String select="SELECT "+ ContactosContract.ContactoEntry.COLUMN_ID+
            ", "+ContactosContract.ContactoEntry.COLUMN_NAME+
            ", "+ContactosContract.ContactoEntry.COLUMN_MAIL+
            " FROM "+ContactosContract.ContactoEntry.TABLE_NAME+
            " WHERE "+ContactosContract.ContactoEntry.COLUMN_ID+" = ?";

    String [] args={String.valueOf(idContacto)};

        Cursor cursor=sdb.rawQuery(select,args);

        Contacto contacto=null;
        int id;
        String nombre;
        String email;

        if(cursor.moveToFirst()){
            id=cursor.getInt(cursor.getColumnIndex(ContactosContract.ContactoEntry.COLUMN_ID));
            nombre=cursor.getString(cursor.getColumnIndex(ContactosContract.ContactoEntry.COLUMN_NAME));
            email=cursor.getString(cursor.getColumnIndex(ContactosContract.ContactoEntry.COLUMN_MAIL));
            contacto= new Contacto(id,nombre,email);
        }
    cursor.close();
    sdb.close();
    return contacto;

    }


    public ArrayList<Contacto> consultarContactos() {

        ArrayList<Contacto> listaContacto= new ArrayList<Contacto>();
        SQLiteDatabase sdb= openReadable();

        String[] columnas={ContactosContract.ContactoEntry.COLUMN_ID,
                ContactosContract.ContactoEntry.COLUMN_NAME,
                ContactosContract.ContactoEntry.COLUMN_MAIL};

        Cursor cursor= sdb.query(ContactosContract.ContactoEntry.TABLE_NAME,columnas,null,null,null,null, ContactosContract.ContactoEntry.COLUMN_NAME+" DESC");

        Contacto contacto=null;
        int id;
        String nombre;
        String email;
        if(cursor.moveToFirst()){
            do{
                id=cursor.getInt(cursor.getColumnIndex(ContactosContract.ContactoEntry.COLUMN_ID));
                nombre=cursor.getString(cursor.getColumnIndex(ContactosContract.ContactoEntry.COLUMN_NAME));
                email=cursor.getString(cursor.getColumnIndex(ContactosContract.ContactoEntry.COLUMN_MAIL));
                contacto= new Contacto(id,nombre,email);
                listaContacto.add(contacto);
            }while(cursor.moveToNext());
        }
        cursor.close();
        sdb.close();
        return listaContacto;
    }

    public long insertarContacto(Contacto contacto) {
        SQLiteDatabase sdb = openWriteable();

        sdb.beginTransaction();

        ContentValues cv = new ContentValues();
        cv.put(ContactosContract.ContactoEntry.COLUMN_NAME, contacto.getName());
        cv.put(ContactosContract.ContactoEntry.COLUMN_MAIL, contacto.getEmail());

        long id = sdb.insert(ContactosContract.ContactoEntry.TABLE_NAME, null, cv);

        if (id != -1) {
            sdb.setTransactionSuccessful();
        }
        sdb.endTransaction();
        close(sdb);

        return id;
    }

    public void modificarContacto(Contacto contacto) {
        SQLiteDatabase database = openWriteable();
        database.beginTransaction();

        ContentValues contactoValues = new ContentValues();
        contactoValues.put(ContactosContract.ContactoEntry.COLUMN_NAME,
                contacto.getName());
        contactoValues.put(ContactosContract.ContactoEntry.COLUMN_MAIL,
                contacto.getEmail());

        String clausulaWhere = ContactosContract.ContactoEntry.COLUMN_ID + " = ?";
        String[] argumentos = {String.valueOf(contacto.getId())};

        database.update(ContactosContract.ContactoEntry.TABLE_NAME,
                contactoValues,
                clausulaWhere,
                argumentos);

        /*database.update(ContactosContract.ContactoEntry.TABLE_NAME,
                contactoValues,
                String.format("%s=%d",ContactosContract.ContactoEntry.COLUMN_ID,
                        contacto.getId()),
                null);*/

        database.setTransactionSuccessful();
        database.endTransaction();
        close(database);

    }

    public void borrarContacto(int idContacto) {

        SQLiteDatabase sdb= openWriteable();
        sdb.beginTransaction();

        String clausulaWhere= ContactosContract.ContactoEntry.COLUMN_ID+" = "+idContacto;

        sdb.delete(ContactosContract.ContactoEntry.TABLE_NAME,clausulaWhere,null);

        sdb.setTransactionSuccessful();
        sdb.endTransaction();
        close(sdb);



    }


}
