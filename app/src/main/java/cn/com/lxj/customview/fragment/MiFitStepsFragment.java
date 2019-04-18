package cn.com.lxj.customview.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cn.com.lxj.customview.R;
import cn.com.lxj.customview.view.xiaomi.StepRefreshView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MiFitStepsFragment extends Fragment {
    private static final String TAG = "MiFitStepsFragment";

    private Button mStartBtn;
    private Button mStopBtn;
    private StepRefreshView mStepRefreshView;

    public MiFitStepsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mi_fit_steps, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        mStartBtn = getActivity().findViewById(R.id.mi_steps_start_btn);
        mStopBtn = getActivity().findViewById(R.id.mi_steps_stop_btn);
        mStepRefreshView = getActivity().findViewById(R.id.mi_step_refresh_view);

        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStepRefreshView.setAnimStart();
            }
        });
        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStepRefreshView.setAnimStop();
            }
        });
    }

}
