package com.linguatune.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Playlist {

    private String name;
    private final ObservableList<Song> songs = FXCollections.observableArrayList();

    public Playlist(String name) {
        this.name = name;
    }

    public ObservableList<Song> getSongs() {
        return songs;
    }
}