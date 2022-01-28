package com.example.chess;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import chess.Chess;
import chess.action.Action;
import chess.action.MoveAction;
import chess.action.UndoAction;
import chess.exceptions.Checkmate;
import chess.exceptions.ChessException;
import chess.exceptions.RequirePromotion;


public class PlaybackActivity extends AppCompatActivity {
    private ImageButton[][] keyboardControls = new ImageButton[8][8];
    private int[][] keyboardControlIds = new int[][]{
            {R.id.a8, R.id.b8, R.id.c8, R.id.d8, R.id.e8, R.id.f8, R.id.g8, R.id.h8},
            {R.id.a7, R.id.b7, R.id.c7, R.id.d7, R.id.e7, R.id.f7, R.id.g7, R.id.h7},
            {R.id.a6, R.id.b6, R.id.c6, R.id.d6, R.id.e6, R.id.f6, R.id.g6, R.id.h6},
            {R.id.a5, R.id.b5, R.id.c5, R.id.d5, R.id.e5, R.id.f5, R.id.g5, R.id.h5},
            {R.id.a4, R.id.b4, R.id.c4, R.id.d4, R.id.e4, R.id.f4, R.id.g4, R.id.h4},
            {R.id.a3, R.id.b3, R.id.c3, R.id.d3, R.id.e3, R.id.f3, R.id.g3, R.id.h3},
            {R.id.a2, R.id.b2, R.id.c2, R.id.d2, R.id.e2, R.id.f2, R.id.g2, R.id.h2},
            {R.id.a1, R.id.b1, R.id.c1, R.id.d1, R.id.e1, R.id.f1, R.id.g1, R.id.h1},
    };

    private Map<String, Drawable> iconMap = new HashMap<>();
    private Queue<Action> actionQueue;
    private Record record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback);
        Chess.init();

        record = (Record)getIntent().getSerializableExtra("record");
        actionQueue = new LinkedBlockingQueue<>();
        actionQueue.addAll(record.getActions());

        String charSeq = "abcdefgh";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                keyboardControls[j][i] = findViewById(keyboardControlIds[j][i]);
                keyboardControls[j][i].setTag(String.format("%s%d", charSeq.charAt(i), 8 - j));
            }
        }

        iconMap.put("bk", ResourcesCompat.getDrawable(getResources(), R.drawable.black_kind, null));
        iconMap.put("bq", ResourcesCompat.getDrawable(getResources(), R.drawable.black_queue, null));
        iconMap.put("br", ResourcesCompat.getDrawable(getResources(), R.drawable.black_rook, null));
        iconMap.put("bb", ResourcesCompat.getDrawable(getResources(), R.drawable.black_bishop, null));
        iconMap.put("bn", ResourcesCompat.getDrawable(getResources(), R.drawable.black_knight, null));
        iconMap.put("bp", ResourcesCompat.getDrawable(getResources(), R.drawable.black_pawn, null));
        iconMap.put("wk", ResourcesCompat.getDrawable(getResources(), R.drawable.white_kind, null));
        iconMap.put("wq", ResourcesCompat.getDrawable(getResources(), R.drawable.white_queue, null));
        iconMap.put("wr", ResourcesCompat.getDrawable(getResources(), R.drawable.white_rook, null));
        iconMap.put("wb", ResourcesCompat.getDrawable(getResources(), R.drawable.white_bishop, null));
        iconMap.put("wn", ResourcesCompat.getDrawable(getResources(), R.drawable.white_knight, null));
        iconMap.put("wp", ResourcesCompat.getDrawable(getResources(), R.drawable.white_pawn, null));

        findViewById(R.id.btn_next).setOnClickListener(e -> onNext());
        updateNextStatus();
    }

    private void updateNextStatus() {
        if (actionQueue.isEmpty()) findViewById(R.id.btn_next).setEnabled(false);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        displayKeyboard(Chess.keyBoard);
    }

    protected void displayKeyboard(String[][] keyboard) {
        String charSeq = "abcdefghijk";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String pos = (String) keyboardControls[i][j].getTag();
                {
                    if ((j + i) % 2 == 0) {
                        keyboardControls[i][j].setBackgroundColor(Color.parseColor("#ffce9e"));
                    } else {
                        keyboardControls[i][j].setBackgroundColor(Color.parseColor("#d18b47"));
                    }
                }
                keyboardControls[i][j].setScaleType(ImageView.ScaleType.FIT_CENTER);
                if (!keyboard[i][j].trim().equals("##")) {
                    Drawable drawable = iconMap.get(keyboard[i][j].trim().toLowerCase());
                    if (drawable == null) {
                        System.out.println("Drawable is null");
                    }
                    keyboardControls[i][j].setImageDrawable(drawable);
                } else {
                    keyboardControls[i][j].setImageDrawable(getResources().getDrawable(R.drawable.none));
                }
            }
        }
    }

    private void onNext() {
        Action action = actionQueue.remove();
        try {
            Chess.doAction(action);
            updateNextStatus();
            displayKeyboard(Chess.keyBoard);
        } catch (ChessException e) {
            e.printStackTrace();
        }
    }

}