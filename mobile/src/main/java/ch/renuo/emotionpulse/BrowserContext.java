package ch.renuo.emotionpulse;

import android.view.accessibility.AccessibilityEvent;

/**
 * Created by digi on 10/11/14.
 */
public class BrowserContext {

    private String url = null;
    private String app = null;

    public boolean hasUrl() {
        return url != null && url.length() > 0;
    }

    public String getUrl() {
        return url;
    }

    private void setUrl(String url) {
        if (url == null || url.length() == 0) {
            this.url = null;
            return;
        }

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        this.url = url;
    }

    public void updateContext(AccessibilityEvent event) {
        if (event.getText().size() == 1 && !event.getText().toString().equals("Browser") && !event.getText().toString().equals("Chrome") && !event.getText().toString().equals("http://Chrome")) {
            setUrl(event.getText().get(0).toString());
        }
        app = event.getPackageName().toString();
    }

    public String getApp() {
        return app;
    }
}
