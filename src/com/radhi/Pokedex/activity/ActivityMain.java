package com.radhi.Pokedex.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import com.radhi.Pokedex.R;
import com.radhi.Pokedex.fragment.*;
import com.radhi.Pokedex.fragment.PokemonName.OnPokemonSelectedListener;
import com.radhi.Pokedex.object.Pokemon;
import com.radhi.Pokedex.other.Database;
import com.radhi.Pokedex.other.PagerAdapter;

import java.util.List;
import java.util.Vector;

public class ActivityMain extends FragmentActivity implements OnPokemonSelectedListener {
    public static final String POKEMON_DATA = "pokemon_data_from_main";
    public static final String POKEMON_ID = "pokemon_id_from_main";
    public static final String POKEMON_NAME = "pokemon_name_from_main";
    public static final String MOVE_DATA = "move_data_for_fragment";
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
    public void onPokemonSelected(String rowData) {
        ViewPager mPager = (ViewPager) findViewById(R.id.pager);
        String[] data = rowData.split(Database.SPLIT);
        String ID = data[0];
        String Name = data[1];

        if (mPager != null) {
            if (mPager.getBackground() != null) mPager.setBackgroundDrawable(null);
            if (mPager.getChildCount() > 1) getSupportFragmentManager().getFragments().clear();

            this.setTitle(Name);
            makePage make = new makePage();
            make.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,ID);
        } else {
            Intent intent = new Intent(ActivityMain.this, ActivityDetails.class);
            intent.putExtra(ActivityMain.POKEMON_ID,ID);
            intent.putExtra(ActivityMain.POKEMON_NAME,Name);
            startActivity(intent);
        }
    }

    public class makePage extends AsyncTask<String, Void, List<Fragment>> {
        @Override
        protected List<Fragment> doInBackground(String... ID) {
            Pokemon pokemon = new Pokemon(getBaseContext(),ID[0]);
            Bundle args = new Bundle();
            args.putParcelable(ActivityMain.POKEMON_DATA,pokemon);

            List<Fragment> fragmentList = new Vector<Fragment>();
            fragmentList.add(Fragment.instantiate(getBaseContext(), PokemonAppearance.class.getName(), args));
            fragmentList.add(Fragment.instantiate(getBaseContext(), PokemonData.class.getName(), args));
            fragmentList.add(Fragment.instantiate(getBaseContext(), PokemonStat.class.getName(), args));
            fragmentList.add(Fragment.instantiate(getBaseContext(), PokemonMove.class.getName(), args));

            return fragmentList;
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onPostExecute(List<Fragment> result) {
            PagerAdapter mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), result);
            ViewPager mPager = (ViewPager) findViewById(R.id.pager);
            mPager.setAdapter(mPagerAdapter);
            mPager.setOffscreenPageLimit(4);
            getWindow().setBackgroundDrawable(null);
        }
    }
}
