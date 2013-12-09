package com.radhi.Pokedex.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import com.radhi.Pokedex.R;
import com.radhi.Pokedex.fragment.*;
import com.radhi.Pokedex.other.PagerAdapter;

import java.util.List;
import java.util.Vector;

public class Details extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getWindow().setBackgroundDrawable(null);

        /**Tangkap intent*/
        Intent i = getIntent();
        String id = i.getStringExtra(Main.POKEMON_ID);
        String nm = i.getStringExtra(Main.POKEMON_NAME);

        /**Ubah title activity*/
        this.setTitle(nm);

        /**Buat swipe navigation*/
        Bundle args = new Bundle();
        args.putString(Main.POKEMON_ID_2, id);
        List<Fragment> fragmentList = new Vector<Fragment>();
        fragmentList.add(Fragment.instantiate(this, PokemonAppearance.class.getName(), args));
        fragmentList.add(Fragment.instantiate(this, PokemonData.class.getName(), args));
        fragmentList.add(Fragment.instantiate(this, PokemonStat.class.getName(), args));
        fragmentList.add(Fragment.instantiate(this, PokemonMove.class.getName(), args));

        PagerAdapter mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragmentList);
        ViewPager mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(4);
    }
}
