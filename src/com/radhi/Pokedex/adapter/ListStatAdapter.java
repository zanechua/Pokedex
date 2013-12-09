package com.radhi.Pokedex.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.radhi.Pokedex.R;

public class ListStatAdapter extends ArrayAdapter<String[]> {
    private final Context context;
    private final String[][] stat;

    public ListStatAdapter(Context context, String[][] stat) {
        super(context, R.layout.row_pokemon_dex, stat);

        this.context = context;
        this.stat = stat;
    }

    static class viewHolder {
        public TextView lblStat;
        public TextView txtStat;
        public TextView graphStat;
        public TextView graphAccentStat;
        public TextView txtMaxStat;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.row_pokemon_stat, null);

            viewHolder v = new viewHolder();
            v.lblStat = (TextView) rowView.findViewById(R.id.lblStat);
            v.txtStat = (TextView) rowView.findViewById(R.id.txtStat);
            v.graphStat = (TextView) rowView.findViewById(R.id.graphStat);
            v.graphAccentStat = (TextView) rowView.findViewById(R.id.graphAccentStat);
            v.txtMaxStat = (TextView) rowView.findViewById(R.id.txtMaxStat);
            rowView.setTag(v);
        }

        viewHolder holder = (viewHolder) rowView.getTag();

        holder.lblStat.setText(stat[position][0]);
        holder.txtStat.setText(stat[position][1]);
        holder.txtMaxStat.setText(stat[position][2]);

        float weight = (float)(Double.parseDouble(stat[position][1]) / Double.parseDouble(stat[position][2]) * 10);

        holder.graphStat.setLayoutParams(
                new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,weight));
        holder.graphAccentStat.setLayoutParams(
                new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,10-weight));

        return rowView;
    }
}