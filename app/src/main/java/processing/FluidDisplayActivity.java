package processing;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import processing.android.CompatUtils;
import processing.android.PFragment;
import processing.core.PApplet;
import processing.fluid.FlowField_LIC;
import processing.fluid.FlowField_LIC_Image;
import processing.softbody.SoftBody2D_Chain;
import processing.softbody.SoftBody2D_Cloth;
import processing.softbody.SoftBody2D_ConnectedBodies;
import processing.softbody.SoftBody2D_DifferentialGrowth;
import processing.softbody.SoftBody2D_DifferentialGrowth2;
import processing.softbody.SoftBody2D_DifferentialGrowth_Closed;
import processing.softbody.SoftBody2D_DifferentialGrowth_Open;
import processing.softbody.SoftBody2D_GetStarted;
import processing.softbody.SoftBody2D_Liquid;
import processing.softbody.SoftBody2D_ParticleCollisionSystem;
import processing.softbody.SoftBody2D_Playground;
import processing.softbody.SoftBody2D_Trees;

public class FluidDisplayActivity extends AppCompatActivity {
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
                sketch = new FlowField_LIC();
                break;
            case 1:
                sketch = new FlowField_LIC_Image();
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
