package com.radhi.Pokedex.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.radhi.Pokedex.R;
import com.radhi.Pokedex.activity.ActivityMain;
import com.radhi.Pokedex.object.Pokemon;
import com.radhi.Pokedex.other.Database;
import com.radhi.Pokedex.other.Enum.ImgSize;

public class PokemonAppearance extends Fragment {
    private String ID;
    private ImageView imgFront;
    private ImageView imgBack;
    private TextView txtSprite;
    private TextView txtFemale;
    private TextView txtTap;
    private ImageView imgFront2;
    private ImageView imgBack2;
    private Pokemon pokemon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon_appearance, container, false);
        pokemon = getArguments().getParcelable(ActivityMain.POKEMON_DATA);

        ImageView imgArt = (ImageView) view.findViewById(R.id.imgSugimoriArt);
        LinearLayout boxSprite = (LinearLayout) view.findViewById(R.id.boxSprite);
        LinearLayout boxFemale = (LinearLayout) view.findViewById(R.id.boxFemale);

        ID = pokemon.ID();
        imgFront = (ImageView) view.findViewById(R.id.imgFront);
        imgBack = (ImageView) view.findViewById(R.id.imgBack);
        txtSprite = (TextView) view.findViewById(R.id.txtSprite);
        txtFemale = (TextView) view.findViewById(R.id.txtSpriteFemale);
        txtTap = (TextView) view.findViewById(R.id.txtTap);
        imgFront2 = (ImageView) view.findViewById(R.id.imgFront2);
        imgBack2 = (ImageView) view.findViewById(R.id.imgBack2);

        Database.setImage(imgArt, "art/sa_" + ID + ".png", ImgSize.LARGE);
        Database.setImage(imgFront, "sprites/normal/front/nf_" + ID + ".png", ImgSize.SMALL);
        Database.setImage(imgBack, "sprites/normal/back/nb_" + ID + ".png", ImgSize.SMALL);

        if (pokemon.hasGenderDifferences()) {
            txtSprite.setText("Male");
            txtFemale.setVisibility(View.VISIBLE);
            boxFemale.setVisibility(View.VISIBLE);
            Database.setImage(imgFront2, "sprites/normal/front/nf_" + ID + "_female.png", ImgSize.SMALL);
            Database.setImage(imgBack2, "sprites/normal/back/nb_" + ID + "_female.png", ImgSize.SMALL);
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

    private void changeToShiny() {
        if (pokemon.hasGenderDifferences()) {
            txtSprite.setText("Male Shiny");
            txtFemale.setText("Female Shiny");
            Database.setImage(imgFront2, "sprites/shiny/front/sf_" + ID + "_female.png", ImgSize.SMALL);
            Database.setImage(imgBack2, "sprites/shiny/back/sb_" + ID + "_female.png", ImgSize.SMALL);
        } else txtSprite.setText("Shiny");
        txtTap.setText("tap to see normal sprite");
        Database.setImage(imgFront, "sprites/shiny/front/sf_" + ID + ".png", ImgSize.SMALL);
        Database.setImage(imgBack, "sprites/shiny/back/sb_" + ID + ".png", ImgSize.SMALL);
    }

    private void changeToNormal() {
        if (pokemon.hasGenderDifferences()) {
            txtSprite.setText("Male");
            txtFemale.setText("Female");
            Database.setImage(imgFront2, "sprites/normal/front/nf_" + ID + "_female.png", ImgSize.SMALL);
            Database.setImage(imgBack2, "sprites/normal/back/nb_" + ID + "_female.png", ImgSize.SMALL);
        } else txtSprite.setText("Normal");
        txtTap.setText("tap to see shiny sprite");
        Database.setImage(imgFront, "sprites/normal/front/nf_" + ID + ".png", ImgSize.SMALL);
        Database.setImage(imgBack, "sprites/normal/back/nb_" + ID + ".png", ImgSize.SMALL);
    }
}