package edu.sjsu.cmpe.prescriptionanalytics.ui;

import com.example.edu.sjsu.cmpe.prescriptionanalytics.R;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class GenerateMedicalBillTable extends Activity{
	
	EditText editText1, editText2, editText3, editText4, editText5;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.display_medical_bill);
		TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
		createTableLayout(15, tableLayout);
		
		/*ScrollView sv = new ScrollView(this);
		HorizontalScrollView hsv = new HorizontalScrollView(this);
		hsv.addView(tableLayout);
		sv.addView(hsv);*/
		
		
	}
	
	public void createTableLayout(int r, TableLayout tableLayout){
	
		
		tableLayout.setBackgroundColor(Color.BLACK);
		tableLayout.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		tableLayout.setGravity(Gravity.CENTER);
		
		TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams();
		tableRowParams.setMargins(1, 1, 1,1);
		tableRowParams.weight=1;
		TableRow tableRow;
		
		
		
		for(int i=0;i<(r+1);i++){
			tableRow = new TableRow(getApplicationContext());
			
			if(i==0){
			
				TextView textView1 = new TextView(this);
				textView1.setText("ICD Code");
				textView1.setBackgroundColor(Color.WHITE);
				textView1.setGravity(Gravity.CENTER);
				textView1.setTypeface(null,  Typeface.BOLD);
				
				TextView textView2= new TextView(this);
				textView2.setText("ICD Description");
				textView2.setBackgroundColor(Color.WHITE);
				textView2.setGravity(Gravity.CENTER);
				textView2.setTypeface(null,  Typeface.BOLD);
				
				TextView textView3 = new TextView(this);
				textView3.setText("CPT Code");
				textView3.setBackgroundColor(Color.WHITE);
				textView3.setGravity(Gravity.CENTER);
				textView3.setTypeface(null,  Typeface.BOLD);
				
				TextView textView4 = new TextView(this);
				textView4.setText("CPT Description");
				textView4.setBackgroundColor(Color.WHITE);
				textView4.setGravity(Gravity.CENTER);
				textView4.setTypeface(null,  Typeface.BOLD);
				
				TextView textView5 = new TextView(this);
				textView5.setText("Charge");
				textView5.setBackgroundColor(Color.WHITE);
				textView5.setGravity(Gravity.CENTER);
				textView5.setTypeface(null,  Typeface.BOLD);
				
				tableRow.addView(textView1);
				tableRow.addView(textView2);
				tableRow.addView(textView3);
				tableRow.addView(textView4);
				tableRow.addView(textView5);
				tableLayout.addView(tableRow);
				
				continue;
			}
			
			editText1 = new EditText(this);
			editText1.setBackgroundColor(Color.WHITE);
			editText1.setEnabled(false);
			editText1.setText("Alcohol and Drug");
			
			editText2 = new EditText(this);
			editText2.setBackgroundColor(Color.WHITE);
			editText2.setEnabled(false);
			editText2.setText("F10");
			
			editText3 = new EditText(this);
			editText3.setBackgroundColor(Color.WHITE);
			editText3.setEnabled(false);
			editText3.setText("99201");	
			
			editText4 = new EditText(this);
			editText4.setBackgroundColor(Color.WHITE);
			editText4.setEnabled(false);
			editText4.setText("Psych Visit");	
			
			editText5 = new EditText(this);
			editText5.setBackgroundColor(Color.WHITE);
			editText5.setEnabled(false);
			editText5.setText("23.56");	
			
			Button edit = new Button(this);
			edit.setText("Edit");
			edit.setOnClickListener(new OnClickListener() {   
				
				@Override
				public void onClick(View arg0) {
					editText1.setEnabled(true);
					editText2.setEnabled(true);
					editText3.setEnabled(true);
					editText4.setEnabled(true);
					editText5.setEnabled(true);
					
					
				}
        });
			
			tableRow.addView(editText1);
			tableRow.addView(editText2);
			tableRow.addView(editText3);
			tableRow.addView(editText4);
			tableRow.addView(editText5);
			tableRow.addView(edit);
			tableLayout.addView(tableRow);
			
			
		}	
	
	}

}
