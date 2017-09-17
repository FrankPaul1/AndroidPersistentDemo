package com.xdrc.xsl.persistent;

import android.content.Context;
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

public class FileFragment extends Fragment implements OnAddInterface {
    private OnFragmentInteractionListener mListener;

    @BindView(R.id.tv_file_result)
    public TextView resultTv;

    private Context ctx;
    String TAG = "FileFragment";

    public FileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_file, container, false);
        ButterKnife.bind(this, view);

        ctx = getActivity();
        refreshResult();

        return view;
    }

    @OnClick(R.id.btn_file_add)
    public void onAdd() {
        onButtonPressed(Uri.parse("content://com.xdrc.xsl.persistent/file"));
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
        String inputText = "username:" + username + ", age:" + age;
        FileOutputStream fos;
        BufferedWriter writer = null;
        try{
            fos = ctx.openFileOutput("data", ctx.MODE_APPEND);
            writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(inputText + "\n");
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            if (writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        refreshResult();
    }

    public void refreshResult() {
        FileInputStream fis;
        BufferedReader reader = null;
        int lineCount = 0;
        try{
            fis = ctx.openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                lineCount++;
            }

            Log.i(TAG, String.valueOf(lineCount));

            resultTv.setText(String.valueOf(lineCount));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
