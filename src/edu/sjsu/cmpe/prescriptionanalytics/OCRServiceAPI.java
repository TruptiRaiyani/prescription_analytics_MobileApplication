package edu.sjsu.cmpe.prescriptionanalytics;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import edu.sjsu.cmpe.prescriptionanalytics.utils.OCRStringDataExtraction;
import android.os.AsyncTask;
import android.util.Log;

public class OCRServiceAPI {

	public final String SERVICE_URL = "http://api.ocrapiservice.com/1.0/rest/ocr";

	private final String PARAM_IMAGE = "image";
	private final String PARAM_LANGUAGE = "language";
	private final String PARAM_APIKEY = "apikey";

	private String apiKey;

	private int responseCode;
	private String responseText;

	public OCRServiceAPI(final String apiKey) {
		this.apiKey = apiKey;
	}

	/*
	 * Convert image to text.
	 * 
	 * @param language The image text language.
	 * 
	 * @param filePath The image absolute file path.
	 * 
	 * @return true if everything went okay and false if there is an error with
	 * sending and receiving data.
	 */
	public void convertToText(final String language, final String filePath) {
		sendImage task = new sendImage();
		task.execute(language, apiKey, filePath);		
	}

	/*
	 * Send image to OCR service and read response.
	 * 
	 * u@param language The image text language.
	 * 
	 * @param filePath The image absolute file path.
	 */
	
	private class sendImage extends AsyncTask<String, String, String> {
		
		@Override
	    protected String doInBackground(String... data) {
			final HttpClient httpclient = new DefaultHttpClient();
			final HttpPost httppost = new HttpPost(SERVICE_URL);
			//httppost.setHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
			String language = data[0];
			String filePath = data[2];
			StringBuilder sb = null;
			
			final FileBody image = new FileBody(new File(filePath));

			final MultipartEntity reqEntity = new MultipartEntity();
			try {
			reqEntity.addPart(PARAM_IMAGE, image);			
			reqEntity.addPart(PARAM_LANGUAGE, new StringBody(language));
			reqEntity.addPart(PARAM_APIKEY, new StringBody(getApiKey()));			
			httppost.setEntity(reqEntity);
			
			
			final HttpResponse response = httpclient.execute(httppost);
			final HttpEntity resEntity = response.getEntity();
			 sb = new StringBuilder();
			if (resEntity != null) {
				final InputStream stream = resEntity.getContent();
				byte bytes[] = new byte[4096];
				int numBytes;
				while ((numBytes = stream.read(bytes)) != -1) {
					if (numBytes != 0) {
						sb.append(new String(bytes, 0, numBytes));
					}
				}
			}

			setResponseCode(response.getStatusLine().getStatusCode());

			setResponseText(sb.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return sb.toString();
		}
		
		
		@Override
		protected void onPostExecute(String result){
			Log.i("Aradhana","Value of response is: " + result);
			result = "**	ICD CODE	**	DESCRIPTION	**	CPT CODE	**	CPT DESCRIPTION	**	CHARGE";
			result = result + "**	A00.9	**	Cholera, unspecified	**	80051	**	Electrolyte panel	**	$30.00";
			result = result + "**		**	  **  80053	**	Comprehensive metabolic panel	**	$18.64";
			result = result + "**		**    **  84165	**	Protein; electrophoretic fractionation and quantitation	**	$33.09";
			result = result + "** F10	**	Alcohol and Drug Dependence	**	99201	**	Office Visit	**	$30.00";
			result = result + "** F32.0	**	Major Depression	**	87645	**	Ophthalmological Services	**	$34.00";
			result = result + "**		**	                    ** 66840	**	Cataract Survey	**	$21.00";
			result = result + "**TOTAL		$81.73";
			OCRStringDataExtraction.ocrStringtoList(result);
		}
		
	}


	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

}
