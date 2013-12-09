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
import com.radhi.Pokedex.activity.Main;
import com.radhi.Pokedex.other.Database;
import com.radhi.Pokedex.other.Enum.NameType;
import com.radhi.Pokedex.adapter.ListAbilityAdapter;
import com.radhi.Pokedex.adapter.ListDexAdapter;

public class PokemonData extends Fragment {
    private Activity activity;
    private String ID;
    private LinearLayout boxNationalID;
    private LinearLayout boxPokedexNumber;
    private TextView txtDescription;
    private Database DB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ID = getArguments().getString(Main.POKEMON_ID_2);
        DB = new Database(activity);

        View view = inflater.inflate(R.layout.fragment_pokemon_data, container, false);
        ListDexAdapter listDexAdapter = new ListDexAdapter(activity, DB.getPokedexNumber(ID));
        ListAbilityAdapter listAbilityAdapter = new ListAbilityAdapter(activity, DB.getPokemonAbility(ID));

        boxNationalID = (LinearLayout) view.findViewById(R.id.boxNationalID);
        boxPokedexNumber = (LinearLayout) view.findViewById(R.id.boxDexNumber);
        txtDescription = (TextView) view.findViewById(R.id.txtPokemonDescription);

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

        for (int n = 0; n < listDexAdapter.getCount(); n++)
            boxPokedexNumber.addView(listDexAdapter.getView(n, null, boxPokedexNumber));
        boxPokedexNumber.setVisibility(View.GONE);

        txtNationalID.setText(ID);
        txtDescription.setText(Html.fromHtml(DB.getPokemonDescription(ID)));
        txtJapaneseName.setText(DB.getPokemonName(ID, NameType.JAPANESE) + " (" +
                DB.getPokemonName(ID,NameType.ROMAJI) + ")");
        txtSpecies.setText(DB.getPokemonName(ID,NameType.SPECIES) + " Pokémon");
        txtHabitat.setText(DB.getPokemonHabitat(ID));
        txtHeight.setText(DB.getPokemonHeight(ID));
        txtWeight.setText(DB.getPokemonWeight(ID));
        txtShape.setText(DB.getPokemonShape(ID));
        txtGender.setText(DB.getPokemonGenderRate(ID));
        txtEgg.setText(DB.getPokemonEgg(ID));
        txtHatch.setText(DB.getPokemonHatchCounter(ID));
        txtGrowth.setText(DB.getPokemonGrowthRate(ID));
        txtCapture.setText(DB.getPokemonCaptureRate(ID));

        for (int n = 0; n < listAbilityAdapter.getCount(); n++)
            boxAbility.addView(listAbilityAdapter.getView(n, null, null));

        String[] type = DB.getPokemonType(ID);
        DB.getTypeName(txtType1,Integer.valueOf(type[0]));
        if (type.length > 1) DB.getTypeName(txtType2,Integer.valueOf(type[1]));

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
                txtDescription.setText(Html.fromHtml(DB.getPokemonDescription(ID)));
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = getActivity();
    }
}
