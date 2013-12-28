package com.radhi.Pokedex.object;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import com.radhi.Pokedex.other.Database;
import com.radhi.Pokedex.other.Enum.Effectiveness;

public class Pokemon implements Parcelable {
    private String ID, EnglishName, JapaneseName, RomajiName, Species,
            Habitat, Type1, Type2, Height, Weight, BodyShape, Gender,
            EggGroups, HatchCounter, GrowthRate, CaptureRate,
            BaseExperience, BaseEffort, BaseHappiness;

    private String[] DexNumber, Description, Ability, Stats,
            TypeOffenceEffective, TypeOffenceImmune, TypeOffenceWeak,
            TypeDefenceWeak, TypeDefenceImmune, TypeDefenceStrong,
            AvailableMoveVersion, AvailableMoveMethod, OtherForm;

    private Boolean hasGenderDifferences, formSwitchable;

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(ID);
        out.writeString(EnglishName);
        out.writeString(JapaneseName);
        out.writeString(RomajiName);
        out.writeString(Species);
        out.writeString(Habitat);
        out.writeString(Type1);
        out.writeString(Type2);
        out.writeString(Height);
        out.writeString(Weight);
        out.writeString(BodyShape);
        out.writeString(Gender);
        out.writeString(EggGroups);
        out.writeString(HatchCounter);
        out.writeString(GrowthRate);
        out.writeString(CaptureRate);
        out.writeString(BaseExperience);
        out.writeString(BaseEffort);
        out.writeString(BaseHappiness);
        out.writeStringArray(DexNumber);
        out.writeStringArray(Description);
        out.writeStringArray(Ability);
        out.writeStringArray(Stats);
        out.writeStringArray(TypeOffenceEffective);
        out.writeStringArray(TypeOffenceImmune);
        out.writeStringArray(TypeOffenceWeak);
        out.writeStringArray(TypeDefenceWeak);
        out.writeStringArray(TypeDefenceImmune);
        out.writeStringArray(TypeDefenceStrong);
        out.writeStringArray(AvailableMoveVersion);
        out.writeStringArray(AvailableMoveMethod);
        out.writeStringArray(OtherForm);
        out.writeByte((byte) (hasGenderDifferences ? 1 : 0));
        out.writeByte((byte) (formSwitchable ? 1 : 0));
    }

    public static final Parcelable.Creator<Pokemon> CREATOR = new Parcelable.Creator<Pokemon>() {
        @Override
        public Pokemon createFromParcel(Parcel in) {return new Pokemon(in);}

        @Override
        public Pokemon[] newArray(int size) {return new Pokemon[size];}
    };

    private Pokemon(Parcel in) {
        ID = in.readString();
        EnglishName = in.readString();
        JapaneseName = in.readString();
        RomajiName = in.readString();
        Species = in.readString();
        Habitat = in.readString();
        Type1 = in.readString();
        Type2 = in.readString();
        Height = in.readString();
        Weight = in.readString();
        BodyShape = in.readString();
        Gender = in.readString();
        EggGroups = in.readString();
        HatchCounter = in.readString();
        GrowthRate = in.readString();
        CaptureRate = in.readString();
        BaseExperience = in.readString();
        BaseEffort = in.readString();
        BaseHappiness = in.readString();
        DexNumber = in.createStringArray();
        Description = in.createStringArray();
        Ability = in.createStringArray();
        Stats = in.createStringArray();
        TypeOffenceEffective = in.createStringArray();
        TypeOffenceImmune = in.createStringArray();
        TypeOffenceWeak = in.createStringArray();
        TypeDefenceWeak = in.createStringArray();
        TypeDefenceImmune = in.createStringArray();
        TypeDefenceStrong = in.createStringArray();
        AvailableMoveVersion = in.createStringArray();
        AvailableMoveMethod = in.createStringArray();
        OtherForm = in.createStringArray();
        hasGenderDifferences = in.readByte() != 0;
        formSwitchable = in.readByte() != 0;
    }

    public Pokemon(Context context, String ID) {
        this.ID = ID;
        Database DB = new Database(context);

        String[] name = DB.getPokemonName(ID).split(Database.SPLIT);
        String[] type = DB.getPokemonType(ID).split(Database.SPLIT);
        String[] hwe = DB.getPokemonHWE(ID).split(Database.SPLIT);
        String[] gchgf = DB.getPokemonGCHHGF(ID).split(Database.SPLIT);

        EnglishName = name[0];
        JapaneseName = name[1];
        RomajiName = name[2];
        Species = name[3];
        Habitat = DB.getPokemonHabitat(ID);
        Type1 = type[0];
        Type2 = type[1];
        Height = hwe[0];
        Weight = hwe[1];
        BaseExperience = hwe[2];
        BodyShape = DB.getPokemonShape(ID);
        GrowthRate = DB.getPokemonGrowthRate(ID);
        Gender = gchgf[0];
        CaptureRate = gchgf[1];
        BaseHappiness = gchgf[2];
        HatchCounter = gchgf[3];
        hasGenderDifferences = gchgf[4].equals("1");
        formSwitchable = gchgf[5].equals("1");
        EggGroups = DB.getPokemonEgg(ID);
        BaseEffort = DB.getPokemonBaseEffort(ID);
        DexNumber = DB.getPokedexNumber(ID);
        Description = DB.getPokemonDescription(ID);
        Ability = DB.getPokemonAbility(ID);
        Stats = DB.getPokemonStat(ID);
        TypeDefenceWeak = DB.getPokemonEfficacy(Type1, Type2, Effectiveness.SUPER_EFFECTIVE);
        TypeDefenceImmune = DB.getPokemonEfficacy(Type1, Type2, Effectiveness.IMMUNE);
        TypeDefenceStrong = DB.getPokemonEfficacy(Type1, Type2, Effectiveness.NOT_EFFECTIVE);
        AvailableMoveVersion = DB.getPokemonMoveVersion(ID);
        AvailableMoveMethod = DB.getPokemonMoveMethod(ID);
        OtherForm = DB.getPokemonForm(ID,EnglishName);

        DB.close();
    }

    public String ID() {return ID;}

    public String EnglishName() {return EnglishName;}

    public String JapaneseName() {return JapaneseName;}

    public String RomajiName() {return RomajiName;}

    public String Species() {return Species + " Pokémon";}

    public String Habitat() {return Habitat;}

    public String Type1() {return Type1;}

    public String Type2() {return Type2;}

    public String Height() {
        if (Height.equals("-1")) return Database.UNKNOWN;
        else {
            double cm = Double.parseDouble(Height) * 10;
            int ft = (int)(cm/30.48);
            double inch = (cm - (ft * 30.48)) / 2.54;

            String metric;
            if (cm < 100) metric = String.format("%s", cm) + "cm";
            else metric = String.format("%s", cm / 100) + "m";

            return metric + " (" + String.valueOf(ft) + "'" + String.format("%.1f",inch) + "'')";
        }
    }

    public String Weight() {
        if (Weight.equals("-1")) return Database.UNKNOWN;
        else {
            double kg = Double.parseDouble(Weight) / 10;
            double lb = kg * 2.20462262;
            return String.format("%s", kg) + "kg (" + String.format("%.2f", lb) + "lbs)";
        }
    }

    public String BodyShape() {return BodyShape;}

    public String Gender() {
        double female = Double.parseDouble(Gender);
        double male = 8 - female;

        if (female == -2) return Database.UNKNOWN;
        else if (female == -1) return "Genderless";
        else return String.format("%s",male / 8 * 100) + "% ♂, "
                + String.format("%s",female / 8 * 100) + "% ♀";
    }

    public String EggGroups() {return EggGroups;}

    public String HatchCounter() {return HatchCounter;}

    public String GrowthRate() {return GrowthRate;}

    public String CaptureRate() {
        if (CaptureRate.equals("-1")) return Database.UNKNOWN;
        else {
            double percent = Double.parseDouble(CaptureRate) / 765 * 100;
            return CaptureRate + " (" + String.format("%.2f",percent) + "% with PokéBall, full HP)";
        }
    }

    public String BaseExperience() {return BaseExperience;}

    public String BaseEffort() {return BaseEffort;}

    public String BaseHappiness() {return BaseHappiness;}

    public Boolean hasGenderDifferences() {return hasGenderDifferences;}

    public Boolean FormSwitchable() {return formSwitchable;}

    public String[] DexNumber() {return DexNumber;}

    public String[] Description() {return Description;}

    public String[] Ability() {return Ability;}

    public String[] Stats() {return Stats;}
    
    public String[] TypeDefenceWeak() {return TypeDefenceWeak;}

    public String[] TypeDefenceImmune() {return TypeDefenceImmune;}

    public String[] TypeDefenceStrong() {return TypeDefenceStrong;}
    
    public String[] AvailableMoveVersion() {return AvailableMoveVersion;}

    public String[] AvailableMoveMethod() {return AvailableMoveMethod;}

    public String[] OtherForm() {return OtherForm;}
}
