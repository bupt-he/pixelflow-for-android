package processing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import processing.test.sketch_230701b.R;

public class Fluid2dActivity extends AppCompatActivity {
    public static final String DISPLAY_TAG = "display_tag";
    public static final String DISPLAY_BUNDLE = "display_bundle";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fluid_2d);
        context = this;
    }


    public void onClickButton(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.btn_fluid_basic:
                bundle.putInt(DISPLAY_TAG, 0);
                break;
            case R.id.btn_fluid_custom_particles:
                bundle.putInt(DISPLAY_TAG, 1);
                break;
            case R.id.btn_fluid_first_blood:
                bundle.putInt(DISPLAY_TAG, 2);
                break;
            case R.id.btn_fluid_get_start:
                bundle.putInt(DISPLAY_TAG, 3);
                break;
        }

        Intent intent = new Intent(this, Fluid2dDisplayActivity.class);
        intent.putExtra(DISPLAY_BUNDLE, bundle);
        startActivity(intent);
    }

}