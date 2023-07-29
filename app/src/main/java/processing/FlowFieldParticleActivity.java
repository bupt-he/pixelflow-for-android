package processing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import processing.test.sketch_230701b.R;

public class FlowFieldParticleActivity extends AppCompatActivity {
    public static final String DISPLAY_TAG = "display_tag";
    public static final String DISPLAY_BUNDLE = "display_bundle";
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_field_particle);
        context = this;
    }


    public void onClickButton(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.btn_flowfieldparticles_attractors:
                bundle.putInt(DISPLAY_TAG, 0);
                break;
            case R.id.btn_flowfieldparticles_dambreak:
                bundle.putInt(DISPLAY_TAG, 1);
                break;
            case R.id.btn_flowfieldparticles_devdemo:
                bundle.putInt(DISPLAY_TAG, 2);
                break;
            case R.id.btn_flowfieldparticles_impulse:
                bundle.putInt(DISPLAY_TAG, 3);
                break;
            case R.id.btn_flowfieldparticles_spritegenerator:
                bundle.putInt(DISPLAY_TAG, 4);

                case R.id.btn_flowfieldparticles_optical_flow_capture:
                bundle.putInt(DISPLAY_TAG, 45);
                break;
        }

        Intent intent = new Intent(this, FlowFieldParticleDisplayActivity.class);
        intent.putExtra(DISPLAY_BUNDLE, bundle);
        startActivity(intent);
    }

}