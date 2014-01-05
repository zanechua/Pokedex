package com.radhi.Pokedex.activity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.radhi.Pokedex.R;
import com.radhi.Pokedex.fragment.*;
import com.radhi.Pokedex.fragment.PokemonName.OnPokemonSelectedListener;
import com.radhi.Pokedex.object.Pokemon;
import com.radhi.Pokedex.other.ChangeLog;
import com.radhi.Pokedex.other.Database;
import com.radhi.Pokedex.other.PagerAdapter;
import com.radhi.Pokedex.other.ZipHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class ActivityMain extends FragmentActivity implements OnPokemonSelectedListener {
    public static final String POKEMON_DATA = "pokemon_data_from_main";
    public static final String POKEMON_ID = "pokemon_id_from_main";
    public static final String POKEMON_NAME = "pokemon_name_from_main";
    public static final String POKEMON_FORM_ID = "pokemon_form_id_from_main";

    public static final String MOVE_DATA = "move_data_for_fragment";
    public static final String MOVE_ID = "move_id_for_dialog";
    public static final String MOVE_NAME = "move_name_for_dialog";

    private static final String ART_URL = "https://www.dropbox.com/s/o3vf6fle56hbtvd/Art.zip?dl=1";
    private static final String SPRITES_URL = "https://www.dropbox.com/s/swc382u0h2gn7om/Sprites.zip?dl=1";
    private static final String UNZIP_TARGET = Environment.getExternalStorageDirectory().toString() + "/Pokedex Image/";

    private static final int bufferSize = 512 * 1024;

    private String zipLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawable(null);

        registerReceiver(onComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        ChangeLog cl = new ChangeLog(this);
        if (cl.isFirstRun())
            cl.getLogDialog().show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onComplete);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onPokemonSelected(String rowData) {
        ViewPager mPager = (ViewPager) findViewById(R.id.pager);
        String[] data = rowData.split(Database.SPLIT);
        String ID = data[0];
        String Name = data[1];

        if (mPager != null) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_search:
                final PokemonName pokemonName =
                        (PokemonName) getFragmentManager().findFragmentById(R.id.frag_pokemon_name);
                if (pokemonName != null && pokemonName.isInLayout())
                    pokemonName.showSearch();
                return true;
            case R.id.menu_art:
                startDownload(ART_URL,"Sugimori Art","Sugimori art for Pokédex","Art.zip");
                zipLocation = UNZIP_TARGET + "Art.zip";
                return true;
            case R.id.menu_sprite:
                startDownload(SPRITES_URL,"Pokémon Sprites","Sprites for Pokédex","Sprites.zip");
                zipLocation = UNZIP_TARGET + "Sprites.zip";
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class makePage extends AsyncTask<String, Void, Void> {
        ProgressDialog ringProgressDialog;
        List<Fragment> fragmentList;
        List<String> titleList;

        @Override
        protected void onPreExecute() {
            ringProgressDialog = ProgressDialog.show(ActivityMain.this, "Please wait...", "Loading data...");
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

    public long startDownload(String url, String title, String desc, String target) {
        File f = new File(Environment.getExternalStoragePublicDirectory("Pokedex Image"),target);
        if (f.exists()) f.delete();

        Uri uri = Uri.parse(url);
        Environment .getExternalStoragePublicDirectory("Pokedex Image") .mkdirs();

        DownloadManager mgr = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
        return mgr.enqueue(new DownloadManager.Request(uri)
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                        DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(title)
                .setDescription(desc)
                .setDestinationInExternalPublicDir("Pokedex Image", target));
    }

    BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            File f = new File(UNZIP_TARGET);
            if (f.exists()) f.delete();

            UnZipFile unzip = new UnZipFile();
            unzip.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,new ZipHelper(zipLocation, UNZIP_TARGET));
        }
    };

    public class UnZipFile extends AsyncTask<ZipHelper,Void,Void> {
        ProgressDialog ringProgressDialog;
        String zipLocation;

        @Override
        protected void onPreExecute() {
            ringProgressDialog = ProgressDialog.show(ActivityMain.this, "Please wait...", "Extracting data...");
        }

        @Override
        protected Void doInBackground(ZipHelper... zip) {
            zipLocation = zip[0].ZipFileLocation();
            zip[0].unzip(bufferSize);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            File f = new File(zipLocation);
            if (f.exists()) f.delete();

            ringProgressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        final PokemonName pokemonName =
                (PokemonName) getFragmentManager().findFragmentById(R.id.frag_pokemon_name);
        if (pokemonName != null && pokemonName.isInLayout() && pokemonName.isSearching())
            pokemonName.showSearch();
        else super.onBackPressed();
    }
}
