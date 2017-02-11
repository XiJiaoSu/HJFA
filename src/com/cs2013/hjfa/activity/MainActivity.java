package com.cs2013.hjfa.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cs2013.hjfa.R;
import com.cs2013.hjfa.R.id;
import com.cs2013.hjfa.R.layout;
import com.cs2013.hjfa.views.CoordinatorMenu;

public class MainActivity extends Activity {

	private ImageView mHeadIv;
	private CoordinatorMenu mCoordinatorMenu;

	private LinearLayout mLLOne = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mHeadIv = (ImageView) findViewById(R.id.iv_head);
		mCoordinatorMenu = (CoordinatorMenu) findViewById(R.id.menu);

		mHeadIv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mCoordinatorMenu.isOpened()) {
					mCoordinatorMenu.closeMenu();
				} else {
					mCoordinatorMenu.openMenu();
				}
			}
		});
		initViews();
	}

	private void initViews() {
		mLLOne = (LinearLayout) findViewById(R.id.ll_one);
		mLLOne.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, "µã»÷ÊÂ¼þ", Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	@Override
	public void onBackPressed() {
		if (mCoordinatorMenu.isOpened()) {
			mCoordinatorMenu.closeMenu();
		} else {
			super.onBackPressed();
		}
	}

}
