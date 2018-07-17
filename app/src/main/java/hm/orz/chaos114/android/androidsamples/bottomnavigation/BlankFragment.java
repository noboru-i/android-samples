package hm.orz.chaos114.android.androidsamples.bottomnavigation;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hm.orz.chaos114.android.androidsamples.R;
import hm.orz.chaos114.android.androidsamples.databinding.FragmentBlankBinding;

public class BlankFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    private FragmentBlankBinding binding;

    public BlankFragment() {
        // Required empty public constructor
    }

    public static BlankFragment newInstance(String param1) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_blank, container, false);
        binding.text.setText(mParam1);
        return binding.getRoot();
    }
}
