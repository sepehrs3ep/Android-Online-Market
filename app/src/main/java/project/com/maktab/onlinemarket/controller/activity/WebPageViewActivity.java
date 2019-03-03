package project.com.maktab.onlinemarket.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import project.com.maktab.onlinemarket.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebPageViewActivity extends AppCompatActivity {
    private static final String BASE_URL_WEB_PAGE = "https://woocommerce.maktabsharif.ir/";
    public static final int PROGRESS_MAX = 100;

    @BindView(R.id.online_market_web_view)
    WebView mWebView;
    @BindView(R.id.web_page_progress_bar)
    ProgressBar mProgressBar;


    public static Intent newIntent(Context context){
        return new Intent(context,WebPageViewActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_page_view);
        ButterKnife.bind(this);
         mProgressBar.setMax(PROGRESS_MAX);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(BASE_URL_WEB_PAGE);

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress==PROGRESS_MAX)
                    mProgressBar.setVisibility(View.GONE);
                else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                getSupportActionBar().setSubtitle(title);

            }
        });

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });



    }
}
