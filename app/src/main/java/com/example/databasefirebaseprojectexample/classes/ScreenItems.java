package com.example.databasefirebaseprojectexample.classes;

public class ScreenItems {

        String Title;
        int Description;
        int ScreenImg;

        public ScreenItems(String title, int description, int screenImg) {
            Title = title;
            Description = description;
            ScreenImg = screenImg;
        }
        public void setTitle(String title) {
            Title = title;
        }

        public void setDescription(int description) {
            Description = description;
        }

        public void setScreenImg(int screenImg) {
            ScreenImg = screenImg;
        }

        public String getTitle() {
            return Title;
        }

        public int getDescription() {
            return Description;
        }

        public int getScreenImg() {
            return ScreenImg;
        }
    }

