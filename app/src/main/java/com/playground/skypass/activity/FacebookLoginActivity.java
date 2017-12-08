package com.playground.skypass.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.gson.JsonObject;
import com.playground.skypass.R;
import com.playground.skypass.app.util.GlobalVariable;
import com.playground.skypass.model.ModelBase;
import com.radyalabs.irfan.util.AppUtility;

import org.json.JSONException;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FacebookLoginActivity extends BaseActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback, View.OnClickListener {

    private CallbackManager callbackManager;
    private RelativeLayout login_fb;
    private RelativeLayout skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_facebook_login);

        login_fb = (RelativeLayout) findViewById(R.id.login_fb);
        skip = (RelativeLayout) findViewById(R.id.skip);

        login_fb.setOnClickListener(this);
        skip.setOnClickListener(this);

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {

                final String fbId = loginResult.getAccessToken().getUserId();
                final String fbPPURL = "http://graph.facebook.com/" + fbId + "/picture?type=large";

                GlobalVariable.saveUserPhotoURL(getApplicationContext(), fbPPURL);

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    org.json.JSONObject object,
                                    GraphResponse response) {
                                // Application code

                                String contentRaw = String.valueOf(object);
                                Log.v("LoginActivity", "FB RESPONSE = " + contentRaw);

                                try {
                                    String rawContent = new String(contentRaw);

                                    org.json.JSONObject raw = new org.json.JSONObject(rawContent);
                                    String fbName = raw.getString("name");

                                    GlobalVariable.saveUserEmail(getApplicationContext(), fbId);
                                    GlobalVariable.saveUserName(getApplicationContext(), fbName);
                                    AppUtility.logD("Login", "fb Name = " + fbName);

                                    JsonObject jsonObject = new JsonObject();
                                    jsonObject.addProperty("user_id", fbId);
                                    jsonObject.addProperty("user_photo", fbPPURL);
                                    jsonObject.addProperty("email", "");
                                    jsonObject.addProperty("bio", "");
                                    jsonObject.addProperty("total_xp", 0);
                                    jsonObject.addProperty("total_points", 0);

                                    showLoading();
                                    Call<ModelBase> call = apiService.addMember(jsonObject);
                                    call.enqueue(new Callback<ModelBase>() {
                                        @Override
                                        public void onResponse(Call<ModelBase> call, Response<ModelBase> response) {
                                            dismissLoading();
                                            if (response.isSuccessful()){
                                                if (!response.body().isError()){
                                                    startActivity(new Intent(getApplicationContext(), MainActivity2.class)
                                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                                            .putExtra("first_menu", "EVENT"));

                                                    goToAnimation();
                                                    GlobalVariable.saveIsLogin(getApplicationContext(), true);
                                                    finish();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ModelBase> call, Throwable t) {
                                            dismissLoading();
                                        }
                                    });

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {
            }
        });

//        LoginManager.getInstance().logInWithReadPermissions(FacebookLoginActivity.this, Arrays.asList("public_profile"));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_fb:
                LoginManager.getInstance().logInWithReadPermissions(FacebookLoginActivity.this, Arrays.asList("public_profile", "user_friends", "email"));
                break;

            case R.id.skip:
                final String fbId = "10212652127891641";
                final String fbPPURL = "https://www.1plusx.com/app/mu-plugins/all-in-one-seo-pack-pro/images/default-user-image.png";

                GlobalVariable.saveUserPhotoURL(getApplicationContext(), fbPPURL);
                GlobalVariable.saveUserEmail(getApplicationContext(), fbId);
                GlobalVariable.saveUserName(getApplicationContext(), "Tripvia User");

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("user_id", "");
                jsonObject.addProperty("user_photo", "");
                jsonObject.addProperty("email", "");
                jsonObject.addProperty("bio", "");
                jsonObject.addProperty("total_xp", 0);
                jsonObject.addProperty("total_points", 0);

                showLoading();
                Call<ModelBase> call = apiService.addMember(jsonObject);
                call.enqueue(new Callback<ModelBase>() {
                    @Override
                    public void onResponse(Call<ModelBase> call, Response<ModelBase> response) {
                        dismissLoading();
                        if (response.isSuccessful()){
                            if (!response.body().isError()){
                                startActivity(new Intent(getApplicationContext(), MainActivity2.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                        .putExtra("first_menu", "EVENT"));

                                goToAnimation();
                                GlobalVariable.saveIsLogin(getApplicationContext(), false);
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelBase> call, Throwable t) {
                        dismissLoading();
                    }
                });
                break;

            default:
        }
    }
}
