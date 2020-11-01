package com.red.seabattle;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TableLayout userShips = (TableLayout)findViewById(R.id.userShips);
        System.out.println(userShips.getChildCount());
    }
}