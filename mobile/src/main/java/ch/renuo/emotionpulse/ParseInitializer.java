package ch.renuo.emotionpulse;

import android.content.Context;

import com.parse.Parse;
import com.parse.ParseUser;

/**
 * Created by lukas on 11/Oct/14.
 */
public class ParseInitializer {
    public static void init(Context context) {
        Parse.initialize(context, "miopZzhX2hAKGjpr5O7PPi0IqZGGMlMRNyKtql8F", "OqJ7Xzsn63jCbeC4Aa1amh6dTe5PduYPiYuhg7Qf");
        ParseUser.enableAutomaticUser();
    }
}
