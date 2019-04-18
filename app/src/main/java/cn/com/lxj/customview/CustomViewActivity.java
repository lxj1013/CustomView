package cn.com.lxj.customview;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.com.lxj.customview.fragment.DrawTextTestFragment;
import cn.com.lxj.customview.fragment.JiKeLikeFragment;
import cn.com.lxj.customview.fragment.MiFitStepsFragment;

import static cn.com.lxj.customview.MainActivity.CUSTOM_VIEW_ID;

public class CustomViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        setFragment(getIntent());
    }

    private void setFragment(Intent intent) {
        int id = intent.getIntExtra(CUSTOM_VIEW_ID, 0);
        switch (id) {
            case R.id.draw_text_text_btn:
                replaceFragment(new DrawTextTestFragment());
                break;
            case R.id.ji_ke_like_btn:
                replaceFragment(new JiKeLikeFragment());
                break;
            case R.id.xiao_mi_steps_btn:
                replaceFragment(new MiFitStepsFragment());
                break;
            default:
                break;
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.custom_view_frameLayout, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
