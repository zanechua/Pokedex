package com.radhi.Pokedex.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.radhi.Pokedex.R;
import com.radhi.Pokedex.other.Enum.NameType;
import com.radhi.Pokedex.other.Enum.ImgSize;

import java.io.IOException;
import java.io.InputStream;

public class ListDexAdapter extends ArrayAdapter<String[]> {
    private final Context context;
    private final String[][] dexData;

    public ListDexAdapter(Context context, String[][] dexData) {
        super(context, R.layout.row_pokemon_dex, dexData);

        this.context = context;
        this.dexData = dexData;
    }

    static class viewHolder {
        public LinearLayout rowDex;
        public TextView lblDexName;
        public TextView txtDexNumber;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.row_pokemon_dex, null);

            viewHolder v = new viewHolder();
            v.rowDex = (LinearLayout) rowView.findViewById(R.id.rowDex);
            v.lblDexName = (TextView) rowView.findViewById(R.id.lblDexName);
            v.txtDexNumber = (TextView) rowView.findViewById(R.id.txtDexNumber);
            rowView.setTag(v);
        }

        viewHolder holder = (viewHolder) rowView.getTag();

        holder.lblDexName.setText(dexData[position][0] + " ID");
        holder.txtDexNumber.setText(dexData[position][1]);

        if (position == dexData.length - 1) holder.rowDex.setPadding(0,0,0,0);

        return rowView;
    }
}
