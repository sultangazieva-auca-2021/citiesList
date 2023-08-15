package com.example.worldcitieslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final ArrayList<ContactInfoModel> listOfContacts;
    public ContactListAdapter(Context context, ArrayList<ContactInfoModel> list) {
        this.context = context;
        this.listOfContacts = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (listOfContacts.get(position) != null) { // important
            return 0;
        }
        return 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.contact_list_row, parent, false);
        return new ContactInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (this.getItemViewType(position) == 0) {
            ContactInfoModel contactInfoModel = (ContactInfoModel) listOfContacts.get(position);
            ContactInfoHolder contactInfoHolder = (ContactInfoHolder) holder;
            contactInfoHolder.name.setText(contactInfoModel.getName());
            contactInfoHolder.phoneNum.setText(contactInfoModel.getPhoneNum());
        }
    }

    @Override
    public int getItemCount() {
        return listOfContacts.size();
    }

    public static class ContactInfoHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView phoneNum;

        public ContactInfoHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.contact_name);
            phoneNum = itemView.findViewById(R.id.contact_phone_number);
        }
    }
}
