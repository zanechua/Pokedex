package com.radhi.Pokedex.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import com.radhi.Pokedex.R;
import com.radhi.Pokedex.fragment.*;
import com.radhi.Pokedex.other.Database;
import com.radhi.Pokedex.other.Enum.NameType;
import com.radhi.Pokedex.other.PagerAdapter;

import java.util.List;
import java.util.Vector;

public class Main extends FragmentActivity implements PokemonName.OnPokemonSelectedListener {
    public static final String POKEMON_ID = "pokemon_id_from_main";
    public static final String POKEMON_NAME = "pokemon_name_from_main";
    public static final String POKEMON_ID_2 = "pokemon_id_for_fragment";
    public static final String MOVE_ID = "move_id_for_dialog";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawable(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_actionbar, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Pokemon's name");

        final PokemonName pokemonName =
                (PokemonName) getFragmentManager().findFragmentById(R.id.frag_pokemon_name);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String q) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String q) {
                if (pokemonName != null && pokemonName.isInLayout())
                    pokemonName.filterList(q);
                return true;
            }
        });

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                if (pokemonName != null && pokemonName.isInLayout())
                    pokemonName.filterList("");
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onPokemonSelected(String id) {
        ViewPager mPager = (ViewPager) findViewById(R.id.pager);
        Log.println(Log.INFO,"PokemonName","List with id = " + id + " is selected");

        if (mPager != null) {
            if (mPager.getBackground() != null) mPager.setBackground(null);
            if (mPager.getChildCount() > 1) getSupportFragmentManager().getFragments().clear();

            this.setTitle(new Database(this).getPokemonName(id,NameType.ENGLISH));

            Bundle args = new Bundle();
            args.putString(Main.POKEMON_ID_2, id);
            List<Fragment> fragmentList = new Vector<Fragment>();
            fragmentList.add(Fragment.instantiate(this, PokemonData2.class.getName(), args));
            fragmentList.add(Fragment.instantiate(this, PokemonMove.class.getName(), args));

            PagerAdapter mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragmentList);
            mPager.setAdapter(mPagerAdapter);
            mPager.setOffscreenPageLimit(2);
        }
        else {
            Intent intent = new Intent(Main.this, Details.class);
            intent.putExtra(Main.POKEMON_ID,id);
            intent.putExtra(Main.POKEMON_NAME,new Database(this).getPokemonName(id, NameType.ENGLISH));
            startActivity(intent);
        }
    }
}
