package cn.com.lxj.customview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    public static final String CUSTOM_VIEW_ID = "CUSTOM_VIEW_ID";

    private Intent mIntent;

    private Button mDrawTextTestBtn;
    private Button mJKLikeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {

        mIntent = new Intent(this, CustomViewActivity.class);

        mDrawTextTestBtn = findViewById(R.id.draw_text_text_btn);
        mJKLikeBtn = findViewById(R.id.ji_ke_like_btn);

        mDrawTextTestBtn.setOnClickListener(this);
        mJKLikeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mIntent.putExtra(CUSTOM_VIEW_ID, v.getId());
        startActivity(mIntent);
    }
}
