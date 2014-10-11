package ch.renuo.emotionpulse;

/**
 * Created by lukas on 11/Oct/14.
 */
public class Emotion {
    private final int heartBeat;
    private final String application;
    private final String context;

    public Emotion(int heartBeat, String application, String context) {
        this.heartBeat = heartBeat;
        this.application = application;
        this.context = context;
    }

    public int getHeartBeat() {
        return heartBeat;
    }

    public String getApplication() {
        return application;
    }

    public String getContext() {
        return context;
    }
}
