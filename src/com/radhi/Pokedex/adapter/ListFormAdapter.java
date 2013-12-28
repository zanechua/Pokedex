package com.radhi.Pokedex.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.radhi.Pokedex.R;
import com.radhi.Pokedex.other.Database;
import com.radhi.Pokedex.other.Enum.ImgSize;

public class ListFormAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] listName;

    public ListFormAdapter(Context context, String[] listName) {
        super(context, R.layout.row_pokemon_name, listName);

        this.context = context;
        this.listName = listName;
    }

    static class viewHolder {
        public TextView txtRowID;
        public TextView txtRowName;
        public TextView txtRowType1;
        public TextView txtRowType2;
        public ImageView imgRow;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.row_pokemon_name, null);

            viewHolder v = new viewHolder();
            v.txtRowID = (TextView) rowView.findViewById(R.id.txtRowID);
            v.txtRowName = (TextView) rowView.findViewById(R.id.txtRowNama);
            v.txtRowType1 = (TextView) rowView.findViewById(R.id.txtRowType1);
            v.txtRowType2 = (TextView) rowView.findViewById(R.id.txtRowType2);
            v.imgRow = (ImageView) rowView.findViewById(R.id.imgRowIcon);
            rowView.setTag(v);
        }

        viewHolder holder = (viewHolder) rowView.getTag();

        String[] dataRow = listName[position].split(Database.SPLIT);

        holder.txtRowID.setVisibility(View.GONE);
        holder.txtRowName.setText(dataRow[1]);
        Database.setTypeName(holder.txtRowType1, Integer.valueOf(dataRow[2]));
        Database.setTypeName(holder.txtRowType2, Integer.valueOf(dataRow[3]));
        Database.setImage(holder.imgRow, "sprites/normal/front/nf_" + dataRow[0] + ".png", ImgSize.SMALL);

        return rowView;
    }
}
