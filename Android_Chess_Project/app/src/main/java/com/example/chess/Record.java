package com.example.chess;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import chess.action.Action;

public class Record implements Serializable {
    private String name;
    private Date date;
    private List<Action> actions;

    public Record(String name) {
        this.name = name;
        this.date = new Date();
        this.actions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void addAction(Action action) {
        this.actions.add(action);
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
}
