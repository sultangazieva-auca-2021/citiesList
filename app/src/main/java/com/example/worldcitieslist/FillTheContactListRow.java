package com.example.worldcitieslist;

import android.app.Application;

import java.util.ArrayList;

public class FillTheContactListRow extends Application {
    private static ArrayList<ContactInfoModel> contactInfoModels = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static ArrayList<ContactInfoModel> getContactInfoModels() {
        return contactInfoModels;
    }

    public static void setContactInfoModels(ArrayList<ContactInfoModel> contactInfoModels) {
        FillTheContactListRow.contactInfoModels = contactInfoModels;
    }
}
