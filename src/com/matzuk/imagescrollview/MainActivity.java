package com.matzuk.imagescrollview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	private AvatarScrollView mImageScrollView;
	private ImageView avatar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// initialize ImageScrollView
		mImageScrollView = (AvatarScrollView) findViewById(R.id.contact_scroll_view);
        // initialize ImageView
        avatar = (ImageView) findViewById(R.id.contact_info_avatar);
        // call setResizableImage function
        mImageScrollView.setAvatar(avatar);
	}
}
