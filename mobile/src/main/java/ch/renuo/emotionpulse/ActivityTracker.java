package ch.renuo.emotionpulse;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;


/**
 * Created by digi on 10/11/14.
 */
public class ActivityTracker  extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        final int eventType = event.getEventType();
        String eventText = null;
        switch(eventType) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                eventText = "Focused: ";
                break;
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                eventText = "Focused: ";
                break;
        }

        eventText = eventText + event.getContentDescription();

    }

    @Override
    public void onInterrupt() {

    }
}
