package processing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import processing.test.sketch_230701b.R;

public class Fluid_Activity extends AppCompatActivity {
    public static final String DISPLAY_TAG = "display_tag";
    public static final String DISPLAY_BUNDLE = "display_bundle";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fluid);
        context = this;
    }


    public void onClickButton(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.btn_fluid_lic:
                bundle.putInt(DISPLAY_TAG, 0);
                break;
            case R.id.btn_fluid_lic_image:
                bundle.putInt(DISPLAY_TAG, 1);
                break;
            case R.id.btn_fluid_basic_lic:
                bundle.putInt(DISPLAY_TAG, 2);
                break;
            case R.id.btn_fluid_windtunnel_lic:
                bundle.putInt(DISPLAY_TAG, 3);
                break;
            case R.id.btn_flow_field_particles_attractors:
                bundle.putInt(DISPLAY_TAG, 4);
                break;


        }
        Intent intent = new Intent(Fluid_Activity.this, FluidDisplayActivity.class);
        intent.putExtra(DISPLAY_BUNDLE, bundle);
        startActivity(intent);
    }
}