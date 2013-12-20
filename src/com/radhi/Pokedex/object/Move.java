package com.radhi.Pokedex.object;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import com.radhi.Pokedex.other.*;
import com.radhi.Pokedex.other.Enum.*;

public class Move implements Parcelable {
    private String moveID,
            Name, Generation, Type, Power, PP,
            Accuracy, Target, Category, Ailment;
    private String[] Description,
            OffenceStrong, OffenceImmune, OffenceWeak;

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(moveID);
        out.writeString(Name);
        out.writeString(Generation);
        out.writeString(Type);
        out.writeString(Power);
        out.writeString(PP);
        out.writeString(Accuracy);
        out.writeString(Target);
        out.writeString(Category);
        out.writeString(Ailment);
        out.writeStringArray(Description);
        out.writeStringArray(OffenceStrong);
        out.writeStringArray(OffenceImmune);
        out.writeStringArray(OffenceWeak);
    }

    public static final Parcelable.Creator<Move> CREATOR
            = new Parcelable.Creator<Move>() {
        @Override
        public Move createFromParcel(Parcel in) {
            return new Move(in);
        }

        @Override
        public Move[] newArray(int size) {
            return new Move[size];
        }
    };

    private Move (Parcel in) {
        moveID = in.readString();
        Name = in.readString();
        Generation = in.readString();
        Type = in.readString();
        Power = in.readString();
        PP = in.readString();
        Accuracy = in.readString();
        Target = in.readString();
        Category = in.readString();
        Ailment = in.readString();
        Description = in.createStringArray();
        OffenceStrong = in.createStringArray();
        OffenceImmune = in.createStringArray();
        OffenceWeak = in.createStringArray();
    }

    public Move(Context context, String moveID) {
        this.moveID = moveID;
        Database DB = new Database(context);

        String[] moveData = DB.getMoveData(moveID).split(Database.SPLIT);
        Name = moveData[0];
        Generation = moveData[1];
        Type = moveData[2];
        Power = moveData[3];
        PP = moveData[4];
        Accuracy = moveData[5];
        Target = moveData[6];
        Category = moveData[7];
        Ailment = DB.getMoveAilment(moveID);
        Description = DB.getMoveDescription(moveID);
        OffenceStrong = DB.getMoveEfficacy(Type, Effectiveness.SUPER_EFFECTIVE);
        OffenceImmune = DB.getMoveEfficacy(Type, Effectiveness.IMMUNE);
        OffenceWeak = DB.getMoveEfficacy(Type, Effectiveness.NOT_EFFECTIVE);

        DB.close();
    }

    public String moveID() {return moveID;}

    public String Name() {return Name;}

    public String Generation() {return Generation;}

    public String Type() {return Type;}

    public String Power() {return Power;}

    public String PP() {return PP;}

    public String Accuracy() {return Accuracy;}

    public String Target() {return Target;}

    public String Category() {return Category;}

    public String Ailment() {return Ailment;}

    public String[] Description() {return Description;}

    public String[] OffenceStrong() {return OffenceStrong;}

    public String[] OffenceImmune() {return OffenceImmune;}

    public String[] OffenceWeak() {return OffenceWeak;}
}
