package com.olympus.asset;

public class AssetData {
	private String id;
	private String model;
	private String serialNum;
	private double rentalAmt;
	private double equipPercent;
	private double miscAmt;
	private boolean assetSet;
	
	
	/*************************************************************************************************************************************************/
	public AssetData() { // Constructor	
	}
	/*************************************************************************************************************************************************/
	public boolean isAssetSet() {
		return assetSet;
	}
	public void setAssetSet(boolean assetSet) {
		this.assetSet = assetSet;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public double getRentalAmt() {
		return rentalAmt;
	}
	public void setRentalAmt(double rentalAmt) {
		this.rentalAmt = rentalAmt;
	}
	public double getEquipPercent() {
		return equipPercent;
	}
	public void setEquipPercent(double equipPercent) {
		this.equipPercent = equipPercent;
	}
	public double getMiscAmt() {
		return miscAmt;
	}
	public void setMiscAmt(double miscAmt) {
		this.miscAmt = miscAmt;
	}
	
	/*************************************************************************************************************************************************/

	
	

}

