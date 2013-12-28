package com.radhi.Pokedex.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.radhi.Pokedex.R;
import com.radhi.Pokedex.activity.ActivityForm;
import com.radhi.Pokedex.activity.ActivityMain;
import com.radhi.Pokedex.adapter.ListFormAdapter;
import com.radhi.Pokedex.object.Pokemon;
import com.radhi.Pokedex.other.Database;

public class PokemonForm extends Fragment {
    private Activity activity;
    private ListView listForm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon_form, container, false);
        Pokemon pokemon = getArguments().getParcelable(ActivityMain.POKEMON_DATA);

        ListFormAdapter adapter = new ListFormAdapter(activity, pokemon.OtherForm());
        listForm = (ListView) view.findViewById(R.id.listPokemonForm);
        listForm.setAdapter(adapter);

        listForm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] rowData = listForm.getItemAtPosition(position).toString().split(Database.SPLIT);

                if (!rowData[0].equals(rowData[4])) {
                    Intent intent = new Intent(activity, ActivityForm.class);
                    intent.putExtra(ActivityMain.POKEMON_FORM_ID, rowData[0]);
                    intent.putExtra(ActivityMain.POKEMON_NAME, rowData[1]);
                    intent.putExtra(ActivityMain.POKEMON_ID, rowData[4]);
                    startActivity(intent);
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
}
