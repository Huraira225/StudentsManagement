package com.example.databasefirebaseprojectexample.classes;

public class AnnouncementsGetterSetter {
    private String userid;
    private String tile;
    private String anncuncements;

    public AnnouncementsGetterSetter() {
    }

    public AnnouncementsGetterSetter(String userid, String tile, String anncuncements) {
        this.userid = userid;
        this.tile = tile;
        this.anncuncements = anncuncements;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTile() {
        return tile;
    }

    public void setTile(String tile) {
        this.tile = tile;
    }

    public String getAnncuncements() {
        return anncuncements;
    }

    public void setAnncuncements(String anncuncements) {
        this.anncuncements = anncuncements;
    }
}