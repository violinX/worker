package com.example.administrator.worker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class TabPage extends AppCompatActivity{
    /**
     * Called when the activity is first created.
     */
    private RadioGroup rgs;
    public List<Fragment> fragments = new ArrayList<Fragment>();

    public String hello = "hello ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_page);
        fragments.add(new TabAFm());
        fragments.add(new TabBFm());
        fragments.add(new TabCFm());
        fragments.add(new TabDFm());


        rgs = (RadioGroup) findViewById(R.id.tabs_rg);

        FragmentTabAdapter tabAdapter = new FragmentTabAdapter(this, fragments, R.id.tab_content, rgs);
        tabAdapter.setOnRgsExtraCheckedChangedListener(new FragmentTabAdapter.OnRgsExtraCheckedChangedListener() {
            @Override
            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
                System.out.println("Extra---- " + index + " checked!!! ");
            }
        });

    }
}
