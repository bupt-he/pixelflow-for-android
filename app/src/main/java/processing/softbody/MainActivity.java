package processing.softbody;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import processing.SoftBodyActivity;
import processing.core.PApplet;
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
                startActivity(new Intent(MainActivity.this, SoftBodyActivity.class));
                break;

            case R.id.btn_sky:
                startActivity(new Intent(MainActivity.this,SkyActivity.class));
                break;
        }
    }
}
