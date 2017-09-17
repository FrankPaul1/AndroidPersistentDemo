package com.xdrc.xsl.persistent;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SharedPreferencesFragment extends Fragment implements OnAddInterface {

    private OnFragmentInteractionListener mListener;

    SharedPreferences sp;

    @BindView(R.id.tv_sp_result)
    TextView resultTv;

    String KEY = "SP";
    String TAG = "SharedPreferences";

    public SharedPreferencesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shared_preferences, container, false);
        ButterKnife.bind(this, view);
        Context ctx = getActivity();

        sp = ctx.getSharedPreferences("data", ctx.MODE_APPEND);
        refreshResult();

        return view;
    }

    @OnClick(R.id.btn_sp_add)
    public void onAdd() {
        onButtonPressed(Uri.parse("content://com.xdrc.xsl.persistent/sp"));
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onAdd(String username, int age) {
        SharedPreferences.Editor editor = sp.edit();

        Set<String> set = sp.getStringSet(KEY, new HashSet<String>());
        Log.i(TAG, "OnAdd: " + username + ";age:" + ", size" + set.size());
        set.add("username:" + username + ";age:" + age);

        editor.putStringSet(KEY, set);
        editor.apply();

        refreshResult();
    }

    public void refreshResult() {
        Set<String> set = sp.getStringSet(KEY, new HashSet<String>());
        resultTv.setText(String.valueOf(set.size()));
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
