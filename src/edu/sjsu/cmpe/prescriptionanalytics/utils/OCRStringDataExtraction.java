package edu.sjsu.cmpe.prescriptionanalytics.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.util.Log;

public class OCRStringDataExtraction {

	
	public static void ocrStringtoList(String medicalData){
		String[] medicalRecord = medicalData.split("\\*{2}");	
		
		Log.i("Aradhana ", "String Extraction");
		for(int i=0; i<medicalRecord.length; i++){
			Log.i("Aradhana ", i + ": " +  medicalRecord[i]);
			System.out.println( i + ": " +  medicalRecord[i]);
		}
	}
	
	public static void medicalRecordListToHashMap(String[] medicalRecord){
		int sizeOfArray = medicalRecord.length;
		
		String totalPrice = medicalRecord[sizeOfArray - 1];
		
		String[] medicalRecordFormatted = Arrays.copyOfRange(medicalRecord, 6,(sizeOfArray - 2));
		int numberOfRecords = medicalRecordFormatted.length/5;
		
		List<List<String>> splittedArray = new ArrayList<List<String>>();
		
		List<String> recordRow = new ArrayList<String>();
		int count=-1;
		for(int i=0; i<numberOfRecords; i++){			
			for(int j=0;j<5;j++){
				count++;
			recordRow.add(medicalRecordFormatted[count+j]);			
			}
			splittedArray.add(recordRow);			
		}	
		
		
		for(int i=0; i<numberOfRecords; i++){			
			for(int j=0;j<5;j++){					
			recordRow.add(medicalRecordFormatted[i+j]);			
			}
				
		}	
		
	}
}
