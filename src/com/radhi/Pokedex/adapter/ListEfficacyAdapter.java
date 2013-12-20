package com.radhi.Pokedex.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.radhi.Pokedex.R;
import com.radhi.Pokedex.other.Database;

public class ListEfficacyAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] efficacy;

    public ListEfficacyAdapter(Context context, String[] efficacy) {
        super(context, R.layout.row_pokemon_dex, efficacy);

        this.context = context;
        this.efficacy = efficacy;
    }

    static class viewHolder {
        public TextView txtValEfficacy;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.row_pokemon_efficacy, null);

            viewHolder v = new viewHolder();
            v.txtValEfficacy = (TextView) rowView.findViewById(R.id.txtValEfficacy);
            rowView.setTag(v);
        }
        viewHolder holder = (viewHolder) rowView.getTag();

        String[] rowData = efficacy[position].split(Database.SPLIT);
        Database.setTypeName(holder.txtValEfficacy, Integer.valueOf(rowData[0]));
        String currentText = holder.txtValEfficacy.getText().toString();
        String value;
        if (rowData[1].equals("0.5")) value = "½";
        else if (rowData[1].equals("0.25")) value = "¼";
        else value = rowData[1];

        holder.txtValEfficacy.setText(currentText + " " + value + " ×");

        return rowView;
    }
}