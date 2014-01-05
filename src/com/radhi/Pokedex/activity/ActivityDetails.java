package com.radhi.Pokedex.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import com.radhi.Pokedex.R;
import com.radhi.Pokedex.fragment.*;
import com.radhi.Pokedex.object.Pokemon;
import com.radhi.Pokedex.other.PagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ActivityDetails extends FragmentActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent i = getIntent();
        String ID = i.getStringExtra(ActivityMain.POKEMON_ID);
        String Name = i.getStringExtra(ActivityMain.POKEMON_NAME);
        this.setTitle(Name);

        makePage mPage = new makePage();
        mPage.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,ID);
    }

    private class makePage extends AsyncTask<String, Void, Void> {
        ProgressDialog ringProgressDialog;
        List<Fragment> fragmentList;
        List<String> titleList;

        @Override
        protected void onPreExecute() {
            ringProgressDialog = ProgressDialog.show(ActivityDetails.this, "Please wait...", "Loading data...");
        }

        @Override
        protected Void doInBackground(String... ID) {
            Pokemon pokemon = new Pokemon(getBaseContext(),ID[0]);
            Bundle args = new Bundle();
            args.putParcelable(ActivityMain.POKEMON_DATA,pokemon);

            fragmentList = new Vector<Fragment>();
            fragmentList.add(Fragment.instantiate(getBaseContext(), PokemonAppearance.class.getName(), args));
            fragmentList.add(Fragment.instantiate(getBaseContext(), PokemonData.class.getName(), args));
            fragmentList.add(Fragment.instantiate(getBaseContext(), PokemonStat.class.getName(), args));
            fragmentList.add(Fragment.instantiate(getBaseContext(), PokemonMove.class.getName(), args));

            titleList = new ArrayList<String>();
            titleList.add("APPEARANCE");
            titleList.add("DATA");
            titleList.add("STATS");
            titleList.add("MOVE");

            if (pokemon.OtherForm().length > 1) {
                fragmentList.add(Fragment.instantiate(getBaseContext(), PokemonForm.class.getName(), args));
                titleList.add("FORM");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            PagerAdapter mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
            ViewPager mPager = (ViewPager) findViewById(R.id.pager);
            mPager.setAdapter(mPagerAdapter);
            mPager.setOffscreenPageLimit(5);
            getWindow().setBackgroundDrawable(null);
            ringProgressDialog.dismiss();
        }
    }
}
