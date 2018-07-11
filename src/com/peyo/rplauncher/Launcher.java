package com.peyo.rplauncher;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

public class Launcher extends Activity implements LauncherModel.Callbacks {
    private LauncherModel mModel;
    private GridFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            mFragment = new GridFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.grid_fragment, mFragment)
                    .commit();
        }
        mModel = new LauncherModel(getApplicationContext(), this);
        mModel.startLoader();
    }

    @Override
    public void bindAllApplications(ArrayList<AppInfo> apps) {
        mFragment.updateApps(apps);
    }
}
