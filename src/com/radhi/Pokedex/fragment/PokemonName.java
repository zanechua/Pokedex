package com.radhi.Pokedex.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.*;
import android.widget.*;
import com.radhi.Pokedex.R;
import com.radhi.Pokedex.adapter.ListNameAdapter;
import com.radhi.Pokedex.other.Database;
import com.radhi.Pokedex.other.Other.pokemonInterface;

public class PokemonName extends Fragment {
    private Activity activity;
    private Database DB;

    private GridView gridPokemon;
    private TextView txtShading;
    private ScrollView scrollFilter;

    private TextView txtFilterGeneration;
    private SparseArray<CheckBox> listChkGeneration;
    private boolean genVisible = false;

    private TextView txtFilterType;
    private SparseArray<CheckBox> listChkType;
    private boolean typeVisible = false;

    private TextView txtFilterColor;
    private SparseArray<CheckBox> listChkColor;
    private boolean colorVisible = false;

    private CheckBox chkBaby, chkGenderDiff;
    private TextView txtClearFilter;

    private String Name = "", Generation = "", Color = "", Type = "";
    private Boolean isBaby = false, hasGenderDiff = false;

    private pokemonInterface pokemonInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DB = new Database(activity);
        View view = inflater.inflate(R.layout.pokemon_name, container, false);
        setHasOptionsMenu(true);
        setComponentName(view);
        makeList();

        setCLickListenerOnFilter();

        gridPokemon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] row = gridPokemon.getItemAtPosition(position).toString().split(Database.SPLIT);
                pokemonInterface.pokemonSelected(row[0]);
            }
        });

        pokemonInterface = (pokemonInterface) activity;

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = getActivity();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_pokemon_name, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Pokemon's name");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String txt) {
                Name = txt;
                setFilter();
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_filter:
                toggleFilterArea();
                return true;
            default:
                return false;
        }
    }

    private void makeList() {
        ListNameAdapter adapter = new ListNameAdapter(activity,
                DB.getPokemonList(Name, Generation, Color, Type, isBaby, hasGenderDiff));
        gridPokemon.setAdapter(adapter);
    }

    private void setComponentName(View v) {
        gridPokemon = (GridView) v.findViewById(R.id.listPokemon);
        txtShading = (TextView) v.findViewById(R.id.shadingView);
        scrollFilter = (ScrollView) v.findViewById(R.id.scrollFilter);

        txtFilterGeneration = (TextView) v.findViewById(R.id.txtFilterGeneration);
        listChkGeneration = new SparseArray<CheckBox>();
        listChkGeneration.put(0, (CheckBox) v.findViewById(R.id.chkGeneration1));
        listChkGeneration.put(1, (CheckBox) v.findViewById(R.id.chkGeneration2));
        listChkGeneration.put(2, (CheckBox) v.findViewById(R.id.chkGeneration3));
        listChkGeneration.put(3, (CheckBox) v.findViewById(R.id.chkGeneration4));
        listChkGeneration.put(4, (CheckBox) v.findViewById(R.id.chkGeneration5));
        listChkGeneration.put(5, (CheckBox) v.findViewById(R.id.chkGeneration6));

        txtFilterType = (TextView) v.findViewById(R.id.txtFilterType);
        listChkType = new SparseArray<CheckBox>();
        listChkType.put(0, (CheckBox) v.findViewById(R.id.chkTypeNormal));
        listChkType.put(1, (CheckBox) v.findViewById(R.id.chkTypeFighting));
        listChkType.put(2, (CheckBox) v.findViewById(R.id.chkTypeFlying));
        listChkType.put(3, (CheckBox) v.findViewById(R.id.chkTypePoison));
        listChkType.put(4, (CheckBox) v.findViewById(R.id.chkTypeGround));
        listChkType.put(5, (CheckBox) v.findViewById(R.id.chkTypeRock));
        listChkType.put(6, (CheckBox) v.findViewById(R.id.chkTypeBug));
        listChkType.put(7, (CheckBox) v.findViewById(R.id.chkTypeGhost));
        listChkType.put(8, (CheckBox) v.findViewById(R.id.chkTypeSteel));
        listChkType.put(9, (CheckBox) v.findViewById(R.id.chkTypeFire));
        listChkType.put(10, (CheckBox) v.findViewById(R.id.chkTypeWater));
        listChkType.put(11, (CheckBox) v.findViewById(R.id.chkTypeGrass));
        listChkType.put(12, (CheckBox) v.findViewById(R.id.chkTypeElectric));
        listChkType.put(13, (CheckBox) v.findViewById(R.id.chkTypePsychic));
        listChkType.put(14, (CheckBox) v.findViewById(R.id.chkTypeIce));
        listChkType.put(15, (CheckBox) v.findViewById(R.id.chkTypeDragon));
        listChkType.put(16, (CheckBox) v.findViewById(R.id.chkTypeDark));
        listChkType.put(17, (CheckBox) v.findViewById(R.id.chkTypeFairy));

        txtFilterColor = (TextView) v.findViewById(R.id.txtFilterColor);
        listChkColor = new SparseArray<CheckBox>();
        listChkColor.put(0, (CheckBox) v.findViewById(R.id.chkColorBlack));
        listChkColor.put(1, (CheckBox) v.findViewById(R.id.chkColorBlue));
        listChkColor.put(2, (CheckBox) v.findViewById(R.id.chkColorBrown));
        listChkColor.put(3, (CheckBox) v.findViewById(R.id.chkColorGray));
        listChkColor.put(4, (CheckBox) v.findViewById(R.id.chkColorGreen));
        listChkColor.put(5, (CheckBox) v.findViewById(R.id.chkColorPink));
        listChkColor.put(6, (CheckBox) v.findViewById(R.id.chkColorPurple));
        listChkColor.put(7, (CheckBox) v.findViewById(R.id.chkColorRed));
        listChkColor.put(8, (CheckBox) v.findViewById(R.id.chkColorWhite));
        listChkColor.put(9, (CheckBox) v.findViewById(R.id.chkColorYellow));

        chkBaby = (CheckBox) v.findViewById(R.id.chkIsBaby);
        chkGenderDiff = (CheckBox) v.findViewById(R.id.chkHasGenderDiff);
        txtClearFilter = (TextView) v.findViewById(R.id.txtClearFilter);
    }

    private void toggleFilterArea() {
        if (scrollFilter.getVisibility() == View.GONE) {
            scrollFilter.setVisibility(View.VISIBLE);
            txtShading.setVisibility(View.VISIBLE);
        } else {
            scrollFilter.setVisibility(View.GONE);
            txtShading.setVisibility(View.GONE);
        }
    }

    private void toggleVisibility(boolean visibility, SparseArray<CheckBox> listCheckBox, TextView txtFilter) {
        int new_visibility = visibility ? View.GONE : View.VISIBLE;
        int drawable_right = visibility ? R.drawable.ic_max_pokedex : R.drawable.ic_min_pokedex;

        txtFilter.setCompoundDrawablesWithIntrinsicBounds(0,0,drawable_right,0);
        int nList = listCheckBox.size();
        for (int n = 0; n < nList; n++)
            listCheckBox.get(n).setVisibility(new_visibility);
    }

    private void clearFilter() {
        int nGen = listChkGeneration.size();
        int nType = listChkType.size();
        int nColor = listChkColor.size();

        for (int n = 0; n < nGen; n++) listChkGeneration.get(n).setChecked(false);
        for (int n = 0; n < nType; n++) listChkType.get(n).setChecked(false);
        for (int n = 0; n < nColor; n++) listChkColor.get(n).setChecked(false);

        chkBaby.setChecked(false);
        chkGenderDiff.setChecked(false);
    }

    private void setCLickListenerOnFilter() {
        txtFilterGeneration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(genVisible, listChkGeneration, txtFilterGeneration);
                genVisible = !genVisible;
            }
        });

        txtFilterType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(typeVisible, listChkType, txtFilterType);
                typeVisible = !typeVisible;
            }
        });

        txtFilterColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleVisibility(colorVisible, listChkColor, txtFilterColor);
                colorVisible = !colorVisible;
            }
        });

        int nGeneration = listChkGeneration.size();
        for (int n = 0; n < nGeneration; n++)
            listChkGeneration.get(n).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilter();
            }
        });

        int nType = listChkType.size();
        for (int n = 0; n < nType; n++)
            listChkType.get(n).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFilter();
                }
            });

        int nColor = listChkColor.size();
        for (int n = 0; n < nColor; n++)
            listChkColor.get(n).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setFilter();
                }
            });

        chkBaby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilter();
            }
        });

        chkGenderDiff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFilter();
            }
        });

        txtClearFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(Name.isEmpty() &&
                        Generation.isEmpty() &&
                        Color.isEmpty() &&
                        !hasGenderDiff &&
                        !isBaby)) {
                clearFilter(); setFilter();}
            }
        });
    }

    private void setFilter() {
        Generation = setFilterText(listChkGeneration);
        Type = setFilterText(listChkType);
        Color = setFilterText(listChkColor);
        isBaby = chkBaby.isChecked();
        hasGenderDiff = chkGenderDiff.isChecked();
        makeList();
    }

    private String setFilterText (SparseArray<CheckBox> listCheckBox) {
        String out = "";
        String value;
        int nList = listCheckBox.size();

        for (int n = 0; n < nList; n++) if (listCheckBox.get(n).isChecked()) {
            value = String.valueOf(n + 1);
            if (out.isEmpty()) out += value;
            else out += ", " + value;
        }

        return out;
    }
}
