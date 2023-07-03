package processing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import processing.test.sketch_230701b.R;

public class SoftBodyActivity extends AppCompatActivity {
    public static final String DISPLAY_TAG = "display_tag";
    public static final String DISPLAY_BUNDLE = "display_bundle";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_body);
        context = this;
    }


    public void onClickButton(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.chain:
                bundle.putInt(DISPLAY_TAG, 0);
                break;
            case R.id.btn_close:
                bundle.putInt(DISPLAY_TAG, 1);
                break;
            case R.id.btn_connectedBodies:
                bundle.putInt(DISPLAY_TAG, 2);
                break;

            case R.id.btn_differentialgrowth:
                bundle.putInt(DISPLAY_TAG, 3);
                break;

            case R.id.btn_differentialgrowth2:
                bundle.putInt(DISPLAY_TAG, 4);
                break;

            case R.id.btn_differentialgrowth_close:
                bundle.putInt(DISPLAY_TAG, 5);
                break;
            case R.id.btn_differentialgrowth_open:
                bundle.putInt(DISPLAY_TAG, 6);
                break;
            case R.id.btn_differentialgrowth_getstarted:
                bundle.putInt(DISPLAY_TAG, 7);
                break;

            case R.id.btn_differentialgrowth_liquid:
                bundle.putInt(DISPLAY_TAG, 8);
                break;

            case R.id.btn_particleCollisionsystem:
                bundle.putInt(DISPLAY_TAG, 9);
                break;

            case R.id.btn_playground:
                bundle.putInt(DISPLAY_TAG, 10);
                break;

            case R.id.btn_trees:
                bundle.putInt(DISPLAY_TAG, 11);
                break;
        }
        Intent intent = new Intent(SoftBodyActivity.this, SoftBodyDisplayActivity.class);
        intent.putExtra(DISPLAY_BUNDLE, bundle);
        startActivity(intent);
    }
}