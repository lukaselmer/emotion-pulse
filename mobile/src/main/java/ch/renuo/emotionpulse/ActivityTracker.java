package ch.renuo.emotionpulse;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.parse.ParseException;


/**
 * Created by digi on 10/11/14.
 */
public class ActivityTracker extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event.getRecordCount() > 0 || event.getSource() != null) {
            Log.d(ACCESSIBILITY_SERVICE, event.toString());
        }

        try {
            // Now send it!
            new EmotionService().store(new Emotion(PulseSource.getPulse(), event.getPackageName().toString(), ContextSource.getContext()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceConnected() {
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_VIEW_SCROLLED | AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED | AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;
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
