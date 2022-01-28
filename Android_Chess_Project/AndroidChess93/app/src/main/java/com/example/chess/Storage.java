package com.example.chess;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import chess.action.Action;

public class Storage {
    private static List<Record> records = new ArrayList<>();

    public static void save(Context context, Record record) {
        records.add(record);

        SharedPreferences preferences = context.getSharedPreferences("records", Activity.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        ByteArrayOutputStream outPutStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream;
        try {
            objectOutputStream = new ObjectOutputStream(outPutStream);
            objectOutputStream.writeObject(records);
            byte[] encodeBase64 = Base64.encode(outPutStream.toByteArray(), 0);
            String objString = new String(encodeBase64);
            edit.putString("records", objString);
            edit.apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Record> getRecords(Context context) {
        String s = context.getSharedPreferences("records", Activity.MODE_PRIVATE)
                          .getString("records", null);
        if (s == null) return new ArrayList<>();

        byte[] a = Base64.decode(s, 0);
        ByteArrayInputStream input = new ByteArrayInputStream(a);

        Object object = null;
        try {
            ObjectInputStream inputStream = new ObjectInputStream(input);
            object = inputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        records = (List<Record>) object;
        return records;
    }
}
