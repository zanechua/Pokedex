package com.radhi.Pokedex.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.radhi.Pokedex.R;
import com.radhi.Pokedex.adapter.ListNameAdapter;
import com.radhi.Pokedex.other.Database;

public class PokemonName extends Fragment {
    private Activity activity;
    private Database DB;
    private OnPokemonSelectedListener pokemonSelectedListener;
    private EditText edPokemon;
    private ListView listPokemon;
    private LinearLayout boxSearch;
    private Boolean searching = false;

    private String Name = "%",
            Generation = "0",
            Color = "0",
            Type1 = "0",
            Type2 = "0";
    private Boolean isBaby = false,
            hasGenderDiff = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon_name, container, false);
        DB = new Database(activity);

        edPokemon = (EditText) view.findViewById(R.id.txtPokemonName);
        listPokemon = (ListView) view.findViewById(R.id.listPokemon);
        boxSearch = (LinearLayout) view.findViewById(R.id.boxSearchPokemon);

        final TextView txtClear = (TextView) view.findViewById(R.id.txtClear);
        final Spinner spinGeneration = (Spinner) view.findViewById(R.id.spinGeneration);
        final Spinner spinColor = (Spinner) view.findViewById(R.id.spinColor);
        final Spinner spinType1 = (Spinner) view.findViewById(R.id.spinType1);
        final Spinner spinType2 = (Spinner) view.findViewById(R.id.spinType2);
        final CheckBox checkIsBaby = (CheckBox) view.findViewById(R.id.checkIsBaby);
        final CheckBox checkHasGenderDiff = (CheckBox) view.findViewById(R.id.checkHasGenderDiff);
        final TextView txtTapClear = (TextView) view.findViewById(R.id.txtTap);

        updateList();

        listPokemon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pokemonSelectedListener.onPokemonSelected(listPokemon.getItemAtPosition(position).toString());
            }
        });

        edPokemon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) txtClear.setVisibility(View.VISIBLE);
                else txtClear.setVisibility(View.GONE);

                Name = "%" + s.toString() + "%";
                updateList();
            }
        });

        txtClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {edPokemon.setText("");}
        });

        spinGeneration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Generation = String.valueOf(position);
                updateList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Color = String.valueOf(position);
                updateList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinType1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Type1 = String.valueOf(position);
                updateList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinType2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Type2 = String.valueOf(position);
                updateList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        checkIsBaby.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isBaby = isChecked;
                updateList();
            }
        });

        checkHasGenderDiff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hasGenderDiff = isChecked;
                updateList();
            }
        });

        txtTapClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinGeneration.setSelection(0);
                spinColor.setSelection(0);
                spinType1.setSelection(0);
                spinType2.setSelection(0);
                checkIsBaby.setChecked(false);
                checkHasGenderDiff.setChecked(false);
            }
        });

        return view;
    }

    public interface OnPokemonSelectedListener {
        public void onPokemonSelected(String rowData);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = getActivity();
        pokemonSelectedListener = (OnPokemonSelectedListener) activity;
    }

    public void showSearch() {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (boxSearch.getVisibility() == View.GONE) {
            boxSearch.setVisibility(View.VISIBLE);
            edPokemon.requestFocus();
            searching = true;
        }
        else {
            imm.hideSoftInputFromWindow(edPokemon.getWindowToken(), 0);
            boxSearch.setVisibility(View.GONE);
            searching = false;
        }
    }

    public boolean isSearching() {return searching;}

    private void updateList() {
        ListNameAdapter adapter = new ListNameAdapter(activity,
                DB.getPokemonList(Name, Generation, Color, Type1, Type2, isBaby, hasGenderDiff));
        listPokemon.setAdapter(adapter);
    }
}
