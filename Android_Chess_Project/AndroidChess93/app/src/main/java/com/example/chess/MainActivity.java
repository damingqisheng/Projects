package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;

import chess.Chess;

public class MainActivity extends AppCompatActivity {

    private ListView playbackListView;
    private PlaybackAdapter playbackAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playButton = findViewById(R.id.btn_play);
        playButton.setOnClickListener((e )-> {
            startActivity(new Intent(this, ChessActivity.class));
        });

        Button replayButton = findViewById(R.id.btn_replay);
        replayButton.setOnClickListener((e) -> {
            Chess.init();
            startActivity(new Intent(this, ChessActivity.class));
        });

        playbackListView = findViewById(R.id.list_playback);
        playbackAdapter = new PlaybackAdapter(this, Storage.getRecords(this));
        playbackListView.setAdapter(playbackAdapter);
        playbackListView.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onPlaybackClick((Record) ((PlaybackAdapter)parent.getAdapter()).getItem(position));
                }
            }
        );

        ((RadioGroup) findViewById(R.id.rdg_sort)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                playbackAdapter.setOrderBy(checkedId == R.id.radio_date ? "date" : "title");
                playbackAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        playbackAdapter.setRecords(Storage.getRecords(this));
        playbackAdapter.notifyDataSetChanged();
    }

    private void onPlaybackClick(Record record) {
        Intent intent = new Intent(this, PlaybackActivity.class);
        intent.putExtra("record", record);
        startActivity(intent);
    }
}