package com.radhi.Pokedex.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.radhi.Pokedex.R;
import com.radhi.Pokedex.other.Database;

public class ListEfficacyAdapter extends ArrayAdapter<Double[]> {
    private final Context context;
    private final Double[][] efficacy;

    public ListEfficacyAdapter(Context context, Double[][] efficacy) {
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

        new Database(context).getTypeName(holder.txtValEfficacy,efficacy[position][0].intValue());
        String currentText = holder.txtValEfficacy.getText().toString();
        String value;

        if (efficacy[position][1] == 0.5) value = "½";
        else if (efficacy[position][1] == 0.25) value = "¼";
        else value = String.format("%.0f",efficacy[position][1]);

        holder.txtValEfficacy.setText(currentText + " " + value + " ×");

        return rowView;
    }
}