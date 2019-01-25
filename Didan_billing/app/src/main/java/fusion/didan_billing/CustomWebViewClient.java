/** Class is needed to display the WebMoney page in the context called its activity
 @author fusion
 @version 1.0
 */

package fusion.didan_billing;

import android.app.Activity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CustomWebViewClient extends WebViewClient {

    private Activity activity = null;

    public CustomWebViewClient(Activity activity) {
        this.activity = activity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        return false;
    }
}