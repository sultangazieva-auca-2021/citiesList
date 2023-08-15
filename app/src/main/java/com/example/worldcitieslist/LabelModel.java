package com.example.worldcitieslist;

public class LabelModel {
    private int imageLabel;
    private String stringLabel;

    public LabelModel(int imageView, String stringLabel) {
        this.imageLabel = imageView;
        this.stringLabel = stringLabel;
    }

    public int getImageLabel() {
        return imageLabel;
    }

    public void setImageLabel(int imageLabel) {
        this.imageLabel = imageLabel;
    }

    public String getStringLabel() {
        return stringLabel;
    }

    public void setStringLabel(String stringLabel) {
        this.stringLabel = stringLabel;
    }
}
