package com.radhi.Pokedex.other;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.TextView;
import com.radhi.Pokedex.R;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import com.radhi.Pokedex.other.Enum.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class Database extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "pokedex_data";
    private static final int DATABASE_VERSION = 1;
    private static final String UNKNOWN = "-";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private Cursor getCursor(String[] column, String table, String sel, String order) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(table);

        return qb.query(getReadableDatabase(), column, sel,
                null, null, null, order);
    }

    private Cursor getCursor(String[] column, String table, String sel, String group, String order) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(table);

        return qb.query(getReadableDatabase(), column, sel,
                null, group, null, order);
    }

    public String[] getPokemonID(String nm) {
        Cursor c = getCursor(new String[] {"pokemon_id"},
                "pokemon_name","eng_name LIKE '%" + nm + "%'", null);

        String[] ID = new String[c.getCount()];
        if (c.moveToFirst()) for (int n = 0; n < c.getCount(); n++) {
            ID[n] = c.getString(0);
            c.moveToNext();
        }

        return ID;
    }

    public String getPokemonName(String id, NameType nameType) {
        Cursor c = getCursor(new String[] {nameType.getNameType()},
                "pokemon_name","pokemon_id = " + id, null);

        if (c.moveToFirst()) return c.getString(0);
        else return UNKNOWN;
    }

    public String getPokemonHabitat(String id) {
        Cursor c = getCursor(new String[] {"pokemon_habitat.name"},
                "pokemon_species LEFT JOIN pokemon_habitat " +
                "ON pokemon_species.habitat_id = pokemon_habitat.pokemon_habitat_id",
                "pokemon_species.id = " + id,null);

        if (c.moveToFirst()) return c.getString(0);
        else return UNKNOWN;
    }

    public String[] getPokemonType(String id) {
        Cursor c = getCursor(new String[] {"type_id"},
                "pokemon_type","pokemon_id = " + id,"slot");

        String[] type;
        if (c.moveToFirst()) {
            type = new String[2];

            type[0] = c.getString(0);

            if(c.moveToNext()) type[1] = c.getString(0);
            else type[1] = "0";
        } else type = new String[] {"0","0"};

        return type;
    }

    public Boolean hasFemaleForm(String id) {
        Cursor c = getCursor(new String[] {"has_gender_differences"},
                "pokemon_species","id = " + id, null);

        return c.moveToFirst() && (c.getString(0).equals("1"));
    }

    public void setImageFromAssets(ImageView imageView, String imgLocation, ImgSize imgSize) {
        try {
            InputStream ims = new FileInputStream(
                    Environment.getExternalStorageDirectory().toString() + "/Pokedex Image/" + imgLocation);
            imageView.setImageDrawable(Drawable.createFromStream(ims, null));
        } catch (IOException e) {
            e.printStackTrace();
            if (imgSize == ImgSize.LARGE) imageView.setImageResource(R.drawable.unknown_large);
            else imageView.setImageResource(R.drawable.unknown_small);
        }
    }

    public String getPokemonDescription(String id) {
        Cursor c = getCursor(new String[] {"version.name","pokemon_description.description"},
                "pokemon_description LEFT JOIN version ON pokemon_description.version_id = version.version_id",
                "pokemon_description.pokemon_id = " + id,
                "pokemon_description.version_id");

        if (c.moveToFirst()) {
            int Rnd = new Random().nextInt(c.getCount());
            for (int n = 0; n < Rnd; n++) c.moveToNext();
            return "<i>" + c.getString(1) + "</i><br/><b>(Pokémon " + c.getString(0) + ")</b>";
        } else return UNKNOWN;
    }

    public String[][] getPokedexNumber(String id) {
        Cursor c = getCursor(new String[] {"pokedex.name","pokemon_dex_number.pokedex_number"},
                "pokemon_dex_number LEFT JOIN pokedex ON pokemon_dex_number.pokedex_id = pokedex.pokedex_id",
                "pokemon_dex_number.species_id = " + id,
                "pokemon_dex_number.pokedex_id");

        String[][] dexData;
        if (c.moveToFirst()) {
            dexData = new String[c.getCount()][2];
            for (int x = 0; x < c.getCount(); x++) {
                for (int y = 0; y < 2; y++)
                    dexData[x][y] = c.getString(y);
                c.moveToNext();
            }
        } else dexData = new String[][] {{UNKNOWN,UNKNOWN}};

        return dexData;
    }

    public String getPokemonHeight(String id) {
        Cursor c = getCursor(new String[] {"height"},
                "pokemon","id = " + id, null);

        if (c.moveToFirst()) {
            double cm = Double.parseDouble(c.getString(0)) * 10;
            int ft = (int)(cm/30.48);
            double inch = (cm - (ft * 30.48)) / 2.54;

            String metric;
            if (cm < 100) metric = String.format("%s", cm) + "cm";
            else metric = String.format("%s", cm / 100) + "m";

            return metric + " (" + String.valueOf(ft) + "'" + String.format("%.1f",inch) + "'')";
        } else return UNKNOWN;
    }

    public String getPokemonWeight(String id) {
        Cursor c = getCursor(new String[] {"weight"},
                "pokemon","id = " + id, null);

        if (c.moveToFirst()) {
            double kg = Double.parseDouble(c.getString(0)) / 10;
            double lb = kg * 2.20462262;
            return String.format("%s", kg) + "kg (" + String.format("%.2f", lb) + "lbs)";
        } else return UNKNOWN;
    }

    public String getPokemonShape(String id) {
        Cursor c = getCursor(new String[] {"pokemon_shape.name", "pokemon_shape.awesome_name"},
                "pokemon_species LEFT JOIN pokemon_shape ON " +
                "pokemon_species.shape_id = pokemon_shape.pokemon_shape_id",
                "pokemon_species.id = " + id, null);

        if (c.moveToFirst()) return c.getString(0) + " (" + c.getString(1) + ")";
        else return UNKNOWN;
    }

    public String getPokemonGenderRate(String id) {
        Cursor c = getCursor(new String[] {"gender_rate"},
                "pokemon_species","id = " + id, null);

        if (c.moveToFirst()) {
            double female = Double.parseDouble(c.getString(0));
            double male = 8 - female;

            if (female < 0) return "Genderless";
            else return String.format("%s",male / 8 * 100) + "% ♂, "
                    + String.format("%s",female / 8 * 100) + "% ♀";
        } else return UNKNOWN;
    }

    public String getPokemonEgg(String id) {
        Cursor c = getCursor(new String[] {"egg.name"},
                "pokemon_egg_group LEFT JOIN egg ON pokemon_egg_group.egg_group_id = egg.egg_group_id",
                "pokemon_egg_group.pokemon_id = " + id,null);

        if (c.moveToFirst()) {
            String egg = c.getString(0);
            while (c.moveToNext()) egg += ", " + c.getString(0);
            return egg;
        } else return UNKNOWN;
    }

    public String getPokemonHatchCounter(String id) {
        Cursor c = getCursor(new String[] {"hatch_counter"},
                "pokemon_species","id = " + id, null);

        if (c.moveToFirst()) return c.getString(0);
        else return UNKNOWN;
    }

    public String getPokemonGrowthRate(String id) {
        Cursor c = getCursor(new String[] {"growth_rate.name"},
                "pokemon_species LEFT JOIN growth_rate ON " +
                "pokemon_species.growth_rate_id = growth_rate.growth_rate_id",
                "pokemon_species.id = " + id,null);

        if (c.moveToFirst()) return c.getString(0);
        else return UNKNOWN;
    }

    public String getPokemonCaptureRate(String id) {
        Cursor c = getCursor(new String[] {"capture_rate"},
                "pokemon_species","id = " + id,null);

        if (c.moveToFirst()) {
            double percent = Double.parseDouble(c.getString(0)) / 765 * 100;
            return c.getString(0) + " (" + String.format("%.2f",percent) + "% with PokéBall, full HP)";
        } else return UNKNOWN;
    }

    public String[][] getPokemonAbility(String id) {
        Cursor c = getCursor(
                new String[] {"ability.name", "pokemon_ability.is_hidden", "ability.description"},
                "pokemon_ability LEFT JOIN ability ON pokemon_ability.ability_id = ability.ability_id",
                "pokemon_ability.pokemon_id = " + id,null);

        String[][] ability;
        if (c.moveToFirst()) {
            ability = new String[c.getCount()][3];
            for (int x = 0; x < c.getCount(); x++) {
                for (int y = 0; y < 3; y++)
                    ability[x][y] = c.getString(y);
                c.moveToNext();
            }
        } else ability = new String[][] {{UNKNOWN,"0",UNKNOWN}};

        return ability;
    }

    public String getPokemonBaseEXP(String id) {
        Cursor c = getCursor(new String[] {"base_experience"},
                "pokemon","id = " + id, null);

        if (c.moveToFirst()) return c.getString(0);
        else return UNKNOWN;
    }

    public String getPokemonBaseEffort(String id) {
        Cursor c = getCursor(new String[] {"stat.name", "pokemon_stat.effort"},
                "pokemon_stat LEFT JOIN stat ON pokemon_stat.stat_id = stat.stat_id",
                "pokemon_stat.pokemon_id = " + id,null);

        if (c.moveToFirst()) {
            String effort = "";
            for (int n = 0; n < c.getCount(); n++) {
                if (!c.getString(1).equals("0")) {
                    if (!effort.equals("")) effort += ", ";
                    effort += c.getString(1) + " " + c.getString(0);
                }
                c.moveToNext();
            }
            return effort;
        } else return UNKNOWN;
    }

    public String getPokemonBaseHappiness(String id) {
        Cursor c = getCursor(new String[] {"base_happiness"},
                "pokemon_species","id = " + id,null);

        if (c.moveToFirst()) return c.getString(0);
        else return UNKNOWN;
    }

    public String[][] getPokemonStat(String id) {
        Cursor c = getCursor(new String[] {"stat.name", "pokemon_stat.base_stat"},
                "pokemon_stat LEFT JOIN stat ON pokemon_stat.stat_id = stat.stat_id",
                "pokemon_stat.pokemon_id = " + id,
                "pokemon_stat.stat_id");

        String[][] stat;
        if (c.moveToFirst())
        {
            stat = new String[c.getCount()][3];
            for (int x = 0; x < c.getCount(); x++) {
                stat[x][0] = c.getString(0);
                stat[x][1] = c.getString(1);
                stat[x][2] = getPokemonMaxStat(id,stat[x][0],stat[x][1]);
                c.moveToNext();
            }
        } else stat = new String[][] {{UNKNOWN,"0","0"}};

        return stat;
    }

    private String getPokemonMaxStat(String id, String statName, String baseStat) {
        if (id.equals("292") && statName.equals("HP")) return "1";
        else {
            int base = Integer.parseInt(baseStat);
            int max;

            if (statName.equals("HP"))
                max = 31 + (2 * base) + (252/4) + 100 + 10;
            else
                max = (int)((31 + (2 * base) + (252/4) + 5) * 1.1);

            return String.valueOf(max);
        }
    }

    public Double[][] getPokemonDefence(Effectiveness effectiveness, String[] type) {
        int nType;
        String critEffect;
        Cursor c;

        if (type[1].equals("0")) nType = 1;
        else nType = 2;

        if (nType == 2) {
            if (effectiveness == Effectiveness.SUPER_EFFECTIVE) critEffect = "> 10000";
            else if (effectiveness == Effectiveness.IMMUNE) critEffect = "= 0";
            else critEffect = "> 0 AND tab1.damage_factor * tab2.damage_factor < 10000";

            c = getCursor(new String[] {"tab1.damage_type_id","tab1.damage_factor * tab2.damage_factor"},
                    "(SELECT damage_type_id, damage_factor " +
                    "FROM type_efficacy WHERE target_type_id = " + type[0] + ") as tab1 " +
                    "LEFT JOIN " +
                    "(SELECT damage_type_id, damage_factor " +
                    "FROM type_efficacy WHERE target_type_id = " + type[1] + ") as tab2 " +
                    "ON tab1.damage_type_id = tab2.damage_type_id",
                    "tab1.damage_factor * tab2.damage_factor " + critEffect,
                    "tab1.damage_type_id");

        } else {
            if (effectiveness == Effectiveness.SUPER_EFFECTIVE) critEffect = "> 100";
            else if (effectiveness == Effectiveness.IMMUNE) critEffect = "= 0";
            else critEffect = "> 0 AND damage_factor < 100";

            c = getCursor(new String[] {"damage_type_id","damage_factor"},
                    "type_efficacy", "target_type_id = " + type[0] + " AND " +
                    "damage_factor " + critEffect, "damage_type_id");
        }

        Double[][] result;
        if (c.moveToFirst()) {
            result = new Double[c.getCount()][2];
            for (int n = 0; n < c.getCount(); n++) {
                result[n][0] = Double.parseDouble(c.getString(0));
                result[n][1] = Double.parseDouble(c.getString(1)) / Math.pow(100,nType);
                c.moveToNext();
            }
        } else result = new Double[][]{{0d,1d}};

        return result;
    }

    public Double[][] getPokemonOffence(Effectiveness effectiveness, String[] type) {
        int nType;
        String critEffect;
        Cursor c;

        if (type[1].equals("0")) nType = 1;
        else nType = 2;

        if (nType == 2) {
            if (effectiveness == Effectiveness.SUPER_EFFECTIVE) critEffect = "> 10000";
            else if (effectiveness == Effectiveness.IMMUNE) critEffect = "= 0";
            else critEffect = "> 0 AND tab1.damage_factor * tab2.damage_factor < 10000";

            c = getCursor(new String[] {"tab1.target_type_id","tab1.damage_factor * tab2.damage_factor"},
                    "(SELECT target_type_id, damage_factor " +
                    "FROM type_efficacy WHERE damage_type_id = " + type[0] + ") as tab1 " +
                    "LEFT JOIN " +
                    "(SELECT target_type_id, damage_factor " +
                    "FROM type_efficacy WHERE damage_type_id = " + type[1] + ") as tab2 " +
                    "ON tab1.target_type_id = tab2.target_type_id",
                    "tab1.damage_factor * tab2.damage_factor " + critEffect,
                    "tab1.target_type_id");

        } else {
            if (effectiveness == Effectiveness.SUPER_EFFECTIVE) critEffect = "> 100";
            else if (effectiveness == Effectiveness.IMMUNE) critEffect = "= 0";
            else critEffect = "> 0 AND damage_factor < 100";

            c = getCursor(new String[] {"target_type_id","damage_factor"},
                    "type_efficacy", "damage_type_id = " + type[0] + " AND " +
                    "damage_factor " + critEffect, "target_type_id");
        }

        Double[][] result;
        if (c.moveToFirst()) {
            result = new Double[c.getCount()][2];
            for (int n = 0; n < c.getCount(); n++) {
                result[n][0] = Double.parseDouble(c.getString(0));
                result[n][1] = Double.parseDouble(c.getString(1)) / Math.pow(100,nType);
                c.moveToNext();
            }
        } else result = new Double[][]{{0d,1d}};

        return result;
    }

    public String[] getPokemonMoveVersion(String id) {
        Cursor c = getCursor(new String[] {"pokemon_move.version_group_id","version.name"},
                "pokemon_move LEFT JOIN version ON pokemon_move.version_group_id = version.version_id",
                "pokemon_move.pokemon_id = " + id,
                "pokemon_move.version_group_id",
                "pokemon_move.version_group_id");

        String[] version;
        if (c.moveToFirst()) {
            version = new String[c.getCount()];
            for (int n = 0; n < c.getCount(); n++) {
                version[n] = c.getString(0) + " - Pokémon " + c.getString(1);
                c.moveToNext();
            }
        } else version = new String[] {UNKNOWN};

        return version;
    }

    public String[] getPokemonMoveMethod(String id, String version) {
        Cursor c = getCursor(
                new String[] {"pokemon_move.pokemon_move_method_id","pokemon_move_method.name"},
                "pokemon_move LEFT JOIN pokemon_move_method ON " +
                "pokemon_move.pokemon_move_method_id = pokemon_move_method.pokemon_move_method_id",
                "pokemon_move.pokemon_id = " + id + " AND " +
                "pokemon_move.version_group_id = " + version,
                "pokemon_move.pokemon_move_method_id",
                "pokemon_move.pokemon_move_method_id");

        String[] moveMethod;
        if (c.moveToFirst()) {
            moveMethod = new String[c.getCount()];
            for (int n = 0; n < c.getCount(); n++) {
                moveMethod[n] = c.getString(0) + " - " + c.getString(1);
                c.moveToNext();
            }
        } else moveMethod = new String[] {UNKNOWN};

        return moveMethod;
    }

    public String[] getMove(String pokemonID, String version, String move_method) {
        String order;
        if (move_method.equals("1")) order = "level, [order]";
        else order = "move_id";

        Cursor c = getCursor(new String[] {"move_id"},"pokemon_move",
                "pokemon_id = " + pokemonID + " AND " +
                "version_group_id = " + version + " AND " +
                "pokemon_move_method_id = " + move_method, order);

        String[] move;
        if (c.moveToFirst()) {
            move = new String[c.getCount()];
            for (int n = 0; n < c.getCount(); n++) {
                move[n] = c.getString(0);
                c.moveToNext();
            }
        } else move = new String[] {UNKNOWN};

        return move;
    }

    public String getMoveName(String moveID) {
        Cursor c = getCursor(new String[] {"name"}, "move_name", "move_id = " + moveID, null);
        if (c.moveToFirst()) return c.getString(0);
        else return UNKNOWN;
    }

    public String getMoveDescription(String id) {
        Cursor c = getCursor(new String[] {"version.name","move_description.description"},
                "move_description LEFT JOIN version ON " +
                "move_description.version_group_id = version.version_id",
                "move_description.move_id = " + id,
                "move_description.version_group_id");

        if (c.moveToFirst()) {
            int Rnd = new Random().nextInt(c.getCount());
            for (int n = 0; n < Rnd; n++) c.moveToNext();
            return "<i>" + c.getString(1) + "</i><br/><b>(Pokémon " + c.getString(0) + ")</b>";
        } else return UNKNOWN;
    }

    public String getMoveCategory(String moveID) {
        Cursor c = getCursor(new String[] {"move_damage_class.name"},
                "move LEFT JOIN move_damage_class ON " +
                "move.damage_class_id = move_damage_class.move_damage_class_id",
                "move.move_id = " + moveID, null);
        if (c.moveToFirst()) return c.getString(0);
        else return UNKNOWN;
    }

    public String getMoveType(String moveID) {
        Cursor c = getCursor(new String[] {"type_id"}, "move", "move_id = " + moveID, null);
        if (c.moveToFirst()) return c.getString(0);
        else return UNKNOWN;
    }

    public String getMovePower(String moveID) {
        Cursor c = getCursor(new String[] {"power"}, "move", "move_id = " + moveID, null);
        if (c.moveToFirst()) return c.getString(0);
        else return UNKNOWN;
    }

    public String getMoveAccuracy(String moveID) {
        Cursor c = getCursor(new String[] {"power"}, "move", "move_id = " + moveID, null);
        if (c.moveToFirst()) return c.getString(0);
        else return UNKNOWN;
    }

    public String getMovePP(String moveID) {
        Cursor c = getCursor(new String[] {"pp"}, "move", "move_id = " + moveID, null);
        if (c.moveToFirst()) return c.getString(0);
        else return UNKNOWN;
    }

    public String getMoveAilment(String moveID) {
        Cursor c = getCursor(new String[] {"move_meta_ailment.name"},
                "move_meta LEFT JOIN move_meta_ailment ON " +
                "move_meta.meta_ailment_id = move_meta_ailment.move_meta_ailment_id",
                "move_meta.move_id = " + moveID, null);
        if (c.moveToFirst()) return c.getString(0);
        else return UNKNOWN;
    }

    public String getMoveTarget(String moveID) {
        Cursor c = getCursor(new String[] {"move_target.name"},
                "move LEFT JOIN move_target ON " +
                "move.target_id = move_target.move_target_id",
                "move.move_id = " + moveID, null);
        if (c.moveToFirst()) return c.getString(0);
        else return UNKNOWN;
    }

    public void getTypeName(TextView tv, int type_id) {
        String type_name;
        int bg_id;

        switch (type_id) {
            case 1 : type_name = "normal";
                bg_id = R.drawable.bg_normal;
                break;
            case 2 : type_name = "fighting";
                bg_id = R.drawable.bg_fighting;
                break;
            case 3 : type_name = "flying";
                bg_id = R.drawable.bg_flying;
                break;
            case 4 : type_name = "poison";
                bg_id = R.drawable.bg_poison;
                break;
            case 5 : type_name = "ground";
                bg_id = R.drawable.bg_ground;
                break;
            case 6 : type_name = "rock";
                bg_id = R.drawable.bg_rock;
                break;
            case 7 : type_name = "bug";
                bg_id = R.drawable.bg_bug;
                break;
            case 8 : type_name = "ghost";
                bg_id = R.drawable.bg_ghost;
                break;
            case 9 : type_name = "steel";
                bg_id = R.drawable.bg_steel;
                break;
            case 10 : type_name = "fire";
                bg_id = R.drawable.bg_fire;
                break;
            case 11 : type_name = "water";
                bg_id = R.drawable.bg_water;
                break;
            case 12 : type_name = "grass";
                bg_id = R.drawable.bg_grass;
                break;
            case 13 : type_name = "electric";
                bg_id = R.drawable.bg_electric;
                break;
            case 14 : type_name = "psychic";
                bg_id = R.drawable.bg_psychic;
                break;
            case 15 : type_name = "ice";
                bg_id = R.drawable.bg_ice;
                break;
            case 16 : type_name = "dragon";
                bg_id = R.drawable.bg_dragon;
                break;
            case 17 : type_name = "dark";
                bg_id = R.drawable.bg_dark;
                break;
            case 18 : type_name = "fairy";
                bg_id = R.drawable.bg_fairy;
                break;
            default : type_name = "";
                bg_id = R.drawable.bg_none;
                break;
        }

        tv.setText(type_name);
        tv.setBackgroundResource(bg_id);
    }
}