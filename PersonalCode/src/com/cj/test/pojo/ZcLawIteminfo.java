package com.cj.test.pojo;

public class ZcLawIteminfo implements Cloneable {
	private String rowguid;
	private String lawInfoGuid;
	private String itemName;
	private String itemContent;
	private String parentItemGuid;
	private int itemLevel;
	private int status;

	@Override
	public String toString() {
		return "ZcLawIteminfo[rowguid=" + rowguid + ",lawInfoGuid=" + lawInfoGuid + ",itemName=" + itemName
				+ ",itemContent=" + itemContent + ",parentItemGuid" + parentItemGuid + ",itemLevel" + itemLevel
				+ ",status=" + status + "]";
	}

	public String getRowguid() {
		return rowguid;
	}

	public void setRowguid(String rowguid) {
		this.rowguid = rowguid;
	}

	public String getLawInfoGuid() {
		return lawInfoGuid;
	}

	public void setLawInfoGuid(String lawInfoGuid) {
		this.lawInfoGuid = lawInfoGuid;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemContent() {
		return itemContent;
	}

	public void setItemContent(String itemContent) {
		this.itemContent = itemContent;
	}

	public String getParentItemGuid() {
		return parentItemGuid;
	}

	public void setParentItemGuid(String parentItemGuid) {
		this.parentItemGuid = parentItemGuid;
	}

	public int getItemLevel() {
		return itemLevel;
	}

	public void setItemLevel(int itemLevel) {
		this.itemLevel = itemLevel;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
