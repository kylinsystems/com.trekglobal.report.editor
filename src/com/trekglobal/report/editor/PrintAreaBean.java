/**
 * 
 */
package com.trekglobal.report.editor;

/**
 * @author hengsin
 *
 */
public class PrintAreaBean {

	private float pointX;
	private float pointY;
	private float currentX;
	private float currentY;

	public PrintAreaBean(int areaType, float x, float y) {
		super();
		this.currentX = x;
		this.currentY = y;
		pointX = x;
		pointY = y;
	}

	public float getPointX() {
		return pointX;
	}

	public void setPointX(float pointX) {
		this.pointX = pointX;
	}

	public float getPointY() {
		return pointY;
	}

	public void setPointY(float pointY) {
		this.pointY = pointY;
	}

	public float getCurrentX() {
		return currentX;
	}

	public void setCurrentX(float currentX) {
		this.currentX = currentX;
	}

	public float getCurrentY() {
		return currentY;
	}

	public void setCurrentY(float currentY) {
		this.currentY = currentY;
	}

}
