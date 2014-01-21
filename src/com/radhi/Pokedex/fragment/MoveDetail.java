package com.radhi.Pokedex.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.radhi.Pokedex.R;
import com.radhi.Pokedex.activity.ActivityMain;
import com.radhi.Pokedex.activity.ActivityMoveDetail;
import com.radhi.Pokedex.adapter.ListEfficacyAdapter;
import com.radhi.Pokedex.object.Move;
import com.radhi.Pokedex.other.Database;

import java.util.Random;

public class MoveDetail extends Fragment {
    private Activity activity;
    private Move move;
    private TextView txtMoveDescription;
    private String moveID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_move_detail, container, false);

        if (moveID != null) move = new Move(activity,moveID);
        else move = getArguments().getParcelable(ActivityMain.MOVE_DATA);

        TextView txtMoveName = (TextView) view.findViewById(R.id.txtMoveName);
        txtMoveDescription = (TextView) view.findViewById(R.id.txtMoveDescription);
        TextView txtMoveType = (TextView) view.findViewById(R.id.txtMoveType);
        TextView txtMoveCategory = (TextView) view.findViewById(R.id.txtMoveCategory);
        TextView txtMovePower = (TextView) view.findViewById(R.id.txtMovePower);
        TextView txtMoveAccuracy = (TextView) view.findViewById(R.id.txtMoveAccuracy);
        TextView txtMovePP = (TextView) view.findViewById(R.id.txtMovePP);
        TextView txtMoveAilment = (TextView) view.findViewById(R.id.txtMoveAilment);
        TextView txtMoveTarget = (TextView) view.findViewById(R.id.txtMoveTarget);
        LinearLayout boxOffenceStrong = (LinearLayout) view.findViewById(R.id.boxOffenceStrong);
        LinearLayout boxOffenceImmune = (LinearLayout) view.findViewById(R.id.boxOffenceImmune);
        LinearLayout boxOffenceWeak = (LinearLayout) view.findViewById(R.id.boxOffenceWeak);

        txtMoveName.setText(move.moveID() + ". " + move.Name());
        txtMoveDescription.setText(Html.fromHtml(getDescription(move)));
        Database.setTypeName(txtMoveType, Integer.valueOf(move.Type()));
        Database.setCategoryResource(txtMoveCategory,move.Category());
        txtMovePower.setText(move.Power());
        txtMoveAccuracy.setText(move.Accuracy());
        txtMovePP.setText(move.PP());
        txtMoveAilment.setText(move.Ailment());
        txtMoveTarget.setText(move.Target());
        fillBoxEffifacy(boxOffenceStrong,move.OffenceStrong());
        fillBoxEffifacy(boxOffenceImmune,move.OffenceImmune());
        fillBoxEffifacy(boxOffenceWeak,move.OffenceWeak());

        txtMoveDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtMoveDescription.setText(Html.fromHtml(getDescription(move)));
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = getActivity();
        moveID = ((ActivityMoveDetail) getActivity()).getMoveID();
    }

    private String getDescription(Move move) {
        int Rnd = new Random().nextInt(move.Description().length);
        String[] desc = move.Description()[Rnd].split(Database.SPLIT);
        return "<i>" +desc[1] + "</i><br/><b>(Pokémon " + desc[0] + ")</b>";
    }

    private void fillBoxEffifacy(LinearLayout box, String[] data) {
        ListEfficacyAdapter adapter = new ListEfficacyAdapter(activity,data);
        for (int n = 0; n < adapter.getCount(); n++)
            box.addView(adapter.getView(n,null,box));
    }
}
