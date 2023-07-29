package processing;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import processing.FlowFieldParticles.FlowFieldParticles_DamBreak;
import processing.FlowFieldParticles.FlowFieldParticles_DevDemo;
import processing.FlowFieldParticles.FlowFieldParticles_Impulse;
import processing.FlowFieldParticles.FlowFieldParticles_OpticalFlowCapture;
import processing.FlowFieldParticles.FlowFieldParticles_SpriteGenerator;
import processing.android.CompatUtils;
import processing.android.PFragment;
import processing.core.PApplet;
import processing.fluid.FlowFieldParticles_Attractors;
import processing.fluid.FlowField_LIC;
import processing.fluid.FlowField_LIC_Image;
import processing.fluid.Fluid_Basic_LIC;
import processing.fluid.Fluid_WindTunnel_LIC;

public class FlowFieldParticleDisplayActivity extends AppCompatActivity {
    private PApplet sketch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frame = new FrameLayout(this);
        frame.setId(CompatUtils.getUniqueViewId());
        setContentView(frame, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        Bundle bundleExtra = getIntent().getBundleExtra(SoftBodyActivity.DISPLAY_BUNDLE);
        int key = bundleExtra.getInt(SoftBodyActivity.DISPLAY_TAG);
        switch (key) {
            case 0:
                sketch = new FlowFieldParticles_Attractors();
                break;

            case 1:
                sketch = new FlowFieldParticles_DamBreak();
                break;

            case 2:
                sketch = new FlowFieldParticles_DevDemo();
                break;

            case 3:
                sketch = new FlowFieldParticles_Impulse();
                break;
            case 4:
                sketch = new FlowFieldParticles_SpriteGenerator();
                break;

                case 5:
                sketch = new FlowFieldParticles_OpticalFlowCapture();
                break;

        }
        PFragment fragment = new PFragment(sketch);
        fragment.setView(frame, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (sketch != null) {
            sketch.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (sketch != null) {
            sketch.onNewIntent(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (sketch != null) {
            sketch.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        if (sketch != null) {
            sketch.onBackPressed();
        }
    }
}
