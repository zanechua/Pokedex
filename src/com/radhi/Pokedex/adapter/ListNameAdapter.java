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
import com.radhi.Pokedex.other.Enum.NameType;

public class ListNameAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] valID;
    private Database db;

    public ListNameAdapter(Context context, String[] valID) {
        super(context, R.layout.row_pokemon_name, valID);

        /**Buat database baru**/
        db = new Database(context);
        this.context = context;
        this.valID = valID;
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

        /**Atur ID pokemon*/
        String id = valID[position];
        holder.txtRowID.setText(id + ".   ");

        /**Atur nama pokemon*/
        String nm = db.getPokemonName(id, NameType.ENGLISH);
        holder.txtRowName.setText(nm);

        /**Atur tipe pokemon*/
        String[] type = db.getPokemonType(id);
        db.getTypeName(holder.txtRowType1, Integer.valueOf(type[0]));
        db.getTypeName(holder.txtRowType2, Integer.valueOf(type[1]));

        /**Atur gambar pokemon*/
        db.setImageFromAssets(holder.imgRow, "sprites/normal/front/nf_" + id + ".png", ImgSize.SMALL);

        return rowView;
    }
}
