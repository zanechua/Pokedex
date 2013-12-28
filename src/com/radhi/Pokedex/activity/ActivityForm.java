package com.radhi.Pokedex.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import com.radhi.Pokedex.R;
import com.radhi.Pokedex.fragment.PokemonAppearance;
import com.radhi.Pokedex.fragment.PokemonStat;
import com.radhi.Pokedex.object.Pokemon;
import com.radhi.Pokedex.other.PagerAdapter;

import java.util.List;
import java.util.Vector;

public class ActivityForm extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent i = getIntent();
        String Form_ID = i.getStringExtra(ActivityMain.POKEMON_FORM_ID);
        String Name = i.getStringExtra(ActivityMain.POKEMON_NAME);
        String ID = i.getStringExtra(ActivityMain.POKEMON_ID);
        this.setTitle(Name);

        makePage mPage = new makePage();
        mPage.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,ID,Form_ID);
    }

    private class makePage extends AsyncTask<String, Void, List<Fragment>> {
        ProgressDialog ringProgressDialog;

        @Override
        protected void onPreExecute() {
            ringProgressDialog = ProgressDialog.show(ActivityForm.this, "Please wait...", "Loading data...");
        }

        @Override
        protected List<Fragment> doInBackground(String... ID) {
            Pokemon pokemon = new Pokemon(getBaseContext(),ID[0]);
            Bundle args = new Bundle();
            args.putParcelable(ActivityMain.POKEMON_DATA,pokemon);
            args.putString(ActivityMain.POKEMON_FORM_ID,ID[1]);

            List<Fragment> fragmentList = new Vector<Fragment>();
            fragmentList.add(Fragment.instantiate(getBaseContext(), PokemonAppearance.class.getName(), args));
            fragmentList.add(Fragment.instantiate(getBaseContext(), PokemonStat.class.getName(), args));

            return fragmentList;
        }

        @Override
        protected void onPostExecute(List<Fragment> result) {
            ringProgressDialog.dismiss();
            PagerAdapter mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), result);
            ViewPager mPager = (ViewPager) findViewById(R.id.pager);
            mPager.setAdapter(mPagerAdapter);
            mPager.setOffscreenPageLimit(2);
            getWindow().setBackgroundDrawable(null);
        }
    }
}
