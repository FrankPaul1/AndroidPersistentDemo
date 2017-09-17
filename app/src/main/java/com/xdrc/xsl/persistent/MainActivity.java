package com.xdrc.xsl.persistent;

import android.content.UriMatcher;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements
        FileFragment.OnFragmentInteractionListener,
        SharedPreferencesFragment.OnFragmentInteractionListener,
        SQLFragment.OnFragmentInteractionListener {
    @BindView(R.id.et_name)
    EditText username;

    @BindView(R.id.et_age)
    EditText age;

    @BindView(R.id.vp)
    ViewPager vp;

    List<String> titleList = Arrays.asList("Shared Preferences", "File", "SQL");
    List<Fragment> mFragments = new ArrayList<>();
    UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    final int SP = 0;
    final int FILE = 1;
    final int SQL = 2;
    final String TAG = "MainActivity";
    String DOMAIN = "com.xdrc.xsl.persistent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initMatcher();
        initViewPager();
    }

    private void initMatcher() {
        matcher.addURI(DOMAIN, "sp", SP);
        matcher.addURI(DOMAIN, "file", FILE);
        matcher.addURI(DOMAIN, "sql", SQL);
    }

    private void initViewPager() {
        mFragments.add(new SharedPreferencesFragment());
        mFragments.add(new FileFragment());
        mFragments.add(new SQLFragment());

        vp.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        int match = matcher.match(uri);
        Log.i(TAG, "onFragmentInteraction " + match);
        int fragmentIndex;
        switch (match) {
            case SP:
                fragmentIndex = 0;
                break;
            case FILE:
                fragmentIndex = 1;
                break;
            case SQL:
                fragmentIndex = 2;
                break;
            default:
                fragmentIndex = -1;
                break;
        }
        onAdd(fragmentIndex);
    }

    private void onAdd(int fragmentIndex) {
        if (fragmentIndex >= 0) {
            OnAddInterface onAddFragment = (OnAddInterface) mFragments.get(fragmentIndex);
            onAddFragment.onAdd(username.getText().toString(), Integer.parseInt(age.getText().toString()));
        }
    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}
