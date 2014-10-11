package ch.renuo.emotionpulse;

import com.parse.ParseException;
import com.parse.ParseObject;

/**
 * Created by lukas on 11/Oct/14.
 */
public class EmotionService {
    public void store(Emotion emotion) throws ParseException {
        ParseObject o = new ParseObject("Emotion");
        o.put("heartBeat", emotion.getHeartBeat());
        o.put("application", emotion.getApplication());
        o.put("context", emotion.getContext());
        o.save();
    }
}
