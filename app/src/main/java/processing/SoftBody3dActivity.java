package processing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import processing.test.sketch_230701b.R;

public class SoftBody3dActivity extends AppCompatActivity {
    public static final String DISPLAY_TAG = "display_tag";
    public static final String DISPLAY_BUNDLE = "display_bundle";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_body_3d);
        context = this;
    }


    public void onClickButton(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.btn_softbody_3d_cloth:
                bundle.putInt(DISPLAY_TAG, 0);
                break;

        }

        Intent intent = new Intent(this, SoftBody3DDisplayActivity.class);
        intent.putExtra(DISPLAY_BUNDLE, bundle);
        startActivity(intent);
    }

}