package com.salazarisaiahnoel.shoppinglist.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.salazarisaiahnoel.shoppinglist.others.StringTriple;
import com.salazarisaiahnoel.shoppinglist.others.StringTripleComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShoppingListData extends SQLiteOpenHelper {

    private final static String database = "shoplist";

    public ShoppingListData(Context context){
        super(context, database, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String q = "CREATE TABLE shoppinglist(name text, number text, price text)";
        db.execSQL(q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String q = "DROP TABLE IF EXISTS shoppinglist";
        db.execSQL(q);
        onCreate(db);
    }

    public void addData(String name, String number, String price){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("number", number);
        cv.put("price", price);

        db.insert("shoppinglist", null, cv);
    }

    public List<String> getName(){
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT * FROM shoppinglist";
        Cursor c = db.rawQuery(q, null);
        while (c.moveToNext()){
            list.add(c.getString(0));
        }
        c.close();
        return list;
    }
    public List<String> getNumber(){
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT * FROM shoppinglist";
        Cursor c = db.rawQuery(q, null);
        while (c.moveToNext()){
            list.add(c.getString(1));
        }
        c.close();
        return list;
    }
    public List<String> getPrice(){
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT * FROM shoppinglist";
        Cursor c = db.rawQuery(q, null);
        while (c.moveToNext()){
            list.add(c.getString(2));
        }
        c.close();
        return list;
    }

    public void clearData(){
        SQLiteDatabase db = getReadableDatabase();
        String q = "DROP TABLE IF EXISTS shoppinglist";
        db.execSQL(q);
        String q1 = "CREATE TABLE shoppinglist(name text, number text, price text)";
        db.execSQL(q1);
    }

    public void deleteData(int position){
        SQLiteDatabase db = getReadableDatabase();
        List<String> name = getName();
        List<String> number = getNumber();
        List<String> price = getPrice();
        name.remove(position);
        number.remove(position);
        price.remove(position);
        ArrayList<StringTriple> list = new ArrayList<>();
        for (int a = 0; a < name.size(); a++){
            list.add(new StringTriple(name.get(a), number.get(a), price.get(a)));
        }
        String q = "DROP TABLE IF EXISTS shoppinglist";
        db.execSQL(q);
        String q1 = "CREATE TABLE shoppinglist(name text, number text, price text)";
        db.execSQL(q1);
        Collections.sort(list, new StringTripleComparator());
        for (int a = 0; a < list.size(); a++){
            String[] a1 = list.get(a).toString().split(", ");
            addData(a1[0].substring(1), a1[1], a1[2].substring(0, a1[2].length() - 1));
        }
    }

    public void rearrangePosition(){
        SQLiteDatabase db = getReadableDatabase();
        List<String> name = getName();
        List<String> number = getNumber();
        List<String> price = getPrice();
        ArrayList<StringTriple> list = new ArrayList<>();
        for (int a = 0; a < name.size(); a++){
            list.add(new StringTriple(name.get(a), number.get(a), price.get(a)));
        }
        String q = "DROP TABLE IF EXISTS shoppinglist";
        db.execSQL(q);
        String q1 = "CREATE TABLE shoppinglist(name text, number text, price text)";
        db.execSQL(q1);
        Collections.sort(list, new StringTripleComparator());
        for (int a = 0; a < list.size(); a++){
            String[] a1 = list.get(a).toString().split(", ");
            addData(a1[0].substring(1), a1[1], a1[2].substring(0, a1[2].length() - 1));
        }
    }
}
