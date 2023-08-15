package com.example.worldcitieslist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.worldcitieslist.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {
    private ActivityMainBinding binding;
    private ArrayList<CityInfoModel> cityInfoModels;
    private List<Object> list;

    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if (!task.isSuccessful()) {
//                            System.out.println("Fetching FCM registration token failed");
//                            return;
//                        }
//
//                        // Get new FCM registration token
//                        String token = task.getResult();
//
//                        // Log and toast
//                        Log.i("TOKEN", "Your token is: " + token);
//                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
//                        //binding.etToken.setText(token);
//                    }
//                });

        cityInfoModels = FillTheCityInfoRow.getCityInfoModels();
        list = new ArrayList<>();

//        list.addAll(cityInfoModels);
        for (int i = 0; i < cityInfoModels.size(); i++) {
            if (i % 5 == 0) {
                list.add(new LabelModel(R.drawable.plain, getString(R.string.city_tour_corp)));
            }
            list.add(cityInfoModels.get(i));
        }

        recyclerViewAdapter = new RecyclerViewAdapter(this, list, this);

        binding.resView.setAdapter(recyclerViewAdapter);
        binding.resView.setLayoutManager(new LinearLayoutManager(this));
        binding.resView.setClickable(true);

        binding.btnAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddOneActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Object clickedItem = list.get(position);
        if (clickedItem instanceof CityInfoModel) {
            CityInfoModel city = (CityInfoModel) clickedItem;
            Intent intent = new Intent(this, DetailedActivity.class);
            intent.putExtra("Name", city.getCityName());
            intent.putExtra("Time", city.getTime());
            intent.putExtra("Weather", city.getTemperature());
            intent.putExtra("Desc", city.getDesc());

            startActivity(intent);
        } else if (clickedItem instanceof LabelModel) {
            LabelModel labelModel = (LabelModel) clickedItem;
            if (labelModel.getImageLabel() == R.drawable.plain) {
                labelModel.setStringLabel("Travel More");
                labelModel.setImageLabel(R.drawable.airplane_pink);
            } else {
                labelModel.setStringLabel(getString(R.string.city_tour_corp));
                labelModel.setImageLabel(R.drawable.plain);
            }

            recyclerViewAdapter.notifyItemChanged(position);
        }
    }
}
