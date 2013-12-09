package com.radhi.Pokedex.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.radhi.Pokedex.R;
import com.radhi.Pokedex.activity.Main;
import com.radhi.Pokedex.adapter.ListAbilityAdapter;
import com.radhi.Pokedex.adapter.ListDexAdapter;
import com.radhi.Pokedex.adapter.ListEfficacyAdapter;
import com.radhi.Pokedex.adapter.ListStatAdapter;
import com.radhi.Pokedex.other.*;
import com.radhi.Pokedex.other.Enum;
import com.radhi.Pokedex.other.Enum.ImgSize;

public class PokemonData2 extends Fragment {
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
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pokemon_data_tab, container, false);
        ID = getArguments().getString(Main.POKEMON_ID_2);
        DB = new Database(activity);

        setPokemonAppearance();
        setPokemonData();
        setPokemonStat();

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = getActivity();
    }

    private void setPokemonAppearance() {
        ImageView imgArt = (ImageView) view.findViewById(R.id.imgSugimoriArt);
        LinearLayout boxSprite = (LinearLayout) view.findViewById(R.id.boxSprite);
        LinearLayout boxFemale = (LinearLayout) view.findViewById(R.id.boxFemale);

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
    }

    private void setPokemonData() {
        ListDexAdapter listDexAdapter = new ListDexAdapter(activity, DB.getPokedexNumber(ID));
        ListAbilityAdapter listAbilityAdapter = new ListAbilityAdapter(activity, DB.getPokemonAbility(ID));

        final LinearLayout boxNationalID = (LinearLayout) view.findViewById(R.id.boxNationalID);
        final LinearLayout boxPokedexNumber = (LinearLayout) view.findViewById(R.id.boxDexNumber);
        final TextView txtDescription = (TextView) view.findViewById(R.id.txtPokemonDescription);

        LinearLayout boxAbility = (LinearLayout) view.findViewById(R.id.boxAbility);
        TextView txtNationalID = (TextView) view.findViewById(R.id.txtNationalID);
        TextView txtJapaneseName = (TextView) view.findViewById(R.id.txtJapaneseName);
        TextView txtSpecies = (TextView) view.findViewById(R.id.txtSpecies);
        TextView txtHabitat = (TextView) view.findViewById(R.id.txtHabitat);
        TextView txtType1 = (TextView) view.findViewById(R.id.txtPokemonType1);
        TextView txtType2 = (TextView) view.findViewById(R.id.txtPokemonType2);
        TextView txtHeight = (TextView) view.findViewById(R.id.txtHeight);
        TextView txtWeight = (TextView) view.findViewById(R.id.txtWeight);
        TextView txtShape = (TextView) view.findViewById(R.id.txtShape);
        TextView txtGender = (TextView) view.findViewById(R.id.txtGender);
        TextView txtEgg = (TextView) view.findViewById(R.id.txtEgg);
        TextView txtHatch = (TextView) view.findViewById(R.id.txtHatch);
        TextView txtGrowth = (TextView) view.findViewById(R.id.txtGrowth);
        TextView txtCapture = (TextView) view.findViewById(R.id.txtCapture);

        for (int n = 0; n < listDexAdapter.getCount(); n++)
            boxPokedexNumber.addView(listDexAdapter.getView(n, null, boxPokedexNumber));
        boxPokedexNumber.setVisibility(View.GONE);

        txtNationalID.setText(ID);
        txtDescription.setText(Html.fromHtml(DB.getPokemonDescription(ID)));
        txtJapaneseName.setText(DB.getPokemonName(ID, Enum.NameType.JAPANESE) + " (" +
                DB.getPokemonName(ID, Enum.NameType.ROMAJI) + ")");
        txtSpecies.setText(DB.getPokemonName(ID, Enum.NameType.SPECIES) + " Pokémon");
        txtHabitat.setText(DB.getPokemonHabitat(ID));
        txtHeight.setText(DB.getPokemonHeight(ID));
        txtWeight.setText(DB.getPokemonWeight(ID));
        txtShape.setText(DB.getPokemonShape(ID));
        txtGender.setText(DB.getPokemonGenderRate(ID));
        txtEgg.setText(DB.getPokemonEgg(ID));
        txtHatch.setText(DB.getPokemonHatchCounter(ID));
        txtGrowth.setText(DB.getPokemonGrowthRate(ID));
        txtCapture.setText(DB.getPokemonCaptureRate(ID));

        for (int n = 0; n < listAbilityAdapter.getCount(); n++)
            boxAbility.addView(listAbilityAdapter.getView(n, null, null));

        String[] type = DB.getPokemonType(ID);
        DB.getTypeName(txtType1,Integer.valueOf(type[0]));
        if (type.length > 1) DB.getTypeName(txtType2,Integer.valueOf(type[1]));

        boxNationalID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boxNationalID.setVisibility(View.GONE);
                boxPokedexNumber.setVisibility(View.VISIBLE);
            }
        });

        boxPokedexNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boxPokedexNumber.setVisibility(View.GONE);
                boxNationalID.setVisibility(View.VISIBLE);
            }
        });

        txtDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDescription.setText(Html.fromHtml(DB.getPokemonDescription(ID)));
            }
        });
    }

    private void setPokemonStat() {
        final LinearLayout boxEfficacy = (LinearLayout) view.findViewById(R.id.boxTypeEfficacy);
        final LinearLayout boxOffence = (LinearLayout) view.findViewById(R.id.boxOffence);
        final LinearLayout boxDefence = (LinearLayout) view.findViewById(R.id.boxDefence);
        final TextView lblEfficacy = (TextView) view.findViewById(R.id.lblTypeEfficacy);
        final TextView lblTapEfficacy = (TextView) view.findViewById(R.id.lblTapEfficacy);
        TextView txtBaseEXP = (TextView) view.findViewById(R.id.txtBaseEXP);
        TextView txtBaseEffort = (TextView) view.findViewById(R.id.txtBaseEffort);
        TextView txtBaseHappines = (TextView) view.findViewById(R.id.txtBaseHappiness);
        LinearLayout boxStat = (LinearLayout) view.findViewById(R.id.boxStat);
        LinearLayout boxOffenceStrong = (LinearLayout) view.findViewById(R.id.boxOffenceStrong);
        LinearLayout boxOffenceImmune = (LinearLayout) view.findViewById(R.id.boxOffenceImmune);
        LinearLayout boxOffenceWeak = (LinearLayout) view.findViewById(R.id.boxOffenceWeak);
        LinearLayout boxDefenceStrong = (LinearLayout) view.findViewById(R.id.boxDefenceStrong);
        LinearLayout boxDefenceImmune = (LinearLayout) view.findViewById(R.id.boxDefenceImmune);
        LinearLayout boxDefenceWeak = (LinearLayout) view.findViewById(R.id.boxDefenceWeak);

        txtBaseEXP.setText(DB.getPokemonBaseEXP(ID));
        txtBaseEffort.setText(DB.getPokemonBaseEffort(ID));
        txtBaseHappines.setText(DB.getPokemonBaseHappiness(ID));

        Double[][] DefenceStrong, DefenceWeak, DefenceImmune;
        Double[][] OffenceStrong, OffenceWeak, OffenceImmune;
        String[] type = DB.getPokemonType(ID);
        DefenceStrong = DB.getPokemonDefence(Enum.Effectiveness.NOT_EFFECTIVE, type);
        DefenceWeak = DB.getPokemonDefence(Enum.Effectiveness.SUPER_EFFECTIVE, type);
        DefenceImmune = DB.getPokemonDefence(Enum.Effectiveness.IMMUNE, type);
        OffenceStrong = DB.getPokemonOffence(Enum.Effectiveness.SUPER_EFFECTIVE, type);
        OffenceWeak = DB.getPokemonOffence(Enum.Effectiveness.NOT_EFFECTIVE, type);
        OffenceImmune = DB.getPokemonOffence(Enum.Effectiveness.IMMUNE, type);

        fillBoxEffifacy(boxDefenceStrong,DefenceStrong);
        fillBoxEffifacy(boxDefenceImmune,DefenceImmune);
        fillBoxEffifacy(boxDefenceWeak,DefenceWeak);
        fillBoxEffifacy(boxOffenceStrong,OffenceStrong);
        fillBoxEffifacy(boxOffenceImmune,OffenceImmune);
        fillBoxEffifacy(boxOffenceWeak,OffenceWeak);

        boxDefence.setVisibility(View.GONE);

        ListStatAdapter listStatAdapter = new ListStatAdapter(activity, DB.getPokemonStat(ID));
        for (int n = 0; n < listStatAdapter.getCount(); n++)
            boxStat.addView(listStatAdapter.getView(n, null, boxStat));

        boxEfficacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boxOffence.isShown()) {
                    lblEfficacy.setText("Defence");
                    lblTapEfficacy.setText("tap to see offence");
                    boxOffence.setVisibility(View.GONE);
                    boxDefence.setVisibility(View.VISIBLE);
                } else {
                    lblEfficacy.setText("Offence");
                    lblTapEfficacy.setText("tap to see defence");
                    boxDefence.setVisibility(View.GONE);
                    boxOffence.setVisibility(View.VISIBLE);
                }
            }
        });
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

    private void fillBoxEffifacy(LinearLayout box, Double[][] data) {
        ListEfficacyAdapter adapter = new ListEfficacyAdapter(activity,data);
        for (int n = 0; n < adapter.getCount(); n++)
            box.addView(adapter.getView(n,null,box));
    }
}