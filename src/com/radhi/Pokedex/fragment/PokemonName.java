package com.radhi.Pokedex.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.radhi.Pokedex.R;
import com.radhi.Pokedex.other.Database;
import com.radhi.Pokedex.adapter.ListNameAdapter;

public class PokemonName extends Fragment {
    private Activity activity;
    private ListNameAdapter adapter;
    private Database DB;
    private View view = null;
    private OnPokemonSelectedListener pokemonSelectedListener;
    private ListView listPokemon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pokemon_name,container,false);
        listPokemon = (ListView) view.findViewById(R.id.listPokemon);
        DB = new Database(activity);

        adapter = new ListNameAdapter(activity,DB.getPokemonID(""));
        listPokemon.setAdapter(adapter);

        listPokemon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ID = listPokemon.getItemAtPosition(position).toString();
                pokemonSelectedListener.onPokemonSelected(ID);
            }
        });

        return view;
    }

    public interface OnPokemonSelectedListener {
        public void onPokemonSelected(String id);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = getActivity();
        pokemonSelectedListener = (OnPokemonSelectedListener) activity;
    }

    public void filterList(String criteria) {
        if (view != null) {
            adapter = new ListNameAdapter(activity,DB.getPokemonID(criteria));
            listPokemon.setAdapter(adapter);
        }
    }
}