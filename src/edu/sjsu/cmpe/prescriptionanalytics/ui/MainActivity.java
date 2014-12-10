package edu.sjsu.cmpe.prescriptionanalytics.ui;

import android.app.Activity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.edu.sjsu.cmpe.prescriptionanalytics.R;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class Login extends Activity {

	String emailid;
	String userpassword;

	TextView emailID, forgotpw, newaccount;
	TextView password;
	Button loginButton;

	public void validateAccount(JSONObject result) {
		String pass = null;

		try {
			pass = result.getString("password");
			emailid = result.getString("emailid");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (pass.equals(userpassword)) {

			Intent i = new Intent(getBaseContext(),
					GenerateMedicalBillTable.class);
			i.putExtra("UserName", emailid);
			startActivity(i);

		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		emailID = (TextView) findViewById(R.id.editText1);
		password = (TextView) findViewById(R.id.editText2);
		loginButton = (Button) findViewById(R.id.button1);
		forgotpw = (TextView) findViewById(R.id.loginforgotpw);
		newaccount = (TextView) findViewById(R.id.logincreatenewaccount);

		/*
		 * newaccount.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) {
		 * 
		 * Intent intent = new Intent(getBaseContext(), Register.class);
		 * startActivity(intent); } });
		 */

		// Listening to register new account link
		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				emailid = emailID.getText().toString();
				userpassword = password.getText().toString();

				if (emailid.equals("") || emailid == null) {
					Toast.makeText(getApplicationContext(),
							"Please enter your username", Toast.LENGTH_SHORT)
							.show();
				}

				else if (password.equals("") || password == null) {
					Toast.makeText(getApplicationContext(),
							"Please enter your password", Toast.LENGTH_SHORT)
							.show();
				} else {
					JSONObject request = new JSONObject();

					try {
						request.put("emailID", emailid);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					LoginTask task = new LoginTask();
					task.execute(request);

				}
			}
		});
	}

	private class LoginTask extends AsyncTask<JSONObject, Integer, JSONObject> {
		boolean flag = false;

		@Override
		protected JSONObject doInBackground(JSONObject... params) {

			JSONArray jsonarray = null;
			JSONObject jsonobject = null;

			String originalUrl = "http://192.168.56.1:3000/Login";
			String url = originalUrl + '/' + emailid;

			String sret = "";
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(url);
			request.addHeader("Cache-Control", "no-cache");

			try {
				HttpResponse response = client.execute(request);
				HttpEntity httpEntity = response.getEntity();
				InputStream in = httpEntity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in));
				StringBuilder str = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					str.append(line + "\n");
				}
				in.close();
				sret = str.toString();
				if (sret != null) {
					System.out.println("String: " + sret);
					flag = true;
					jsonarray = new JSONArray(sret);

					for (int i = 0; i < jsonarray.length(); i++) {
						jsonobject = jsonarray.getJSONObject(i);
						String username = jsonobject.getString("UserName");
						String password = jsonobject.getString("password");
						String type = jsonobject.getString("UserType");
						// String category = jsonobject.getString("category");
					}

				}
			} catch (JSONException e) {
				Log.e("JSON Parser", "Error parsing data " + e.toString());
			} catch (Exception ex) {
				ex.printStackTrace();
				Log.e("error", ex.toString());
				sret = "Error";
			}

			return jsonobject;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			if (flag == true)
				validateAccount(result);
		}
	}

}
