package com.radhi.Pokedex.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.Window;
import android.widget.TextView;
import com.radhi.Pokedex.R;
import com.radhi.Pokedex.other.Database;

public class MoveDetail extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_move_detail);

        Database DB = new Database(this);

        String ID = getIntent().getStringExtra(Main.MOVE_ID);
        TextView txtMoveName = (TextView) findViewById(R.id.txtMoveName);
        TextView txtMoveDescription = (TextView) findViewById(R.id.txtMoveDescription);
        TextView txtMoveType = (TextView) findViewById(R.id.txtMoveType);
        TextView txtMoveCategory = (TextView) findViewById(R.id.txtMoveCategory);
        TextView txtMovePower = (TextView) findViewById(R.id.txtMovePower);
        TextView txtMoveAccuracy = (TextView) findViewById(R.id.txtMoveAccuracy);
        TextView txtMovePP = (TextView) findViewById(R.id.txtMovePP);
        TextView txtMoveAilment = (TextView) findViewById(R.id.txtMoveAilment);
        TextView txtMoveTarget = (TextView) findViewById(R.id.txtMoveTarget);

        txtMoveName.setText(DB.getMoveName(ID));
        txtMoveDescription.setText(Html.fromHtml(DB.getMoveDescription(ID)));
        DB.getTypeName(txtMoveType,Integer.valueOf(DB.getMoveType(ID)));
        txtMoveCategory.setText(DB.getMoveCategory(ID));
        txtMovePower.setText(DB.getMovePower(ID));
        txtMoveAccuracy.setText(DB.getMoveAccuracy(ID) + " %");
        txtMovePP.setText(DB.getMovePP(ID));
        txtMoveAilment.setText(DB.getMoveAilment(ID));
        txtMoveTarget.setText(DB.getMoveTarget(ID));
    }
}
