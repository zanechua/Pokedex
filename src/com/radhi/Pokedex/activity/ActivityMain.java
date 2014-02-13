package com.radhi.Pokedex.activity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import com.radhi.Pokedex.R;
import com.radhi.Pokedex.fragment.PokemonDetails;
import com.radhi.Pokedex.fragment.PokemonName;
import com.radhi.Pokedex.other.Other;
import com.radhi.Pokedex.other.ZipHelper;

import java.io.File;

public class ActivityMain extends FragmentActivity implements Other.pokemonInterface {
    private String unzipTarget;
    private String zipLocation;
    private DownloadManager mgr;

    private FrameLayout fragmentContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawable(null);

        fragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);
        if (savedInstanceState == null) {
            PokemonName pokemonName = new PokemonName();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_list_container,pokemonName)
                    .commit();

            mgr = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);

            registerReceiver(onComplete,
                    new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

            unzipTarget = Other.ImageLocation;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_art:
                Other.startDownload(mgr,Other.ArtURL, "Sugimori Art", "Sugimori art for Pokédex", "Art.zip");
                zipLocation = unzipTarget + "Art.zip";
                return true;
            case R.id.menu_sprite:
                Other.startDownload(mgr,Other.SpritesURL,"Pokémon Sprites","Sprites for Pokédex","Sprites.zip");
                zipLocation = unzipTarget + "Sprites.zip";
                return true;
            case R.id.menu_changelog:
                Intent change = new Intent(ActivityMain.this, ActivityAboutChangelog.class);
                change.putExtra(Other.AboutOrChange,0);
                startActivity(change);
                return true;
            case R.id.menu_about:
                Intent about = new Intent(ActivityMain.this, ActivityAboutChangelog.class);
                about.putExtra(Other.AboutOrChange, 1);
                startActivity(about);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
      
    @Override
    public void pokemonSelected(String id) {
        if (fragmentContainer == null) {
            Intent intent = new Intent(this, ActivityDetails.class);
            intent.putExtra(Other.PokemonId, id);
            this.startActivity(intent);
        } else {
            PokemonDetails pokemonDetails = new PokemonDetails();

            Bundle args = new Bundle();
            args.putString(Other.PokemonId, id);
            pokemonDetails.setArguments(args);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, pokemonDetails)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void formSelected(String id, String img_id, String name, boolean formSwitchable) {
        Bundle args = new Bundle();
        args.putString(Other.PokemonId, id);
        args.putString(Other.PokemonImageId, img_id);
        args.putString(Other.PokemonName, name);

        PokemonDetails alternativeForm = new PokemonDetails();
        alternativeForm.setArguments(args);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, alternativeForm)
                .addToBackStack(null)
                .commit();
    }

    public class UnzipFile extends AsyncTask<ZipHelper,Void,Void> {
        ProgressDialog ringProgressDialog;
        String zipLocation;

        @Override
        protected void onPreExecute() {
            ringProgressDialog = ProgressDialog.show(ActivityMain.this, null, "Extracting data");
        }

        @Override
        protected Void doInBackground(ZipHelper... zip) {
            zipLocation = zip[0].ZipFileLocation();
            zip[0].unzip(2048);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            File f = new File(zipLocation);
            if (f.exists()) f.delete();

            ringProgressDialog.dismiss();
        }
    }

    BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            File f = new File(unzipTarget);
            if (f.exists()) f.delete();

            UnzipFile unzip = new UnzipFile();
            unzip.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,new ZipHelper(zipLocation, unzipTarget));
        }
    };
}
