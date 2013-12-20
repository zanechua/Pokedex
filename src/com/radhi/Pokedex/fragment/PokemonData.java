package com.radhi.Pokedex.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.radhi.Pokedex.R;
import com.radhi.Pokedex.activity.ActivityMain;
import com.radhi.Pokedex.object.Pokemon;
import com.radhi.Pokedex.other.Database;
import com.radhi.Pokedex.adapter.ListAbilityAdapter;
import com.radhi.Pokedex.adapter.ListDexAdapter;

import java.util.Random;

public class PokemonData extends Fragment {
    private Activity activity;
    private Pokemon pokemon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon_data, container, false);
        final LinearLayout boxNationalID = (LinearLayout) view.findViewById(R.id.boxNationalID);
        final LinearLayout boxPokedexNumber = (LinearLayout) view.findViewById(R.id.boxDexNumber);
        final TextView txtDescription = (TextView) view.findViewById(R.id.txtPokemonDescription);

        pokemon = getArguments().getParcelable(ActivityMain.POKEMON_DATA);

        ListDexAdapter listDexAdapter = new ListDexAdapter(activity, pokemon.DexNumber());
        ListAbilityAdapter listAbilityAdapter = new ListAbilityAdapter(activity, pokemon.Ability());

        LinearLayout boxAbility = (LinearLayout) view.findViewById(R.id.boxAbility);
        TextView txtNationalID = (TextView) view.findViewById(R.id.txtNationalID);
        TextView txtJapaneseName = (TextView) view.findViewById(R.id.txtJapaneseName);
        TextView txtSpecies = (TextView) view.findViewById(R.id.txtSpecies);
        TextView txtHabitat = (TextView) view.findViewById(R.id.txtHabitat);
        TextView txtType1 = (TextView) view.findViewById(R.id.txtPokemonType1);
        TextView txtType2 = (TextView) view.findViewById(R.id.txtPokemonType2);
        TextView txtHeight = (TextView) view.findViewById(R.id.txtHeight);
        TextView txtWeight = (TextView) view.findViewById(R.id.txtWeight);
        TextView txtShape = (TextView) view.findViewById(R.id.txtShape);
        TextView txtGender = (TextView) view.findViewById(R.id.txtGender);
        TextView txtEgg = (TextView) view.findViewById(R.id.txtEgg);
        TextView txtHatch = (TextView) view.findViewById(R.id.txtHatch);
        TextView txtGrowth = (TextView) view.findViewById(R.id.txtGrowth);
        TextView txtCapture = (TextView) view.findViewById(R.id.txtCapture);

        txtNationalID.setText(pokemon.ID());
        txtDescription.setText(Html.fromHtml(getDescription(pokemon)));
        txtJapaneseName.setText(pokemon.JapaneseName() + " (" + pokemon.RomajiName() + ")");
        txtSpecies.setText(pokemon.Species());
        txtHabitat.setText(pokemon.Habitat());
        txtHeight.setText(pokemon.Height());
        txtWeight.setText(pokemon.Weight());
        txtShape.setText(pokemon.BodyShape());
        txtGender.setText(pokemon.Gender());
        txtEgg.setText(pokemon.EggGroups());
        txtHatch.setText(pokemon.HatchCounter());
        txtGrowth.setText(pokemon.GrowthRate());
        txtCapture.setText(pokemon.CaptureRate());
        Database.setTypeName(txtType1, Integer.valueOf(pokemon.Type1()));
        Database.setTypeName(txtType2, Integer.valueOf(pokemon.Type2()));

        for (int n = 0; n < listAbilityAdapter.getCount(); n++)
            boxAbility.addView(listAbilityAdapter.getView(n, null, null));

        for (int n = 0; n < listDexAdapter.getCount(); n++)
            boxPokedexNumber.addView(listDexAdapter.getView(n, null, boxPokedexNumber));
        boxPokedexNumber.setVisibility(View.GONE);

        boxNationalID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boxNationalID.setVisibility(View.GONE);
                boxPokedexNumber.setVisibility(View.VISIBLE);
            }
        });

        boxPokedexNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boxPokedexNumber.setVisibility(View.GONE);
                boxNationalID.setVisibility(View.VISIBLE);
            }
        });

        txtDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDescription.setText(Html.fromHtml(getDescription(pokemon)));
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = getActivity();
    }

    private String getDescription(Pokemon pokemon) {
        int Rnd = new Random().nextInt(pokemon.Description().length);
        String[] desc = pokemon.Description()[Rnd].split(Database.SPLIT);
        return "<i>" +desc[1] + "</i><br/><b>(Pokémon " + desc[0] + ")</b>";
    }
}
