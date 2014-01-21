package com.radhi.Pokedex.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.radhi.Pokedex.R;
import com.radhi.Pokedex.other.Database;

public class ListMoveAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] moveData;

    public ListMoveAdapter(Context context, String[] moveData) {
        super(context, R.layout.row_move, moveData);

        this.context = context;
        this.moveData = moveData;
    }

    static class viewHolder {
        public LinearLayout rowMove;
        public TextView txtMoveLevel;
        public TextView txtMoveName;
        public TextView txtMovePower;
        public TextView txtMoveAccuracy;
        public TextView txtMoveType;
        public TextView txtMoveCategory;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.row_move, null);

            viewHolder v = new viewHolder();
            v.rowMove = (LinearLayout) rowView.findViewById(R.id.rowMove);
            v.txtMoveName = (TextView) rowView.findViewById(R.id.txtMoveName);
            v.txtMovePower = (TextView) rowView.findViewById(R.id.txtMovePower);
            v.txtMoveAccuracy = (TextView) rowView.findViewById(R.id.txtMoveAccuracy);
            v.txtMoveLevel = (TextView) rowView.findViewById(R.id.txtMoveLevel);
            v.txtMoveType = (TextView) rowView.findViewById(R.id.txtMoveType);
            v.txtMoveCategory = (TextView) rowView.findViewById(R.id.txtMoveCategory);
            rowView.setTag(v);
        }

        viewHolder holder = (viewHolder) rowView.getTag();

        String[] rowData = moveData[position].split(Database.SPLIT);
        holder.txtMoveName.setText(rowData[1]);
        holder.txtMoveLevel.setText(rowData[2].equals("0") ? "" : "lv " + rowData[2]);
        holder.txtMovePower.setText(rowData[4].equals("0") ? "" : rowData[4]);
        holder.txtMoveAccuracy.setText(rowData[5].equals("0") ? "" : rowData[5]);
        Database.setTypeName(holder.txtMoveType, Integer.valueOf(rowData[3]));
        Database.setCategoryResource(holder.txtMoveCategory, Integer.valueOf(rowData[6]));

        return rowView;
    }
}