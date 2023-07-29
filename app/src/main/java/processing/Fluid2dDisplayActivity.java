package processing;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import processing.FlowFieldParticles.FlowFieldParticles_DamBreak;
import processing.FlowFieldParticles.FlowFieldParticles_DevDemo;
import processing.FlowFieldParticles.FlowFieldParticles_Impulse;
import processing.FlowFieldParticles.FlowFieldParticles_SpriteGenerator;
import processing.android.CompatUtils;
import processing.android.PFragment;
import processing.core.PApplet;
import processing.fluid.FlowFieldParticles_Attractors;
import processing.fluid.Fluid_Basic_LIC;
import processing.fluid2d.Fluid_Basic;
import processing.fluid2d.Fluid_CustomParticles;
import processing.fluid2d.Fluid_FirstBlood;
import processing.fluid2d.Fluid_GetStarted;

public class Fluid2dDisplayActivity extends AppCompatActivity {
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
                sketch = new Fluid_Basic();
                break;

            case 1:
                sketch = new Fluid_CustomParticles();
                break;

            case 2:
                sketch = new Fluid_FirstBlood();
                break;

            case 3:
                sketch = new Fluid_GetStarted();
                break;
            case 4:

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
