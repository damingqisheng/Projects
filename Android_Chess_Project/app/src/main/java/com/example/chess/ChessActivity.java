package com.example.chess;

import android.app.Application;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.core.app.DialogCompat;
import androidx.core.content.res.ConfigurationHelper;
import androidx.core.content.res.ResourcesCompat;

import java.nio.charset.CharsetDecoder;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chess.Chess;
import chess.action.Action;
import chess.action.MoveAction;
import chess.action.RandomMoveAction;
import chess.action.ResignAction;
import chess.action.UndoAction;
import chess.exceptions.Checkmate;
import chess.exceptions.ChessException;
import chess.exceptions.RequirePromotion;


public class ChessActivity extends AppCompatActivity implements View.OnClickListener {
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

    private Button choice;
    private Map<String, Drawable> iconMap = new HashMap<>();
    private String pickupPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess);

        String charSeq = "abcdefgh";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                keyboardControls[j][i] = findViewById(keyboardControlIds[j][i]);
                keyboardControls[j][i].setTag(String.format("%s%d", charSeq.charAt(i), 8 - j));
                keyboardControls[j][i].setOnClickListener(this);
            }
        }

        findViewById(R.id.btn_b_resign).setOnClickListener(e -> tryAgain( Chess.isWhite ? "Black" : "White"));
        findViewById(R.id.btn_undo).setOnClickListener(e -> onUndoClick());
        findViewById(R.id.btn_undo).setEnabled(Chess.canUndo());
        findViewById(R.id.btn_ai).setOnClickListener(e -> onAIClick());
        findViewById(R.id.btn_draw).setOnClickListener(e -> onDraw());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

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

        displayKeyboard(Chess.keyBoard);
    }

    protected void showPromotion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Which to promotion");

        String[] available = new String[]{"Queen", "Rook", "Knight", "Bishop"};
        builder.setSingleChoiceItems(available, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Chess.promotion(available[which]);
                } catch (RequirePromotion requirePromotion) {
                    showPromotion();
                }
                displayKeyboard(Chess.keyBoard);
            }
        });
        builder.setCancelable(false);
        builder.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Chess.isWhite = !Chess.isWhite;
                displayKeyboard(Chess.keyBoard);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    protected void onDraw() {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        onSaveGame();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setTitle("Draw ?")
                .create()
                .show();
    }

    protected void displayKeyboard(String[][] keyboard) {
        String charSeq = "abcdefghijk";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String pos = (String) keyboardControls[i][j].getTag();
                if (pickupPosition != null && pickupPosition.equals(pos)) {
                    keyboardControls[i][j].setBackgroundColor(Color.parseColor("#ff8b47"));
                } else {
                    if ((j + i) % 2 == 0) {
                        keyboardControls[i][j].setBackgroundColor(Color.parseColor("#ffce9e"));
                    } else {
                        keyboardControls[i][j].setBackgroundColor(Color.parseColor("#d18b47"));
                    }
                }
                keyboardControls[i][j].setScaleType(ImageView.ScaleType.FIT_CENTER);
                if (keyboard[i][j].trim().equals("##") || keyboard[i][j].trim().isEmpty()) {
                    keyboardControls[i][j].setImageDrawable(getResources().getDrawable(R.drawable.none));
                } else {
                    Drawable drawable = iconMap.get(keyboard[i][j].trim().toLowerCase());
                    if (drawable == null) {
                        System.out.println("Drawable is null");
                    }
                    keyboardControls[i][j].setImageDrawable(drawable);
                }
            }
        }

        showPlayerInfo();
        findViewById(R.id.btn_undo).setEnabled(Chess.canUndo());
        if (Chess.checking) {
            Toast.makeText(this, "Checking", Toast.LENGTH_LONG).show();
            setTitle("Checking...");
        } else {
            setTitle((Chess.isWhite ? "White" : "Black") + "'s Turn");
        }
    }

    private void showPlayerInfo() {
    }

    protected void onAIClick() {
        try {
            Chess.doAction(new RandomMoveAction());
        } catch (ChessException e) {
            e.printStackTrace();
        } finally {
            displayKeyboard(Chess.keyBoard);
        }
    }

    protected void tryAgain(String winner) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(false);
        builder.setTitle(String.format("%s is win.", winner));
//        builder.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Chess.init();
//                displayKeyboard(Chess.keyBoard);
//            }
//        });

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onSaveGame();
            }
        });

//        builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Chess.init();
//                finish();
//            }
//        });
        builder.create().show();
    }

    private Record record = new Record("Untitled game");

    public void onUndoClick() {
        try {
            Chess.doAction(new UndoAction());
            record.getActions().remove(record.getActions().size() - 1);
            Chess.isWhite = !Chess.isWhite;
        } catch (ChessException e) {
            e.printStackTrace();
        } finally {
            displayKeyboard(Chess.keyBoard);
        }
    }

    private void onSaveGame() {
        View view = this.getLayoutInflater().inflate(R.layout.dialog_save, null);
        new AlertDialog.Builder(this)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = ((EditText) view.findViewById(R.id.edit_title)).getText().toString();
                        if (title != null && !title.trim().isEmpty()) {
                            record.setName(title);
                        }
                        Storage.save(ChessActivity.this, record);
                        Toast.makeText(ChessActivity.this, "Game saved", Toast.LENGTH_LONG).show();
                        Chess.init();
                        ChessActivity.this.finish();
                    }
                })
                .setCancelable(false)
                .setTitle("Set game title")
                .setView(view)
                .create()
                .show();
    }

    @Override
    public void onClick(View v) {
        String pos = (String) v.getTag();
        if (pickupPosition != null) {
            // clear pick
            if (pickupPosition.equals(pos)) {
                pickupPosition = null;
                displayKeyboard(Chess.keyBoard);
                return;
            }
//            Toast.makeText(this, String.format("%s -> %s", pickupPosition, pos), Toast.LENGTH_SHORT).show();
            try {
                Action action = new MoveAction(pickupPosition, pos);
                Chess.doAction(action);
                record.addAction(action);
            } catch (Checkmate e) {
//                Toast.makeText(this, String.format("%s is win", e.getWinner()), Toast.LENGTH_LONG).show();
                tryAgain(e.getWinner());
            } catch (RequirePromotion e) {
                showPromotion();
            } catch (ChessException e) {
                e.printStackTrace();
            }
            pickupPosition = null;
        } else {
            pickupPosition = pos;
//            Toast.makeText(this, pos, Toast.LENGTH_SHORT).show();
        }

        displayKeyboard(Chess.keyBoard);
    }
}