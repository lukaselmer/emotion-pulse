package ch.renuo.emotionpulse;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;


/**
 * Created by digi on 10/11/14.
 */
public class ActivityTracker extends AccessibilityService {

    private final EmotionService mEmotionService = new EmotionService();
    private BrowserContext mBrowserContext;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (mBrowserContext == null) mBrowserContext = new BrowserContext();

        mBrowserContext.updateContext(event);

        PulseSource p = new PulseSource();

        ParseInitializer.init(getApplicationContext());

        if (!ParseUser.getCurrentUser().isAuthenticated() || ParseUser.getCurrentUser().getEmail() == null) {
            Toast.makeText(getApplicationContext(), "Please login to the Emotion Pulse app", Toast.LENGTH_LONG);
            return;
        }

        if (!mBrowserContext.hasUrl()) return;

        try {
            // Now send it!
            mEmotionService.store(new Emotion(p.getPulse(), mBrowserContext.getApp(), mBrowserContext.getUrl()));
        } catch (ParseException e) {
            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG);
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceConnected() {
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        info.notificationTimeout = 0;
        info.packageNames = new String[]{"com.android.browser", "com.google.android.browser", "com.android.chrome"};

        // Set the type of feedback your service will provide.
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;

        // Default services are invoked only if no package-specific ones are present
        // for the type of AccessibilityEvent generated.  This service *is*
        // application-specific, so the flag isn't necessary.  If this was a
        // general-purpose service, it would be worth considering setting the
        // DEFAULT flag.

        // info.flags = AccessibilityServiceInfo.DEFAULT;

        info.notificationTimeout = 100;

        setServiceInfo(info);
    }

    @Override
    public void onInterrupt() {

    }
}
