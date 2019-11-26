/**
 * 
 */
package com.trekglobal.report.editor.action;

import org.adempiere.webui.action.IAction;
import org.adempiere.webui.adwindow.ADWindow;
import org.compiere.util.Env;

import com.trekglobal.report.editor.ui.WPrintFormatEditor;

/**
 * @author hengsin
 *
 */
public class EditorAction implements IAction {

	/**
	 * 
	 */
	public EditorAction() {
	}

	/* (non-Javadoc)
	 * @see org.adempiere.webui.action.IAction#execute(java.lang.Object)
	 */
	@Override
	public void execute(Object target) {
		ADWindow adwindow = (ADWindow) target;
		int windowNo = adwindow.getADWindowContent().getWindowNo();
		int AD_PrintFormat_ID = Env.getContextAsInt(Env.getCtx(), windowNo, "AD_PrintFormat_ID", true);
		if (AD_PrintFormat_ID > 0) {
			WPrintFormatEditor editor = new WPrintFormatEditor(AD_PrintFormat_ID, adwindow.getADWindowContent());
			editor.showEditorWindow();
		}
	}

}
