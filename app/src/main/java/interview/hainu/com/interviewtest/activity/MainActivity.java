package interview.hainu.com.interviewtest.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import interview.hainu.com.interviewtest.R;
import interview.hainu.com.interviewtest.view.MyToggleButton;

public class MainActivity extends Activity {

    private MyToggleButton main_toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_toggle = (MyToggleButton) findViewById(R.id.main_toggle);

        main_toggle.setSlidBackgroundResource(R.drawable.progress_thumb);
        main_toggle.setSwitchBackgroundResourceOn(R.drawable.toggle_bg_on);
        main_toggle.setSwitchBackgroundResourceOff(R.drawable.toggle_bg_off);
        main_toggle.setToggleState(MyToggleButton.MyToggleState.Open);

        main_toggle.setOnTaggleStateChangeListener(new MyToggleButton.OnTaggleStateChangeListener() {
            @Override
            public void onStateChange(MyToggleButton.MyToggleState state) {
                String tip = state == MyToggleButton.MyToggleState.Open ? "开启" : "关闭";
                Toast.makeText(MainActivity.this,tip,Toast.LENGTH_SHORT).show();
            }
        });

    }
}
