package com.example.pc.challenge08;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private int setButton = 0;
    private Button handle;
    private Animation inAnim;
    private Animation outAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handle = findViewById(R.id.handle);

        final LinearLayout webSearch = findViewById(R.id.webSearch);
        outAnim = AnimationUtils.loadAnimation(this,R.anim.outanim);
        inAnim = AnimationUtils.loadAnimation(this,R.anim.inanim);
        Button webButton = (Button)findViewById(R.id.webButton);
        final WebView webView = (WebView)findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        final EditText webAddress = (EditText)findViewById(R.id.webAddress);

        webButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = webAddress.getText().toString();
                webView.loadUrl(address);
                webAddress.setText("");
            }
        });

        handle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setButton==0){
                    webSearch.startAnimation(inAnim);
                    webSearch.setVisibility(View.VISIBLE);
                    handle.setText("↑");
                    setButton=1;
                }else{
                    handle.setText("↓");
                    webSearch.startAnimation(outAnim);
                    webSearch.setVisibility(View.GONE);
                    setButton=0;
                }
            }
        });
    }
}
