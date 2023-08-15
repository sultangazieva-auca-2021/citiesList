package com.example.worldcitieslist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.worldcitieslist.databinding.ActivityAddOneBinding;

import java.util.List;

public class AddOneActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityAddOneBinding binding;
    private List<CityInfoModel> cityInfoModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddOneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnOk.setOnClickListener(this);
        binding.btnCancel.setOnClickListener(this);

        cityInfoModels = FillTheCityInfoRow.getCityInfoModels();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == binding.btnOk.getId() || view.getId() == binding.btnCancel.getId()) {
            String newName = binding.etCityName.getEditableText().toString();
            String temperature = binding.etTemperature.getEditableText().toString();
            String time = binding.etCityTime.getEditableText().toString();
            if (view.getId() == binding.btnOk.getId()) {
                if (newName.isEmpty()) {
                    binding.etCityName.setError("!");
                    binding.etCityName.requestFocus();
                }

                if (temperature.isEmpty()) {
                    binding.etTemperature.setError("!");
                }

                if (time.isEmpty()) {
                    binding.etCityTime.setError("!");
                }

                if (!newName.isEmpty() && !temperature.isEmpty() && !time.isEmpty()) {
                    CityInfoModel newCityInfoModel = new CityInfoModel(newName, temperature, time, "This is " + newName);
                    cityInfoModels.add(newCityInfoModel);
                    Intent intent = new Intent(AddOneActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            if (view.getId() == binding.btnCancel.getId()) {
                finish();
            }
        }
    }
}
