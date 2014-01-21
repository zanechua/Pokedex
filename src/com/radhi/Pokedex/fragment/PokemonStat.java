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
import com.radhi.Pokedex.activity.ActivityMain;
import com.radhi.Pokedex.adapter.ListEfficacyAdapter;
import com.radhi.Pokedex.adapter.ListStatAdapter;
import com.radhi.Pokedex.object.Pokemon;

public class PokemonStat extends Fragment {
    public static final int MAX = 255;
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon_stat, container, false);
        Pokemon pokemon = getArguments().getParcelable(ActivityMain.POKEMON_DATA);

        TextView txtBaseEXP = (TextView) view.findViewById(R.id.txtBaseEXP);
        TextView txtBaseEffort = (TextView) view.findViewById(R.id.txtBaseEffort);
        TextView txtBaseHappines = (TextView) view.findViewById(R.id.txtBaseHappiness);
        LinearLayout boxStat = (LinearLayout) view.findViewById(R.id.boxStat);
        LinearLayout boxDefenceStrong = (LinearLayout) view.findViewById(R.id.boxDefenceStrong);
        LinearLayout boxDefenceImmune = (LinearLayout) view.findViewById(R.id.boxDefenceImmune);
        LinearLayout boxDefenceWeak = (LinearLayout) view.findViewById(R.id.boxDefenceWeak);

        txtBaseEXP.setText(pokemon.BaseExperience());
        txtBaseEffort.setText(pokemon.BaseEffort());
        txtBaseHappines.setText(pokemon.BaseHappiness());
        
        fillBoxEffifacy(boxDefenceStrong,pokemon.TypeDefenceStrong());
        fillBoxEffifacy(boxDefenceImmune,pokemon.TypeDefenceImmune());
        fillBoxEffifacy(boxDefenceWeak,pokemon.TypeDefenceWeak());

        ListStatAdapter listStatAdapter = new ListStatAdapter(activity, pokemon.Stats());
        for (int n = 0; n < listStatAdapter.getCount(); n++)
            boxStat.addView(listStatAdapter.getView(n, null, boxStat));

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = getActivity();
    }

    private void fillBoxEffifacy(LinearLayout box, String[] data) {
        ListEfficacyAdapter adapter = new ListEfficacyAdapter(activity,data);
        for (int n = 0; n < adapter.getCount(); n++)
            box.addView(adapter.getView(n,null,box));
    }

}
