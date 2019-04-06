package cn.com.lxj.customview.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.com.lxj.customview.R;
import cn.com.lxj.customview.view.jike.LikeClickListener;
import cn.com.lxj.customview.view.jike.LikeCountView;
import cn.com.lxj.customview.view.jike.LikeView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 */
public class JiKeLikeFragment extends Fragment {

    private static final String TAG = "JiKeLikeFragment";

    private LikeView mLikeView;
    private LikeCountView mLikeCountView;
    private EditText mLikeEdit;
    private Button mLikeOkBtn;

    public JiKeLikeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ji_ke_like, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        mLikeView = getActivity().findViewById(R.id.like_view);
        mLikeCountView = getActivity().findViewById(R.id.like_count_view);
        mLikeView.setLikeClickListener(mLikeCountView);

        mLikeEdit = getActivity().findViewById(R.id.like_edit);
        mLikeOkBtn = getActivity().findViewById(R.id.like_ok_btn);
        mLikeOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = mLikeEdit.getText().toString();
                try {
                    mLikeCountView.setCount(Integer.valueOf(str));
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}
