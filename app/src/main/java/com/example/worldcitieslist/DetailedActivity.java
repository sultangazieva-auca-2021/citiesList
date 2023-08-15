package com.example.worldcitieslist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.worldcitieslist.databinding.ActivityDetailedBinding;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;

public class DetailedActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int NUM_OF_PICTURES_IN_SLIDER = 5;
    private ActivityDetailedBinding binding;
    private ArrayList<String> urlList;
    ArrayList<ContactInfoModel> contactInfoModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");
        String time = intent.getStringExtra("Time");
        String weather = intent.getStringExtra("Weather");

        binding.cityName.setText(name);
        binding.timeItself.setText(time);
        binding.weatherItself.setText(weather);

        binding.descButton.setOnClickListener(this);
        binding.arrowBack.setOnClickListener(this);
        binding.arrowNext.setOnClickListener(this);

        urlList = new ArrayList<>();
        contactInfoModels = new ArrayList<>();

        String encodedKeyword;
        try {
            encodedKeyword = URLEncoder.encode(name, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        String url = "https://source.unsplash.com/random?" + encodedKeyword;

        for (int i = 0; i < NUM_OF_PICTURES_IN_SLIDER; i++) {
            String newUrl = url + "&timestamp=" + i;
            urlList.add(newUrl);
        }

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(DetailedActivity.this, urlList);
        binding.viewPager.setAdapter(viewPagerAdapter);

        binding.indicator.setViewPager(binding.viewPager);
        viewPagerAdapter.registerDataSetObserver(binding.indicator.getDataSetObserver());

        binding.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (binding.viewPager.getCurrentItem() == 0) {
                    binding.arrowBack.setVisibility(View.GONE);
                } else {
                    binding.arrowBack.setVisibility(View.VISIBLE);
                }

                if (binding.viewPager.getCurrentItem() == urlList.size() - 1) {
                    binding.arrowNext.setVisibility(View.GONE);
                } else {
                    binding.arrowNext.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.desc_button) {
//            binding.rvWeatherByTime.setVisibility(View.VISIBLE);
//            if (binding.descButton.getText().toString().equalsIgnoreCase("Show description")) {
//                binding.descButton.setText("Hide description");
//                binding.rvWeatherByTime.setVisibility(View.VISIBLE);
//            } else {
//                binding.descButton.setText("Show description");
//                binding.rvWeatherByTime.setVisibility(View.INVISIBLE);
//            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_CONTACTS}, 0);
            } else {
                getPhoneContact();
                Intent intent = new Intent(this, ContactListActivity.class);
                startActivity(intent);
            }

//            if (!binding.descButton.getText().toString().equalsIgnoreCase("access denied")) {
//                Intent intent = new Intent(this, ContactListActivity.class);
//                startActivity(intent);
//            }

        } else if (view.getId() == R.id.arrow_back) {
            binding.viewPager.setCurrentItem(getItem(-1), true);
        } else if (view.getId() == R.id.arrow_next) {
            binding.viewPager.setCurrentItem(getItem(1), true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhoneContact();
                Intent intent = new Intent(this, ContactListActivity.class);
                startActivity(intent);
            } else {
                binding.descButton.setText("access denied by user:\nchange from settings");
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void getPhoneContact() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        Log.i("CONTACT_PROVIDER", "TOTAL NUM OF CONTACTS: " + Integer.toString(cursor.getCount()));
        HashSet<String> uniquePhoneNumbers = new HashSet<>();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                @SuppressLint("Range") String contactPhoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                if (!uniquePhoneNumbers.contains(contactName)) {
                    contactInfoModels.add(new ContactInfoModel(contactName, contactPhoneNum));
                }

                uniquePhoneNumbers.add(contactName);
            }

            FillTheContactListRow.setContactInfoModels(contactInfoModels);
        }
    }

    private int getItem(int i) {
        return binding.viewPager.getCurrentItem() + i;
    }
}