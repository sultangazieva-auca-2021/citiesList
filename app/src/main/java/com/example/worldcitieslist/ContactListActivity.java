package com.example.worldcitieslist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.worldcitieslist.databinding.ActivityContactListBinding;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {
    private ActivityContactListBinding binding;

    private ArrayList<ContactInfoModel> contactInfoModels;

    private ContactListAdapter contactListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        contactInfoModels = FillTheContactListRow.getContactInfoModels();

        contactListAdapter = new ContactListAdapter(this, contactInfoModels);

        binding.rvContactList.setAdapter(contactListAdapter);
        binding.rvContactList.setLayoutManager(new LinearLayoutManager(this));
    }
}
