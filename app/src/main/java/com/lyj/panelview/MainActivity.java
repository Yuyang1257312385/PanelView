package com.lyj.panelview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lyj.panel.PanelView;

public class MainActivity extends AppCompatActivity {

    private PanelView mPanelView;
    private Button mStartBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPanelView = (PanelView) findViewById(R.id.view_panel);
        mStartBtn = (Button) findViewById(R.id.btn_start);
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPanelView.setMaxValue(5000);
                mPanelView.setCurrentValue(4000);
                mPanelView.setMaxDuration(5000);
                mPanelView.setStepColorList(new String[]{"#ff0000","#00ff00","#0000ff"});
                mPanelView.start();
            }
        });
    }
}
