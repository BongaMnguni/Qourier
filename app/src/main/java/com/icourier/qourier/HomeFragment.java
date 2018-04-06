package com.icourier.qourier;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


public class HomeFragment extends Fragment {

    RelativeLayout layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        layout = (RelativeLayout) view.findViewById(R.id.home_layout);
        layout.getBackground().setAlpha(170);
        return view;
    }


}
