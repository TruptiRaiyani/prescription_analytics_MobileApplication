package edu.sjsu.cmpe.prescriptionanalytics;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.edu.sjsu.cmpe.prescriptionanalytics.R;

import edu.sjsu.cmpe.prescriptionanalytics.ui.GenerateMedicalBillTable;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends Activity {

	private Button btnTakePicture;
	private Uri fileUri;
	private ImageView imgPreview;
	public static final int CAMERA_CAPTURE_REQUEST_CODE = 101;
	private OCRServiceAPI obj;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		obj = new OCRServiceAPI("TefFtdh8AV");

		imgPreview = (ImageView) findViewById(R.id.imgPreview);

		btnTakePicture = (Button) findViewById(R.id.takeapicture);
		btnTakePicture.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				captureImage();

			}
		});

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		fileUri = savedInstanceState.getParcelable("file_uri");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelable("file_uri", fileUri);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	private boolean isCameraSupported() {
		if (getApplicationContext().getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {

			return true;
		} else {
			return false;
		}
	}

	private void captureImage() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		fileUri = getImageUri();
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		startActivityForResult(intent, CAMERA_CAPTURE_REQUEST_CODE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_CAPTURE_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				previewCaptureImage();
			} else {
				Toast.makeText(getApplicationContext(),
						"Failed to capture image", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void previewCaptureImage() {
		/*imgPreview.setVisibility(View.VISIBLE);
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 8;
		final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
				options);
		imgPreview.setImageBitmap(bitmap);*/
		obj.convertToText("en",fileUri.getPath());
		
		Intent intent = new Intent(this, GenerateMedicalBillTable.class);
		startActivity(intent);
		

	}

	public Uri getImageUri() {
		return Uri.fromFile(getImage());
	}

	private File getImage() {

		String root = Environment.getExternalStorageDirectory().toString();
		File directory = new File(root + "/prescription_analytics");
		if (!directory.exists()) {
			directory.mkdirs();
		}

		String image_format = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());

		File mediaFile = new File(directory.getPath() + "/IMG_" + image_format
				+ ".jpg");

		return mediaFile;
	}

}
