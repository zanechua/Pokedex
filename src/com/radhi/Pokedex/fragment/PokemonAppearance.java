package com.radhi.Pokedex.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.radhi.Pokedex.R;
import com.radhi.Pokedex.activity.Main;
import com.radhi.Pokedex.other.Database;
import com.radhi.Pokedex.other.Enum.ImgSize;

public class PokemonAppearance extends Fragment {
    private Activity activity;
    private String ID;
    private Database DB;
    private ImageView imgFront;
    private ImageView imgBack;
    private TextView txtSprite;
    private TextView txtFemale;
    private TextView txtTap;
    private ImageView imgFront2;
    private ImageView imgBack2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon_appearance, container, false);

        ImageView imgArt = (ImageView) view.findViewById(R.id.imgSugimoriArt);
        LinearLayout boxSprite = (LinearLayout) view.findViewById(R.id.boxSprite);
        LinearLayout boxFemale = (LinearLayout) view.findViewById(R.id.boxFemale);

        ID = getArguments().getString(Main.POKEMON_ID_2);
        DB = new Database(activity);
        imgFront = (ImageView) view.findViewById(R.id.imgFront);
        imgBack = (ImageView) view.findViewById(R.id.imgBack);
        txtSprite = (TextView) view.findViewById(R.id.txtSprite);
        txtFemale = (TextView) view.findViewById(R.id.txtSpriteFemale);
        txtTap = (TextView) view.findViewById(R.id.txtTap);
        imgFront2 = (ImageView) view.findViewById(R.id.imgFront2);
        imgBack2 = (ImageView) view.findViewById(R.id.imgBack2);

        DB.setImageFromAssets(imgArt, "art/sa_" + ID + ".png", ImgSize.LARGE);
        DB.setImageFromAssets(imgFront, "sprites/normal/front/nf_" + ID + ".png", ImgSize.SMALL);
        DB.setImageFromAssets(imgBack, "sprites/normal/back/nb_" + ID + ".png", ImgSize.SMALL);

        if (DB.hasFemaleForm(ID)) {
            txtSprite.setText("Male");
            txtFemale.setVisibility(View.VISIBLE);
            boxFemale.setVisibility(View.VISIBLE);
            DB.setImageFromAssets(imgFront2, "sprites/normal/front/nf_" + ID + "_female.png", ImgSize.SMALL);
            DB.setImageFromAssets(imgBack2, "sprites/normal/back/nb_" + ID + "_female.png", ImgSize.SMALL);
        }

        boxSprite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtSprite.getText().toString().equals("Normal") || txtSprite.getText().toString().equals("Male"))
                    changeToShiny();
                else changeToNormal();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = getActivity();
    }

    private void changeToShiny() {
        if (DB.hasFemaleForm(ID)) {
            txtSprite.setText("Male Shiny");
            txtFemale.setText("Female Shiny");
            DB.setImageFromAssets(imgFront2, "sprites/shiny/front/sf_" + ID + "_female.png", ImgSize.SMALL);
            DB.setImageFromAssets(imgBack2, "sprites/shiny/back/sb_" + ID + "_female.png", ImgSize.SMALL);
        } else txtSprite.setText("Shiny");
        txtTap.setText("tap to see normal sprite");
        DB.setImageFromAssets(imgFront, "sprites/shiny/front/sf_" + ID + ".png", ImgSize.SMALL);
        DB.setImageFromAssets(imgBack, "sprites/shiny/back/sb_" + ID + ".png", ImgSize.SMALL);
    }

    private void changeToNormal() {
        if (DB.hasFemaleForm(ID)) {
            txtSprite.setText("Male");
            txtFemale.setText("Female");
            DB.setImageFromAssets(imgFront2, "sprites/normal/front/nf_" + ID + "_female.png", ImgSize.SMALL);
            DB.setImageFromAssets(imgBack2, "sprites/normal/back/nb_" + ID + "_female.png", ImgSize.SMALL);
        } else txtSprite.setText("Normal");
        txtTap.setText("tap to see shiny sprite");
        DB.setImageFromAssets(imgFront, "sprites/normal/front/nf_" + ID + ".png", ImgSize.SMALL);
        DB.setImageFromAssets(imgBack, "sprites/normal/back/nb_" + ID + ".png", ImgSize.SMALL);
    }
}