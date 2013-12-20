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
        public TextView txtMoveID;
        public TextView txtMoveName;
        public TextView txtMoveType;
        public TextView txtMoveLevel;
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
            v.txtMoveID = (TextView) rowView.findViewById(R.id.txtMoveID);
            v.txtMoveName = (TextView) rowView.findViewById(R.id.txtMoveName);
            v.txtMoveType = (TextView) rowView.findViewById(R.id.txtMoveType);
            v.txtMoveLevel = (TextView) rowView.findViewById(R.id.txtMoveLevel);
            rowView.setTag(v);
        }

        viewHolder holder = (viewHolder) rowView.getTag();

        String[] rowData = moveData[position].split(Database.SPLIT);
        holder.txtMoveID.setText(rowData[0] + ".");
        holder.txtMoveName.setText(rowData[1]);
        holder.txtMoveLevel.setText(rowData[2].equals("0") ? "" : "(lv " + rowData[2] + ")");
        Database.setTypeName(holder.txtMoveType, Integer.valueOf(rowData[3]));

        return rowView;
    }
}