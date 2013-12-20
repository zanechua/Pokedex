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

public class ListAbilityAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] ability;

    public ListAbilityAdapter(Context context, String[] ability) {
        super(context, R.layout.row_pokemon_dex, ability);

        this.context = context;
        this.ability = ability;
    }

    static class viewHolder {
        public LinearLayout rowAbility;
        public TextView lblAbilityName;
        public TextView txtAbilityDescription;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.row_pokemon_ability, null);

            viewHolder v = new viewHolder();
            v.rowAbility = (LinearLayout) rowView.findViewById(R.id.rowAbility);
            v.lblAbilityName = (TextView) rowView.findViewById(R.id.lblAbilityName);
            v.txtAbilityDescription = (TextView) rowView.findViewById(R.id.txtAbilityDescription);
            rowView.setTag(v);
        }
        viewHolder holder = (viewHolder) rowView.getTag();

        String[] rowData = ability[position].split(Database.SPLIT);

        String abilityName;
        if (rowData[1].equals("0")) abilityName = "Ability : ";
        else abilityName = "Hidden Ability : ";

        holder.lblAbilityName.setText(abilityName + rowData[0]);
        holder.txtAbilityDescription.setText(rowData[2]);

        if (position == ability.length - 1) holder.rowAbility.setPadding(0,0,0,-4);

        return rowView;
    }
}