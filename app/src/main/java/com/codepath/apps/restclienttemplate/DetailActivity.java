package com.codepath.apps.restclienttemplate;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {

    TextView tvView1;
    TextView tvView2;
    ImageView imageView2;

    Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvView1 = findViewById(R.id.tvView1);
        tvView2 = findViewById(R.id.tvView2);
        imageView2 = findViewById(R.id.imageView2);

        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        tvView1.setText(tweet.user.screenName);
        tvView2.setText(tweet.boby);
        Glide.with(DetailActivity.this).load(tweet.user.profileImageUrl).into(imageView2);

    }
}
