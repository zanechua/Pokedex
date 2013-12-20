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
import com.radhi.Pokedex.activity.ActivityMain;
import com.radhi.Pokedex.activity.ActivityMoveDetail;
import com.radhi.Pokedex.adapter.ListMoveAdapter;
import com.radhi.Pokedex.adapter.MoveSpinnerAdapter;
import com.radhi.Pokedex.object.Pokemon;
import com.radhi.Pokedex.other.Database;

public class PokemonMove extends Fragment {
    private Activity activity;
    private String ID, version, moveMethod;
    private ListMoveAdapter moveAdapter;
    private Database DB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon_move, container, false);
        final Pokemon pokemon = getArguments().getParcelable(ActivityMain.POKEMON_DATA);

        final Spinner spinVersion = (Spinner) view.findViewById(R.id.spinVersion);
        final Spinner spinMoveMethod = (Spinner) view.findViewById(R.id.spinMoveMethod);
        final TextView lblTap = (TextView) view.findViewById(R.id.lblTapMove);
        final ListView listMove = (ListView) view.findViewById(R.id.listPokemonMove);
        final LinearLayout boxSpinner = (LinearLayout) view.findViewById(R.id.boxMoveSpinner);
        LinearLayout boxMove = (LinearLayout) view.findViewById(R.id.boxMoveFilter);

        setSpinnerAdapter(spinVersion,pokemon.AvailableMoveVersion(),"Pokémon");
        setSpinnerAdapter(spinMoveMethod, pokemon.AvailableMoveMethod(),"");
        DB = new Database(activity);

        boxMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinVersion.isShown()) {
                    ID = pokemon.ID();
                    version = spinVersion.getSelectedItem().toString().split(Database.SPLIT)[0];
                    moveMethod = spinMoveMethod.getSelectedItem().toString().split(Database.SPLIT)[0];

                    moveAdapter = new ListMoveAdapter(
                            activity, DB.getPokemonMoveList(ID,version,moveMethod));
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
                Intent intent = new Intent(activity, ActivityMoveDetail.class);
                intent.putExtra(ActivityMain.MOVE_ID, listMove.getItemAtPosition(position).toString().split(Database.SPLIT)[0]);
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

    private void setSpinnerAdapter(Spinner spin, String[] entries, String add) {
        MoveSpinnerAdapter adapter = new MoveSpinnerAdapter(activity,entries,add);
        spin.setAdapter(adapter);
    }
}
