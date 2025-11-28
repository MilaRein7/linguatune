package com.linguatune.model;

public class Song {
    private String id;
    private String title;
    private String artist;
    private String genre;
    private String level;
    private String lyrics;

    public Song(String id,
                String title,
                String artist,
                String genre,
                String level,
                String lyrics) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.level = level;
        this.lyrics = lyrics;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getGenre() {
        return genre;
    }

    public String getLevel() {
        return level;
    }

    public String getLyrics() {
        return lyrics;
    }

    @Override
    public String toString() {
        return title + " — " + artist;
    }
}
