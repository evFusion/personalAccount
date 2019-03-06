package fusion.didan_billing;

/** This class manage to start payment CRB Banking card activity in called activity
 @author fusion
 @version 1.0
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class BankPaymentActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_payment);
        WebView webBrowser = findViewById(R.id.webBrowser);

        WebSettings webSettings = webBrowser.getSettings();
        webSettings.setJavaScriptEnabled(true);

        CustomWebViewClient webViewClient = new CustomWebViewClient(this);
        webBrowser.setWebViewClient(webViewClient);
        webBrowser.loadUrl("https://sprutpay.ru/service/account?seller=1109");
    }
}