package com.radhi.Pokedex.activity;

import android.app.Activity;
import android.os.Bundle;
import com.radhi.Pokedex.R;

public class ActivityMoveDetail extends Activity {
    private String moveID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moveID = getIntent().getStringExtra(ActivityMain.MOVE_ID);

        setContentView(R.layout.activity_move_detail);
        getWindow().setBackgroundDrawable(null);
    }

    public String getMoveID() {return moveID;}
}
