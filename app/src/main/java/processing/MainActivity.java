package processing;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import processing.android.PFragment;
import processing.android.CompatUtils;
import processing.core.PApplet;
import processing.softbody.SoftBody2D_Chain;
import processing.softbody.SoftBody2D_Cloth;
import processing.softbody.SoftBody2D_ConnectedBodies;
import processing.softbody.SoftBody2D_DifferentialGrowth;
import processing.softbody.SoftBody2D_DifferentialGrowth2;
import processing.softbody.SoftBody2D_DifferentialGrowth_Closed;
import processing.softbody.SoftBody2D_GetStarted;
import processing.softbody.SoftBody2D_Liquid;
import processing.softbody.SoftBody2D_ParticleCollisionSystem;
import processing.softbody.SoftBody2D_Playground;
import processing.softbody.SoftBody2D_Trees;
import processing.test.sketch_230701b.R;

public class MainActivity extends AppCompatActivity {
    private PApplet sketch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClickButton(View view) {
        switch (view.getId()){
            case R.id.btn_body:
                startActivity(new Intent(MainActivity.this,SoftBodyActivity.class));
                break;
        }
    }
}
