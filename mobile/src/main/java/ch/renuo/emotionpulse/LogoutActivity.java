package ch.renuo.emotionpulse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;

public class LogoutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ParseInitializer.init(this);

        setContentView(R.layout.activity_logout);

        Button mEmailSignInButton = (Button) findViewById(R.id.action_logout_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        Button mLogEmotionButton = (Button) findViewById(R.id.action_log_emotion_button);
        mLogEmotionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logEmotion(77, "application", "context");
            }
        });
    }

    private void logEmotion(int heartBeat, String application, String context) {
        String message;
        try {
            new EmotionService().store(new Emotion(heartBeat, application, context));
            message = "Sucessfully logged emotion!";
        } catch (ParseException e) {
            message = e.getLocalizedMessage();
        }
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void logout() {
        ParseUser.logOut();
        Toast.makeText(getApplicationContext(), "Logout successful", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
