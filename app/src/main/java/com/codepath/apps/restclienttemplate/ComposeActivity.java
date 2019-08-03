package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    public  static final int MAX_TWEET_LENGTH = 140;

    private EditText etcompose;
    private Button btnTweet;
    private TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        client = TwitterApp.getRestClient(this);
        etcompose = findViewById(R.id.etCompose);
        btnTweet = findViewById(R.id.btnTweet);
        
        // Set click listener on button
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String TweetContent  = etcompose.getText().toString();
              // Error
                if (TweetContent.isEmpty()){
                    Toast.makeText(ComposeActivity.this,"Your tweet is empty!", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TweetContent.length()> 140){
                    Toast.makeText(ComposeActivity.this, "Your tweet is too long",Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(ComposeActivity.this,TweetContent,Toast.LENGTH_LONG).show();

                // Make API call to twiller to publish the content in edit text
                client.composeTweet(TweetContent,new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.d("TwitterClient", "Succesfully posted tweet!" + response.toString());
                        try {
                            Tweet tweet = Tweet.fromJson(response);
                            Intent data = new Intent();
                            data.putExtra("tweet", Parcels.wrap(tweet));
                            setResult(RESULT_OK, data);
                            // closes the activity , pass data to parent
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.e("TwitterClient","Failed to post tweet:" + responseString);
                    }
                });


            }
        });
    }
}
