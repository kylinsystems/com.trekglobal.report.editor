package com.trekglobal.report.editor;

import org.compiere.print.MPrintFormatItem;

public class FieldPosition {
	private MPrintFormatItem pfItem;
	private float x;
	private float y;
	public MPrintFormatItem getPrintFormatItem() {
		return pfItem;
	}
	public void setPrintFormatItem(MPrintFormatItem pfItem) {
		this.pfItem = pfItem;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	
	
}
