package com.radhi.Pokedex.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.radhi.Pokedex.R;
import com.radhi.Pokedex.activity.Main;
import com.radhi.Pokedex.adapter.ListEfficacyAdapter;
import com.radhi.Pokedex.adapter.ListStatAdapter;
import com.radhi.Pokedex.other.*;
import com.radhi.Pokedex.other.Enum.*;

public class PokemonStat extends Fragment {
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String ID = getArguments().getString(Main.POKEMON_ID_2);
        Database DB = new Database(activity);

        View view = inflater.inflate(R.layout.fragment_pokemon_stat, container, false);

        final LinearLayout boxEfficacy = (LinearLayout) view.findViewById(R.id.boxTypeEfficacy);
        final LinearLayout boxOffence = (LinearLayout) view.findViewById(R.id.boxOffence);
        final LinearLayout boxDefence = (LinearLayout) view.findViewById(R.id.boxDefence);
        final TextView lblEfficacy = (TextView) view.findViewById(R.id.lblTypeEfficacy);
        final TextView lblTapEfficacy = (TextView) view.findViewById(R.id.lblTapEfficacy);
        TextView txtBaseEXP = (TextView) view.findViewById(R.id.txtBaseEXP);
        TextView txtBaseEffort = (TextView) view.findViewById(R.id.txtBaseEffort);
        TextView txtBaseHappines = (TextView) view.findViewById(R.id.txtBaseHappiness);
        LinearLayout boxStat = (LinearLayout) view.findViewById(R.id.boxStat);
        LinearLayout boxOffenceStrong = (LinearLayout) view.findViewById(R.id.boxOffenceStrong);
        LinearLayout boxOffenceImmune = (LinearLayout) view.findViewById(R.id.boxOffenceImmune);
        LinearLayout boxOffenceWeak = (LinearLayout) view.findViewById(R.id.boxOffenceWeak);
        LinearLayout boxDefenceStrong = (LinearLayout) view.findViewById(R.id.boxDefenceStrong);
        LinearLayout boxDefenceImmune = (LinearLayout) view.findViewById(R.id.boxDefenceImmune);
        LinearLayout boxDefenceWeak = (LinearLayout) view.findViewById(R.id.boxDefenceWeak);

        txtBaseEXP.setText(DB.getPokemonBaseEXP(ID));
        txtBaseEffort.setText(DB.getPokemonBaseEffort(ID));
        txtBaseHappines.setText(DB.getPokemonBaseHappiness(ID));

        Double[][] DefenceStrong, DefenceWeak, DefenceImmune;
        Double[][] OffenceStrong, OffenceWeak, OffenceImmune;
        String[] type = DB.getPokemonType(ID);
        DefenceStrong = DB.getPokemonDefence(Effectiveness.NOT_EFFECTIVE, type);
        DefenceWeak = DB.getPokemonDefence(Effectiveness.SUPER_EFFECTIVE, type);
        DefenceImmune = DB.getPokemonDefence(Effectiveness.IMMUNE, type);
        OffenceStrong = DB.getPokemonOffence(Effectiveness.SUPER_EFFECTIVE, type);
        OffenceWeak = DB.getPokemonOffence(Effectiveness.NOT_EFFECTIVE, type);
        OffenceImmune = DB.getPokemonOffence(Effectiveness.IMMUNE, type);
        
        fillBoxEffifacy(boxDefenceStrong,DefenceStrong);
        fillBoxEffifacy(boxDefenceImmune,DefenceImmune);
        fillBoxEffifacy(boxDefenceWeak,DefenceWeak);
        fillBoxEffifacy(boxOffenceStrong,OffenceStrong);
        fillBoxEffifacy(boxOffenceImmune,OffenceImmune);
        fillBoxEffifacy(boxOffenceWeak,OffenceWeak);
        
        boxDefence.setVisibility(View.GONE);

        ListStatAdapter listStatAdapter = new ListStatAdapter(activity, DB.getPokemonStat(ID));
        for (int n = 0; n < listStatAdapter.getCount(); n++)
            boxStat.addView(listStatAdapter.getView(n, null, boxStat));

        boxEfficacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boxOffence.isShown()) {
                    lblEfficacy.setText("Defence");
                    lblTapEfficacy.setText("tap to see offence");
                    boxOffence.setVisibility(View.GONE);
                    boxDefence.setVisibility(View.VISIBLE);
                } else {
                    lblEfficacy.setText("Offence");
                    lblTapEfficacy.setText("tap to see defence");
                    boxDefence.setVisibility(View.GONE);
                    boxOffence.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = getActivity();
    }

    private void fillBoxEffifacy(LinearLayout box, Double[][] data) {
        ListEfficacyAdapter adapter = new ListEfficacyAdapter(activity,data);
        for (int n = 0; n < adapter.getCount(); n++)
            box.addView(adapter.getView(n,null,box));
    }

}
