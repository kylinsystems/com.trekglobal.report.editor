/******************************************************************************
 * Copyright (C) 2013 Trek Global                                             *
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package com.trekglobal.report.editor.ui;

import org.compiere.print.MPrintFormatItem;
import org.compiere.util.DisplayType;
import org.compiere.util.Util;
import org.zkoss.zk.ui.HtmlBasedComponent;

/**
 * @author milap
 * @author hengsin
 *
 */
public class PrintFormatEditorUtil {

	public static void addCSSStyle(HtmlBasedComponent comp, String style,
			boolean isAppend) {

		if (!Util.isEmpty(comp.getStyle(), true) && isAppend) {
			comp.setStyle(comp.getStyle() + style);
		} else {
			comp.setStyle(style);
		}
	}

	public static int pointToPixel(float point) {
		return new Float(point * 4 / 3).intValue();
	}

	public static float pixelToPointValue(int pixel) {
		return pixel * 3f / 4f;
	}
	
	public static float inchToPointValue(float inch) {
		return inch * 72;
	}
	
	public static float mmToPointValue(float mm) {
		return inchToPointValue(mm / 25.4f);
	}

	public static String getCSSFontFamily(String fontFamily) {
		if ("Dialog".equals(fontFamily) || "DialogInput".equals(fontFamily)
				|| "SansSerif".equals(fontFamily)) {
			return "sans-serif";
		} else if ("SansSerif".equals(fontFamily)) {
			return "sans-serif";
		} else if ("Serif".equals(fontFamily)) {
			return "serif";
		} else if ("Monospaced".equals(fontFamily)) {
			return "monospace";
		}
		return null;
	}

	public static String getAlignmentStyleCSS(MPrintFormatItem pfItem) {
		String retValue = "left";
		String align = pfItem.getFieldAlignmentType();
		if (align != null) {
			if (align.equalsIgnoreCase("L"))
				retValue = "left";
			else if (align.equalsIgnoreCase("T"))
				retValue = "right";
			else if (align.equals("C"))
				retValue = "center";
		}
		if (align == null || align.equalsIgnoreCase("D")) {
			if (DisplayType.isNumeric(pfItem.getAD_Column()
					.getAD_Reference_ID())) {
				retValue = "right";
			} else {
				retValue = "left";
			}
		}

		return retValue;
	}
	
	public static void setX(MPrintFormatItem pfItem, float xVal)
	{
		if(!pfItem.isRelativePosition())
		{
			pfItem.setXPosition(new Float(xVal).intValue());
		}
		else
			pfItem.setXSpace(new Float(xVal).intValue());
	}
	
	public static void setY(MPrintFormatItem pfItem, float yVal)
	{
		if(!pfItem.isRelativePosition())
		{
			pfItem.setYPosition(new Float(yVal).intValue());
		}
		else
			pfItem.setYSpace(new Float(yVal).intValue());
	}
	
	public static int getX(MPrintFormatItem pfItem)
	{
		int retVal = 0;
		if(!pfItem.isRelativePosition())
		{
			retVal = pfItem.getXPosition();
		}
		else
			retVal = pfItem.getXSpace();
	
		return retVal;
	}
	
	public static int getY(MPrintFormatItem pfItem)
	{
		int retVal = 0;
		if(!pfItem.isRelativePosition())
		{
			retVal = pfItem.getYPosition();
		}
		else
			retVal = pfItem.getYSpace();
	
		return retVal;
	}
}