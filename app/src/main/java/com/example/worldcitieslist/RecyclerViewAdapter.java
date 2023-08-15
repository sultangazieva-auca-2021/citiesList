package com.example.worldcitieslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<Object> list;
    private final RecyclerViewInterface recyclerViewInterface;
    public RecyclerViewAdapter(Context context, List<Object> list, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.list = list;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof CityInfoModel) { // important
            return 0;
        }
        return 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) { // important viewType is already a type we do not have to use context
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.city_info_row, parent, false);
            return new InfoHolder(view);
        } else {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.label_row, parent, false);
            return new LabelHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (this.getItemViewType(position) == 0) {
            CityInfoModel city = (CityInfoModel) list.get(position);
            InfoHolder cityInfoModel = (InfoHolder) holder;
            cityInfoModel.cityName.setText(city.getCityName());
            cityInfoModel.temperature.setText(city.getTemperature());
            cityInfoModel.time.setText(city.getTime());
        } else {
            LabelModel label = (LabelModel) list.get(position);
            LabelHolder labelHolder = (LabelHolder) holder;
            labelHolder.imageView.setImageResource(label.getImageLabel());
            labelHolder.textView.setText(label.getStringLabel());

            int color;
            if (label.getImageLabel() == R.drawable.plain) {
                color = labelHolder.itemView.getResources().getColor(R.color.coral);
            } else {
                color = labelHolder.itemView.getResources().getColor(R.color.black);
            }

            labelHolder.textView.setTextColor(color);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerViewInterface != null) {
                    int pos = holder.getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(pos);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class InfoHolder extends RecyclerView.ViewHolder {
        TextView cityName;
        TextView temperature;
        TextView time;
        public InfoHolder(@NonNull View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.city_name);
            temperature = itemView.findViewById(R.id.weather_itself);
            time = itemView.findViewById(R.id.time_itself);
        }
    }

    public static class LabelHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public LabelHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageLabel);
            textView = itemView.findViewById(R.id.corp_name);
        }
    }
}
