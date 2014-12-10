package edu.sjsu.cmpe.prescriptionanalytics.domain;

public class MedicalBillMap {

	String icdCode;
	String icdDescription;
	String cptCode;
	String cptDescription;
	String charge;
	
	public MedicalBillMap(){
		
	}
	
	public void setICDCode(String icdCode){
		this.icdCode = icdCode;
	}
	
	public void setICDDescription(String icdDescription){
		this.icdDescription = icdDescription;
	}
	
	public void setCPTCode(String cptCode){
		this.cptCode = cptCode;
	}
	
	public void setCPTDescription(String cptDescription){
		this.cptDescription = cptDescription;
	}
	
	public void setCharge(String charge){
		this.charge = charge;
	}
	

	public String getICDCode(){
		return icdCode;
	}
	
	public String getICDDescription(){
		return icdDescription;
	}
	
	public String getCPTCode(){
		return cptCode;
	}
	
	public String getCPTDescription(){
		return cptDescription;
	}
	
	public String getCharge(){
		return charge;
	}
	
}
