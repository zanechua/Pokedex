package com.radhi.Pokedex.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.radhi.Pokedex.R;
import com.radhi.Pokedex.other.*;

public class ListMoveAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] moveID;
    private Database db;

    public ListMoveAdapter(Context context, String[] moveID) {
        super(context, R.layout.row_move, moveID);

        db = new Database(context);
        this.context = context;
        this.moveID = moveID;
    }

    static class viewHolder {
        public TextView txtMoveID;
        public TextView txtMoveName;
        public TextView txtMoveType;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.row_move, null);

            viewHolder v = new viewHolder();
            v.txtMoveID = (TextView) rowView.findViewById(R.id.txtMoveID);
            v.txtMoveName = (TextView) rowView.findViewById(R.id.txtMoveName);
            v.txtMoveType = (TextView) rowView.findViewById(R.id.txtMoveType);
            rowView.setTag(v);
        }

        viewHolder holder = (viewHolder) rowView.getTag();

        holder.txtMoveID.setText(moveID[position] + ".   ");
        holder.txtMoveName.setText(db.getMoveName(moveID[position]));
        db.getTypeName(holder.txtMoveType,
                Integer.valueOf(db.getMoveType(moveID[position])));
        return rowView;
    }
}