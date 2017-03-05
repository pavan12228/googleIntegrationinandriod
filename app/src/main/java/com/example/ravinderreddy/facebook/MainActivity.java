package com.example.ravinderreddy.facebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MainActivity extends FragmentActivity implements View.OnClickListener {


	private GoogleApiClient mGoogleApiClient;
	private int RC_SIGN_IN;
	private String  TAG="pavan";
	private TextView mStatusTextView;
	ImageView imageView;
    // MyApplication myApplication;
	ImageLoader imageLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
		//myApplication=new MyApplication();
		imageLoader=ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		imageView= (ImageView) findViewById(R.id.activity_main_profile_img_iv);
		mStatusTextView= (TextView) findViewById(R.id.activity_main_login_status_tv);
		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
				.requestEmail()
				.build();
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
					@Override
					public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
						Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
					}
				} /* OnConnectionFailedListener */)
				.addApi(Auth.GOOGLE_SIGN_IN_API, gso)
				.build();
		SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
		signInButton.setSize(SignInButton.SIZE_STANDARD);
		signInButton.setOnClickListener(this);

	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.sign_in_button:
				signIn();
				break;
			// ...
		}
	}

	private void signIn() {
		Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
		startActivityForResult(signInIntent, RC_SIGN_IN);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
		if (requestCode == RC_SIGN_IN) {
			GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
			handleSignInResult(result);
		}
	}
	private void handleSignInResult(GoogleSignInResult result) {
		Log.d(TAG, "handleSignInResult:" + result.isSuccess());
		if (result.isSuccess()) {
			// Signed in successfully, show authenticated UI.
			GoogleSignInAccount acct = result.getSignInAccount();
			mStatusTextView.setText( acct.getDisplayName());
		imageLoader.displayImage(acct.getPhotoUrl().toString(),imageView);
			Log.d(TAG,""+acct.getDisplayName());
			//imageView.setImageURI(acct.getPhotoUrl());
			updateUI(true);
		} else {
			// Signed out, show unauthenticated UI.
			updateUI(false);
		}
	}

	private void updateUI(boolean b) {




	}

}
