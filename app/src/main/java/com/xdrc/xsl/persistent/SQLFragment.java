package com.xdrc.xsl.persistent;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SQLFragment extends Fragment implements OnAddInterface {
    private OnFragmentInteractionListener mListener;

    @BindView(R.id.tv_sql_result)
    public TextView resultTv;

    private Context ctx;
    String TAG = "SQLFragment";
    private MyDBHelper dbHelper;
    private SQLiteDatabase writeDb;
    private SQLiteDatabase readDb;

    public SQLFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sql, container, false);
        ButterKnife.bind(this, view);

        ctx = getActivity();
        dbHelper = new MyDBHelper(ctx);
        writeDb = dbHelper.getWritableDatabase();
        readDb = dbHelper.getReadableDatabase();
        refreshResult();

        return view;
    }

    @OnClick(R.id.btn_sql_add)
    public void onAdd() {
        onButtonPressed(Uri.parse("content://com.xdrc.xsl.persistent/sql"));
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
        Log.i(TAG, "onAdd: " + username + ", " + age);
        writeDb.beginTransaction();
        try {
            writeDb.execSQL("INSERT INTO " + MyDBHelper.TABLE +
                    " (username, age) VALUES(?, ?)", new Object[]{username, age});
            writeDb.setTransactionSuccessful();
            refreshResult();
        } finally {
            writeDb.endTransaction();
        }
    }

    public void refreshResult() {
        Cursor c = readDb.rawQuery("SELECT * from " + MyDBHelper.TABLE, null);
        int count = 0;
        while (c.moveToNext()) {
            count++;
            Log.i(TAG, "Current is " + c.getString(c.getColumnIndex("username")) + c.getInt(c.getColumnIndex("age")));
        }
        c.close();

        resultTv.setText(String.valueOf(count));
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
