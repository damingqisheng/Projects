package com.example.chess;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class PlaybackAdapter extends BaseAdapter {
    private List<Record> records;
    private LayoutInflater inflater;

    public PlaybackAdapter(Context context, List<Record> records) {
        this.records = records;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return records.size();
    }

    @Override
    public Object getItem(int position) {
        return records.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder")
        View view = inflater.inflate(R.layout.playback_list_item, null);
        ((TextView)view.findViewById(R.id.text_title)).setText(records.get(position).getName());
        ((TextView)view.findViewById(R.id.text_date)).setText(records.get(position).getDate().toLocaleString());
        return view;
    }

    public void setOrderBy(String by) {
        switch (by) {
            case "title":
                this.records.sort((x, y) -> x.getName().compareTo(y.getName()));
                break;
            case "date":
                this.records.sort((x, y) -> y.getDate().compareTo(x.getDate()));
                break;
        }
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }
}
