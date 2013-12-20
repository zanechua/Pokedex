package com.radhi.Pokedex.other;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.TextView;
import com.radhi.Pokedex.R;
import com.radhi.Pokedex.other.Enum.Effectiveness;
import com.radhi.Pokedex.other.Enum.ImgSize;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Database extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "pokedex_data";
    private static final int DATABASE_VERSION = 2;
    public static final String UNKNOWN = "-";
    public static final String SPLIT = "ǁ";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgradeVersion(2);
    }

    private Cursor getCursor(String query, String[] args) {
        return getReadableDatabase().rawQuery(query, args);
    }

    public String[] getPokemonList(String nm) {
        Cursor c = getCursor("" +
                "SELECT " +
                "nm.pokemon_id, nm.eng_name, " +
                "tp1.type_id, ifnull(tp2.type_id,'0') " +
                "FROM " +
                "pokemon_name as nm " +
                "LEFT JOIN " +
                "(SELECT pokemon_id, type_id FROM pokemon_type WHERE slot = 1) as tp1 " +
                "ON nm.pokemon_id = tp1.pokemon_id " +
                "LEFT JOIN " +
                "(SELECT pokemon_id, type_id FROM pokemon_type WHERE slot = 2) as tp2 " +
                "ON nm.pokemon_id = tp2.pokemon_id " +
                "WHERE nm.eng_name LIKE ? " +
                "ORDER BY nm.pokemon_id",
                new String[] {nm}
        );

        String[] ID = new String[c.getCount()];
        if (c.moveToFirst()) for (int n = 0; n < c.getCount(); n++) {
            ID[n] = c.getString(0) + SPLIT +
                    c.getString(1) + SPLIT +
                    c.getString(2) + SPLIT +
                    c.getString(3);
            c.moveToNext();
        }

        c.close();
        return ID;
    }

    public String getPokemonName(String id) {
        Cursor name = getCursor("" +
                "SELECT eng_name, jap_name, romaji_name, species " +
                "FROM pokemon_name " +
                "WHERE pokemon_id = ?",
                new String[] {id});

        String Name = UNKNOWN + SPLIT + UNKNOWN + SPLIT + UNKNOWN + SPLIT + UNKNOWN;
        if (name.moveToFirst())
            Name = name.getString(0) + SPLIT +
                    name.getString(1) + SPLIT +
                    name.getString(2) + SPLIT +
                    name.getString(3);

        name.close();
        return Name;
    }
      
    public String getPokemonHabitat(String id) {
        Cursor habitat = getCursor("" +
                "SELECT hb.name " +
                "FROM pokemon_species as sp LEFT JOIN pokemon_habitat as hb " +
                "ON sp.habitat_id = hb.pokemon_habitat_id " +
                "WHERE sp.id = ?",
                new String[] {id});

        String Habitat = UNKNOWN;
        if (habitat.moveToFirst()) Habitat = habitat.getString(0);

        habitat.close();
        return Habitat;
    }

    public String getPokemonType(String id) {
        Cursor type = getCursor("" +
                "SELECT type_id " +
                "FROM pokemon_type " +
                "WHERE pokemon_id = ? " +
                "ORDER BY slot",
                new String[] {id});

        String Type = "0" + SPLIT + "0";
        if (type.moveToFirst()) {
            Type = type.getString(0) + SPLIT;

            if(type.moveToNext()) Type += type.getString(0);
            else Type += "0";
        }

        type.close();
        return Type;
    }

    public String getPokemonHWE(String id) {
        Cursor hwe = getCursor("" +
                "SELECT height, weight, base_experience " +
                "FROM pokemon " +
                "WHERE id = ?",
                new String[] {id});

        String HWE = "-1" + SPLIT + "-1" + SPLIT + UNKNOWN;
        if (hwe.moveToFirst())
            HWE = hwe.getString(0) + SPLIT +
                    hwe.getString(1) + SPLIT +
                    hwe.getString(2);

        hwe.close();
        return HWE;
    }

    public String getPokemonShape(String id) {
        Cursor shape = getCursor("" +
                "SELECT pokemon_shape.name, pokemon_shape.awesome_name " +
                "FROM pokemon_species LEFT JOIN pokemon_shape " +
                "ON pokemon_species.shape_id = pokemon_shape.pokemon_shape_id " +
                "WHERE pokemon_species.id = ?",new String[] {id});

        String Shape = UNKNOWN;
        if (shape.moveToFirst())
            Shape = shape.getString(0) + " (" +
                    shape.getString(1) + ")";

        shape.close();
        return Shape;
    }

    public String getPokemonGrowthRate(String id) {
        Cursor growth = getCursor("" +
                "SELECT growth_rate.name " +
                "FROM pokemon_species LEFT JOIN growth_rate " +
                "ON pokemon_species.growth_rate_id = growth_rate.growth_rate_id " +
                "WHERE pokemon_species.id = ?", new String[] {id});

        String Growth = UNKNOWN;
        if (growth.moveToFirst()) Growth = growth.getString(0);

        growth.close();
        return Growth;
    }

    public String getPokemonGCHHGF(String id) {
        Cursor gchhgf = getCursor("" +
                "SELECT " +
                "gender_rate, capture_rate, base_happiness, hatch_counter, " +
                "has_gender_differences, forms_switchable " +
                "FROM pokemon_species " +
                "WHERE id = ?", new String[] {id});

        String GCHHGF = "-2" + SPLIT +
                "-1" + SPLIT +
                UNKNOWN + SPLIT +
                UNKNOWN + SPLIT +
                UNKNOWN + SPLIT +
                UNKNOWN;
        if (gchhgf.moveToFirst())
            GCHHGF = gchhgf.getString(0) + SPLIT +
                    gchhgf.getString(1) + SPLIT +
                    gchhgf.getString(2) + SPLIT +
                    gchhgf.getString(3) + SPLIT +
                    gchhgf.getString(4) + SPLIT +
                    gchhgf.getString(5);

        gchhgf.close();
        return GCHHGF;
    }

    public String getPokemonEgg(String id) {
        Cursor egg = getCursor("" +
                "SELECT egg.name " +
                "FROM pokemon_egg_group as pegg LEFT JOIN egg " +
                "ON pegg.egg_group_id = egg.egg_group_id " +
                "WHERE pegg.pokemon_id = ?",new String[] {id});

        String Egg = UNKNOWN;
        if (egg.moveToFirst()) {
            Egg = egg.getString(0);
            while (egg.moveToNext()) Egg += ", " + egg.getString(0);
        }

        egg.close();
        return Egg;
    }

    public String getPokemonBaseEffort(String id) {
        Cursor e = getCursor("" +
                "SELECT stat.name, pstat.effort " +
                "FROM pokemon_stat as pstat LEFT JOIN stat " +
                "ON pstat.stat_id = stat.stat_id " +
                "WHERE pstat.pokemon_id = ?", new String[] {id});

        String Effort = UNKNOWN;
        if (e.moveToFirst()) {
            Effort = "";
            for (int n = 0; n < e.getCount(); n++) {
                if (!e.getString(1).equals("0")) {
                    Effort += (Effort.equals("") ? "" : ", ");
                    Effort += e.getString(1) + " " + e.getString(0);
                }
                e.moveToNext();
            }
        }

        e.close();
        return Effort;
    }

    public String[] getPokedexNumber(String id) {
        Cursor c = getCursor("" +
                "SELECT dex.name, dnum.pokedex_number " +
                "FROM pokemon_dex_number as dnum LEFT JOIN pokedex as dex " +
                "ON dnum.pokedex_id = dex.pokedex_id " +
                "WHERE dnum.species_id = ? " +
                "ORDER BY dnum.pokedex_id", new String[] {id});

        String[] dexData;
        if (c.moveToFirst()) {
            dexData = new String[c.getCount()];
            for (int n = 0; n < c.getCount(); n++) {
                dexData[n] = c.getString(0) + SPLIT + c.getString(1);
                c.moveToNext();
            }
        } else dexData = new String[] {UNKNOWN + SPLIT + UNKNOWN};

        c.close();
        return dexData;
    }

    public String[] getPokemonDescription(String id) {
        Cursor c = getCursor("" +
                "SELECT ver.name, des.description " +
                "FROM pokemon_description as des LEFT JOIN version as ver " +
                "ON des.version_id = ver.version_id " +
                "WHERE des.pokemon_id = ? " +
                "ORDER BY des.version_id",new String[] {id});

        String[] Desc;
        if (c.moveToFirst()) {
            Desc = new String[c.getCount()];
            for (int n = 0; n < c.getCount(); n++) {
                Desc[n] = c.getString(0) + SPLIT + c.getString(1);
                c.moveToNext();
            }
        } else Desc = new String[] {UNKNOWN + SPLIT + UNKNOWN};

        c.close();
        return Desc;
    }

    public String[] getPokemonAbility(String id) {
        Cursor c = getCursor("" +
                "SELECT ability.name, pokemon_ability.is_hidden, ability.description " +
                "FROM pokemon_ability LEFT JOIN ability " +
                "ON pokemon_ability.ability_id = ability.ability_id " +
                "WHERE pokemon_ability.pokemon_id = ?",new String[] {id});

        String[] Ability;
        if (c.moveToFirst()) {
            Ability = new String[c.getCount()];
            for (int n = 0; n < c.getCount(); n++) {
                    Ability[n] = c.getString(0) + SPLIT +
                            c.getString(1) + SPLIT +
                            c.getString(2);

                c.moveToNext();
            }
        } else Ability = new String[] {UNKNOWN + SPLIT + UNKNOWN + SPLIT + UNKNOWN};

        c.close();
        return Ability;
    }

    public String[] getPokemonStat(String id) {
        Cursor c = getCursor("" +
                "SELECT stat.name, pokemon_stat.base_stat " +
                "FROM pokemon_stat LEFT JOIN stat ON pokemon_stat.stat_id = stat.stat_id " +
                "WHERE pokemon_stat.pokemon_id = ? " +
                "ORDER BY pokemon_stat.stat_id", new String[] {id});

        String[] stat;
        if (c.moveToFirst())
        {
            stat = new String[c.getCount()];
            for (int n = 0; n < c.getCount(); n++) {
                stat[n] = c.getString(0) + SPLIT + c.getString(1) + SPLIT +
                        getPokemonMaxStat(id,c.getString(0),c.getString(1));
                c.moveToNext();
            }
        } else stat = new String[] {UNKNOWN + SPLIT + "0" + SPLIT + "0"};

        return stat;
    }

    public String[] getPokemonEfficacy(String type1, String type2, Effectiveness eff) {
        Integer normal;
        if (type2.equals("0")) normal = 100;
        else normal = 10000;

        String criteria;
        if (eff == Effectiveness.SUPER_EFFECTIVE) criteria = "> " + String.valueOf(normal);
        else if (eff == Effectiveness.IMMUNE) criteria = "= 0";
        else criteria = "BETWEEN 1 AND " + String.valueOf(normal-1);

        String Query = String.format("" +
                "SELECT tab1.damage_type_id, tab1.damage_factor * IFNULL(tab2.damage_factor,'1') " +
                "FROM " +
                "(SELECT damage_type_id, damage_factor FROM type_efficacy WHERE target_type_id = %1$s) as tab1 " +
                "LEFT JOIN " +
                "(SELECT damage_type_id, damage_factor FROM type_efficacy WHERE target_type_id = %2$s) as tab2 " +
                "ON tab1.damage_type_id = tab2.damage_type_id " +
                "WHERE tab1.damage_factor * ifNULL(tab2.damage_factor,'1') %3$s " +
                "ORDER BY tab1.damage_type_id",type1,type2,criteria);

        Cursor c = getCursor(Query, null);

        String[] efficacy;
        if (c.moveToFirst()) {
            efficacy = new String[c.getCount()];
            Double val;
            for (int n = 0; n < c.getCount(); n++) {
                val = Double.valueOf(c.getString(1)) / normal;

                efficacy[n] = c.getString(0) + SPLIT +
                        (val > 1 || val == 0 ? String.format("%.0f",val) : String.valueOf(val));
                c.moveToNext();
            }
        } else efficacy = new String[] {"0" + SPLIT + "1"};

        c.close();
        return efficacy;
    }

    public String[] getPokemonMoveVersion(String id) {
        Cursor c = getCursor("" +
                "SELECT pokemon_move.version_group_id, version.name " +
                "FROM pokemon_move LEFT JOIN version ON pokemon_move.version_group_id = version.version_id " +
                "WHERE pokemon_move.pokemon_id = ? " +
                "GROUP BY pokemon_move.version_group_id " +
                "ORDER BY pokemon_move.version_group_id", new String[] {id});

        String[] version;
        if (c.moveToFirst()) {
            version = new String[c.getCount()];
            for (int n = 0; n < c.getCount(); n++) {
                version[n] = c.getString(0) + SPLIT + c.getString(1);
                c.moveToNext();
            }
        } else version = new String[] {UNKNOWN + SPLIT + UNKNOWN};

        c.close();
        return version;
    }

    public String[] getPokemonMoveMethod(String id) {
        Cursor c = getCursor("" +
                "SELECT pmove.pokemon_move_method_id, mmeth.name " +
                "FROM pokemon_move as pmove LEFT JOIN pokemon_move_method as mmeth " +
                "ON pmove.pokemon_move_method_id = mmeth.pokemon_move_method_id " +
                "WHERE pmove.pokemon_id = ? " +
                "GROUP BY pmove.pokemon_move_method_id " +
                "ORDER BY pmove.pokemon_move_method_id", new String[] {id});

        String[] moveMethod;
        if (c.moveToFirst()) {
            moveMethod = new String[c.getCount()];
            for (int n = 0; n < c.getCount(); n++) {
                moveMethod[n] = c.getString(0) + SPLIT + c.getString(1);
                c.moveToNext();
            }
        } else moveMethod = new String[] {UNKNOWN + SPLIT + UNKNOWN};

        c.close();
        return moveMethod;
    }

    public String[] getPokemonMoveList(String id, String version, String method) {
        Cursor c = getCursor("" +
                "SELECT m.move_id, nm.name, m.level, mv.type_id " +
                "FROM pokemon_move as m LEFT JOIN move_name as nm " +
                "ON m.move_id = nm.move_id LEFT JOIN move as mv " +
                "ON m.move_id = mv.move_id " +
                "WHERE pokemon_id = ? AND " +
                "version_group_id = ? AND " +
                "pokemon_move_method_id = ? " +
                "ORDER BY m.level, m.[order]",
                new String[] {id, version, method});

        String[] moveList = new String[c.getCount()];
        if (c.moveToFirst()) {
            for (int n = 0; n < c.getCount(); n++) {
                moveList[n] = c.getString(0) + SPLIT +
                                c.getString(1) + SPLIT +
                                c.getString(2) + SPLIT +
                                c.getString(3);
                c.moveToNext();
            }
        }

        c.close();
        return moveList;
    }

    public String getMoveData(String moveId) {
        Cursor c = getCursor("" +
                "SELECT mn.name, mv.generation_id, mv.type_id, " +
                "mv.power, mv.pp, mv.accuracy, mt.name, md.name " +
                "FROM move as mv LEFT JOIN move_name as mn " +
                "ON mv.move_id = mn.move_id " +
                "LEFT JOIN move_target as mt " +
                "ON mv.target_id = mt.move_target_id " +
                "LEFT JOIN move_damage_class as md " +
                "ON mv.damage_class_id = md.move_damage_class_id " +
                "WHERE mv.move_id = ?",
                new String[] {moveId});

        String moveData = UNKNOWN + SPLIT + UNKNOWN + SPLIT +
                UNKNOWN + SPLIT + UNKNOWN + SPLIT +
                UNKNOWN + SPLIT + UNKNOWN + SPLIT +
                UNKNOWN + SPLIT + UNKNOWN + SPLIT;
        if (c.moveToFirst()) {
            moveData = c.getString(0) + SPLIT +
                    c.getString(1) + SPLIT +
                    c.getString(2) + SPLIT +
                    c.getString(3) + SPLIT +
                    c.getString(4) + SPLIT +
                    c.getString(5) + SPLIT +
                    c.getString(6) + SPLIT +
                    c.getString(7);
        }

        c.close();
        return moveData;
    }

    public String getMoveAilment(String moveId) {
        Cursor c = getCursor("" +
                "SELECT ma.name " +
                "FROM move_meta as mm " +
                "LEFT JOIN move_meta_ailment as ma " +
                "on mm.meta_ailment_id = ma.move_meta_ailment_id " +
                "WHERE move_id = ?",
                new String[] {moveId});

        String ailment = UNKNOWN;
        if (c.moveToFirst()) ailment = c.getString(0);

        c.close();
        return ailment;
    }

    public String[] getMoveDescription(String moveId) {
        Cursor c = getCursor("" +
                "SELECT ver.name, des.description " +
                "FROM move_description as des LEFT JOIN version as ver " +
                "ON des.version_group_id = ver.version_id " +
                "WHERE des.move_id = ? " +
                "ORDER BY des.version_group_id",new String[] {moveId});

        String[] Desc;
        if (c.moveToFirst()) {
            Desc = new String[c.getCount()];
            for (int n = 0; n < c.getCount(); n++) {
                Desc[n] = c.getString(0) + SPLIT + c.getString(1);
                c.moveToNext();
            }
        } else Desc = new String[] {UNKNOWN + SPLIT + UNKNOWN};

        c.close();
        return Desc;
    }

    public String[] getMoveEfficacy(String type, Effectiveness eff) {
        String criteria;
        if (eff == Effectiveness.SUPER_EFFECTIVE) criteria = "> 100";
        else if (eff == Effectiveness.IMMUNE) criteria = "= 0";
        else criteria = "BETWEEN 1 AND 99";

        Cursor c = getCursor("" +
                "SELECT target_type_id, damage_factor " +
                "FROM type_efficacy " +
                "WHERE damage_type_id = ? " +
                "AND damage_factor " + criteria, new String[] {type});

        String[] efficacy;
        if (c.moveToFirst()) {
            efficacy = new String[c.getCount()];
            Double val;
            for (int n = 0; n < c.getCount(); n++) {
                val = Double.valueOf(c.getString(1)) / 100;

                efficacy[n] = c.getString(0) + SPLIT +
                        (val > 1 || val == 0 ? String.format("%.0f",val) : String.valueOf(val));
                c.moveToNext();
            }
        } else efficacy = new String[] {"0" + SPLIT + "1"};

        c.close();
        return efficacy;
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

    public static void setImage(ImageView imageView, String imgLocation, ImgSize imgSize) {
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

    public static void setTypeName(TextView tv, int type_id) {
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