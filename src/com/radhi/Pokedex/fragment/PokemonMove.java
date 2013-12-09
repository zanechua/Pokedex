package com.radhi.Pokedex.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.radhi.Pokedex.R;
import com.radhi.Pokedex.activity.Main;
import com.radhi.Pokedex.adapter.ListMoveAdapter;
import com.radhi.Pokedex.other.Database;
import com.radhi.Pokedex.activity.MoveDetail;

public class PokemonMove extends Fragment {
    private Activity activity;
    private String ID, Version, MoveMethod;
    private Database DB;
    private ListMoveAdapter moveAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ID = getArguments().getString(Main.POKEMON_ID_2);
        DB = new Database(activity);

        View view = inflater.inflate(R.layout.fragment_pokemon_move, container, false);

        final Spinner spinVersion = (Spinner) view.findViewById(R.id.spinVersion);
        final Spinner spinMoveMethod = (Spinner) view.findViewById(R.id.spinMoveMethod);
        final TextView lblTap = (TextView) view.findViewById(R.id.lblTapMove);
        final ListView listMove = (ListView) view.findViewById(R.id.listPokemonMove);
        final LinearLayout boxSpinner = (LinearLayout) view.findViewById(R.id.boxMoveSpinner);
        LinearLayout boxMove = (LinearLayout) view.findViewById(R.id.boxMoveFilter);

        setSpinnerAdapter(spinVersion,DB.getPokemonMoveVersion(ID));
        spinVersion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Version = spinVersion.getSelectedItem().toString().split(" - ")[0];
                setSpinnerAdapter(spinMoveMethod,DB.getPokemonMoveMethod(ID,Version));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        boxMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinVersion.isShown()) {
                    Version = spinVersion.getSelectedItem().toString().split(" - ")[0];
                    MoveMethod = spinMoveMethod.getSelectedItem().toString().split(" - ")[0];
                    moveAdapter = new ListMoveAdapter(activity, DB.getMove(ID,Version,MoveMethod));
                    listMove.setAdapter(moveAdapter);

                    lblTap.setText("tap to show filter box");

                    boxSpinner.setVisibility(View.GONE);
                    listMove.setVisibility(View.VISIBLE);
                } else {
                    lblTap.setText("tap to filter");
                    listMove.setVisibility(View.GONE);
                    boxSpinner.setVisibility(View.VISIBLE);
                }
            }
        });

        listMove.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(activity,MoveDetail.class);
                intent.putExtra(Main.MOVE_ID,listMove.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = getActivity();
    }

    private void setSpinnerAdapter(Spinner spin, String[] entries) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                activity, android.R.layout.simple_spinner_item, entries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
    }
}
