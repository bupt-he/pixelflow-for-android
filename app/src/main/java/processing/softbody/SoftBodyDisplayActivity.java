package processing.softbody;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import processing.SoftBodyActivity;
import processing.android.CompatUtils;
import processing.android.PFragment;
import processing.core.PApplet;

public class SoftBodyDisplayActivity extends AppCompatActivity {
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
                sketch = new SoftBody2D_Chain();
                break;
            case 1:
                sketch = new SoftBody2D_Cloth();
                break;
            case 2:
                sketch = new SoftBody2D_ConnectedBodies();
                break;
            case 3:
                sketch = new SoftBody2D_DifferentialGrowth();
                break;
            case 4:
                sketch = new SoftBody2D_DifferentialGrowth2();
                break;
            case 5:
                sketch = new SoftBody2D_DifferentialGrowth_Closed();
                break;
            case 6:
                sketch = new SoftBody2D_DifferentialGrowth_Open();
                break;
            case 7:
                sketch = new SoftBody2D_GetStarted();
                break;
            case 8:
                sketch = new SoftBody2D_Liquid();
                break;
            case 9:
                sketch = new SoftBody2D_ParticleCollisionSystem();
                break;
            case 10:
                sketch = new SoftBody2D_Playground();
                break;
            case 11:
                sketch = new SoftBody2D_Trees();
                break;
            case 12:
                sketch = new Softbody3D_Cloth();
                break;
            case 13:
                sketch = new Softbody3D_ParticleCollisionSystem();
                break;
            case 14:
                sketch = new Softbody3D_Playground();
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
