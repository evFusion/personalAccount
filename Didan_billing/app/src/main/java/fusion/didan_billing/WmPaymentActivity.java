/** This class manage to start payment WM activity in called activity
 @author fusion
 @version 1.0
 */

package fusion.didan_billing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WmPaymentActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wm_payment);
        WebView webBrowser = findViewById(R.id.webBrowser);
        //webBrowser.loadUrl("https://mini.webmoney.ru"); // Open system browser

        WebSettings webSettings = webBrowser.getSettings();
        webSettings.setJavaScriptEnabled(true);

        CustomWebViewClient webViewClient = new CustomWebViewClient(this);
        webBrowser.setWebViewClient(webViewClient);
        webBrowser.loadUrl("https://mini.webmoney.ru");
    }
}
