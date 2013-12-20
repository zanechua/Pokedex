package com.radhi.Pokedex.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.radhi.Pokedex.other.Database;

public class MoveSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
    private final Context context;
    private final String[] spinItem;
    private final String additionalText;

    public MoveSpinnerAdapter(Context context, String[] spinItem, String additionalText) {
        this.context = context;
        this.spinItem = spinItem;
        this.additionalText = additionalText;
    }

    @Override
    public int getCount() {
        return spinItem.length;
    }

    @Override
    public String getItem(int position) {
        return spinItem[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView txtItem = new TextView(context);
        String item = spinItem[position].split(Database.SPLIT)[1];
        txtItem.setText(additionalText + " " + item);
        return txtItem;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView txtItem = new TextView(context);
        String item = spinItem[position].split(Database.SPLIT)[1];
        txtItem.setText(additionalText + " " + item);
        txtItem.setPadding(10,18,10,18);
        return txtItem;
    }
}
