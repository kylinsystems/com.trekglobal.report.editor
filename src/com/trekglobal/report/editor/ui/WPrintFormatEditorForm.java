/******************************************************************************
 * Copyright (C) 2012 Trek Global                                             *
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

import org.adempiere.webui.LayoutUtils;
import org.adempiere.webui.component.Column;
import org.adempiere.webui.component.ConfirmPanel;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Group;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListHead;
import org.adempiere.webui.component.ListHeader;
import org.adempiere.webui.component.Listbox;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.Window;
import org.adempiere.webui.editor.WEditor;
import org.adempiere.webui.editor.WNumberEditor;
import org.adempiere.webui.editor.WStringEditor;
import org.adempiere.webui.editor.WTableDirEditor;
import org.adempiere.webui.editor.WYesNoEditor;
import org.compiere.model.MColumn;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.print.MPrintFormatItem;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Absolutelayout;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Center;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Div;
import org.zkoss.zul.East;
import org.zkoss.zul.Separator;
import org.zkoss.zul.South;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.West;


public class WPrintFormatEditorForm extends Window
{
	/**
	 *
	 */
	private static final long serialVersionUID = -2533099650671242190L;

	private WPrintFormatEditor pfe;

	protected Borderlayout mainLayout = new Borderlayout();
	private ConfirmPanel confirmPanel = new ConfirmPanel(true, false, true, false, false, false);
	protected Listbox printedItemListbox = new Listbox();
	protected Listbox nonPrintedItemListbox = new Listbox();
	private Div centerDiv;
	private Vlayout westPaneLayout;
	protected Absolutelayout printLayout = null;
	
	protected WEditor editorName = null;
	protected WEditor editorSeqNo = null;
	protected WEditor fieldAlignment = null;
	protected WEditor maxWidth = null;
	protected WEditor maxHeight = null;
	protected WEditor fixedWidth = null;
	protected WEditor printFont = null;
	protected WEditor imageField = null;
	protected WEditor imageAttached = null;
	protected WEditor imageUrl = null;
	protected WEditor lineAlignment = null;
	protected WEditor lineWidth = null;
	protected WEditor includedPrintFormat = null;
	protected WEditor shapeType = null;
	protected WEditor oneLineOnly = null;
	protected WEditor nextLine = null;
	protected WEditor nextPage = null;
	protected WEditor xSpace = null;
	protected WEditor ySpace = null;
	protected WEditor xPosition = null;
	protected WEditor yPosition = null;
	protected WYesNoEditor relativePosition = null;
	protected WEditor setNLPosition = null;
	protected WEditor supressNull = null;
	protected WEditor printColor = null;
	protected WEditor fillShape = null;
	protected WEditor arcDiameter = null;
	protected WEditor printAreaType = null;
	protected WEditor editorTestValue = null;
	
	public WPrintFormatEditorForm(WPrintFormatEditor printFormatEditor) {
		pfe = printFormatEditor;
	}
	
	/**
	 * Static init
	 * 
	 * @throws Exception
	 */
	public void init() throws Exception {
		setSizable(false);
		setClosable(true);
		setMaximizable(true);
		setMaximized(true);
		appendChild(mainLayout);
		LayoutUtils.addSclass("tab-editor-form-content", mainLayout);
		setBorder("normal");

		confirmPanel.addActionListener(Events.ON_CLICK, pfe);

		Borderlayout eastPaneLayout = new Borderlayout();

		Center eastPaneCenter = new Center();
		Grid propGrid = initPropertiesGrid();
		propGrid.setVflex("1");
		eastPaneCenter.appendChild(propGrid);
		eastPaneLayout.appendChild(eastPaneCenter);		
		eastPaneLayout.setVflex("1");
		South south = new South();
		eastPaneLayout.appendChild(south);
		confirmPanel.setVflex("min");
		confirmPanel.setHflex("1");
		south.appendChild(confirmPanel);
		eastPaneLayout.setWidth("100%");
		//eastPaneLayout.setHeight("100%");
		
		
		East east = new East();
		LayoutUtils.addSclass("tab-editor-form-east-panel", east);
		mainLayout.appendChild(east);
		east.appendChild(eastPaneLayout);
		east.setWidth("25%");

		Label formatItemLabel = new Label();
		ListHead printedHead = new ListHead();
		printedHead.setParent(printedItemListbox);
		ListHeader visibleHeader = new ListHeader();
		formatItemLabel.setText("Displayed");
		visibleHeader.appendChild(formatItemLabel);
		visibleHeader.setParent(printedHead);

		ListHead nonPrintedHead = new ListHead();
		nonPrintedHead.setParent(nonPrintedItemListbox);
		ListHeader nonPrintedHeader = new ListHeader();
		nonPrintedHeader.appendChild(new Label("Not Displayed"));
		nonPrintedHeader.setParent(nonPrintedHead);

		westPaneLayout = new Vlayout();
		westPaneLayout.setHeight("100%");
		westPaneLayout.appendChild(printedItemListbox);
		printedItemListbox.setVflex("1");
		westPaneLayout.appendChild(nonPrintedItemListbox);
		nonPrintedItemListbox.setVflex("1");

		printedItemListbox.addEventListener(Events.ON_DROP, pfe);
		nonPrintedItemListbox.addEventListener(Events.ON_DROP, pfe);

		West west = new West();
		LayoutUtils.addSclass("tab-editor-form-west-panel", west);
		mainLayout.appendChild(west);
		west.appendChild(westPaneLayout);
		west.setCollapsible(true);
		west.setSplittable(true);
		west.setWidth("200px");

		Center center = new Center();

		centerDiv = new Div();
		PrintFormatEditorUtil
				.addCSSStyle(
						centerDiv,
						"border: dashed 1px black; overflow: auto; background-color: grey;",
						false);
		centerDiv.setHeight("100%");

		Div previewDiv = new Div();
		previewDiv.setHeight(pfe.paperHeight + "pt");
		previewDiv.setWidth(pfe.paperWidth + "pt");

		printLayout = new Absolutelayout();
		PrintFormatEditorUtil.addCSSStyle(printLayout,
				" border: 1px black solid; background-color:#E1E1E1;", false);

		printLayout.setWidth((pfe.paperWidth + "pt"));
		printLayout.setHeight((pfe.paperHeight + "pt"));
		printLayout.setDroppable("true");
		printLayout.addEventListener(Events.ON_DROP, pfe);

		previewDiv.appendChild(printLayout);
		centerDiv.appendChild(previewDiv);
		center.appendChild(centerDiv);

		mainLayout.appendChild(center);
		setCtrlKeys("#left#right#up#down#del");
		addEventListener(Events.ON_CTRL_KEY, pfe);				
	} 
	
	/**
	 * Initialize Grid of Field's Properties return @Grid
	 */
	private Grid initPropertiesGrid() {
		Grid gridView = GridFactory.newGridLayout();
		//
		Columns columns = new Columns();
		gridView.appendChild(columns);
		//
		Column column = new Column();
		columns.appendChild(column);
		column.setHflex("min");
		column.setAlign("right");

		column = new Column();
		columns.appendChild(column);
		column.setHflex("1");
		Rows rows = new Rows();
		gridView.appendChild(rows);

		Row row = null;

		row = new Row();
		Group group = new Group(Msg.getMsg(Env.getCtx(), "Property"));
		Cell cell = (Cell) group.getFirstChild();
		cell.setSclass("z-group-inner");
		cell.setColspan(2);
		cell.setAlign("left");
		group.setOpen(true);
		rows.appendChild(group);

		row = new Row();
		Label labelName = new Label(Msg.getElement(Env.getCtx(),
				MPrintFormatItem.COLUMNNAME_Name));
		editorName = new WStringEditor(MPrintFormatItem.COLUMNNAME_Name, false,
				false, false, 0, 0, null, null);
		editorName.fillHorizontal();
		row.appendChild(labelName.rightAlign());
		row.appendChild(editorName.getComponent());
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		Label labelTestValue = new Label(Msg.getElement(Env.getCtx(), "TestValue"));
		editorTestValue = new WStringEditor("_TestValue_", false,
				false, true, 0, 0, null, null);
		editorTestValue.fillHorizontal();
		editorTestValue.addValueChangeListener(pfe);
		row.appendChild(labelTestValue.rightAlign());
		row.appendChild(editorTestValue.getComponent());
		row.setGroup(group);
		rows.appendChild(row);
		
		row = new Row();
		Label labelSeqNo = new Label(Msg.getElement(Env.getCtx(),
				MPrintFormatItem.COLUMNNAME_SeqNo));
		editorSeqNo = new WNumberEditor(MPrintFormatItem.COLUMNNAME_SeqNo,
				false, false, false, DisplayType.Integer, labelSeqNo.getValue());
		editorSeqNo.fillHorizontal();
		row.appendChild(labelSeqNo.rightAlign());
		row.appendChild(editorSeqNo.getComponent());
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		Label imgURL = new Label(Msg.getElement(Env.getCtx(),
				MPrintFormatItem.COLUMNNAME_ImageURL));
		imageUrl = new WStringEditor(MPrintFormatItem.COLUMNNAME_ImageURL, false,
				false, false, 0, 0, null, null);
		row.appendChild(imgURL.rightAlign());
		row.appendChild(imageUrl.getComponent());
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		Label labelXPos = new Label(Msg.getElement(Env.getCtx(),
				MPrintFormatItem.COLUMNNAME_XPosition));
		xPosition = new WNumberEditor(MPrintFormatItem.COLUMNNAME_XPosition,
				true, false, true, DisplayType.Integer, labelXPos.getValue());
		xPosition.fillHorizontal();
		row.appendChild(labelXPos.rightAlign());
		row.appendChild(xPosition.getComponent());
		xPosition.addValueChangeListener(pfe);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		Label labelYPos = new Label(Msg.getElement(Env.getCtx(),
				MPrintFormatItem.COLUMNNAME_YPosition));
		yPosition = new WNumberEditor(MPrintFormatItem.COLUMNNAME_YPosition,
				true, false, true, DisplayType.Integer, labelYPos.getValue());
		yPosition.fillHorizontal();
		row.appendChild(labelYPos.rightAlign());
		row.appendChild(yPosition.getComponent());
		yPosition.addValueChangeListener(pfe);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		Label labelXSpace = new Label(Msg.getElement(Env.getCtx(),
				MPrintFormatItem.COLUMNNAME_XSpace));
		xSpace = new WNumberEditor(MPrintFormatItem.COLUMNNAME_XSpace, true,
				false, true, DisplayType.Integer, labelXSpace.getValue());
		xSpace.fillHorizontal();
		row.appendChild(labelXSpace.rightAlign());
		row.appendChild(xSpace.getComponent());
		xSpace.addValueChangeListener(pfe);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		Label labelYSpace = new Label(Msg.getElement(Env.getCtx(),
				MPrintFormatItem.COLUMNNAME_YSpace));
		ySpace = new WNumberEditor(MPrintFormatItem.COLUMNNAME_YSpace, true,
				false, true, DisplayType.Integer, labelYSpace.getValue());
		ySpace.fillHorizontal();
		row.appendChild(labelYSpace.rightAlign());
		row.appendChild(ySpace.getComponent());
		ySpace.addValueChangeListener(pfe);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		Label lblMaxHeight = new Label(Msg.getElement(Env.getCtx(),
				MPrintFormatItem.COLUMNNAME_MaxHeight));
		maxHeight = new WNumberEditor(MPrintFormatItem.COLUMNNAME_MaxHeight,
				true, false, true, DisplayType.Integer, lblMaxHeight.getValue());
		maxHeight.fillHorizontal();
		row.appendChild(lblMaxHeight.rightAlign());
		row.appendChild(maxHeight.getComponent());
		maxHeight.addValueChangeListener(pfe);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		Label lblMaxWidth = new Label(Msg.getElement(Env.getCtx(),
				MPrintFormatItem.COLUMNNAME_MaxWidth));
		maxWidth = new WNumberEditor(MPrintFormatItem.COLUMNNAME_MaxWidth,
				true, false, true, DisplayType.Integer, lblMaxWidth.getValue());
		maxWidth.fillHorizontal();
		row.appendChild(lblMaxWidth.rightAlign());
		row.appendChild(maxWidth.getComponent());
		maxWidth.addValueChangeListener(pfe);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		Label label = new Label(Msg.getElement(Env.getCtx(),
				MPrintFormatItem.COLUMNNAME_ArcDiameter));
		arcDiameter = new WNumberEditor(
				MPrintFormatItem.COLUMNNAME_ArcDiameter, true, false, true,
				DisplayType.Integer, label.getValue());
		arcDiameter.fillHorizontal();
		row.appendChild(label.rightAlign());
		row.appendChild(arcDiameter.getComponent());
		arcDiameter.addValueChangeListener(pfe);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		label = new Label(Msg.getElement(Env.getCtx(),
				MPrintFormatItem.COLUMNNAME_LineWidth));
		lineWidth = new WNumberEditor(MPrintFormatItem.COLUMNNAME_LineWidth,
				true, false, true, DisplayType.Integer, label.getValue());
		lineWidth.fillHorizontal();
		row.appendChild(label.rightAlign());
		row.appendChild(lineWidth.getComponent());
		lineWidth.addValueChangeListener(pfe);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		row.appendChild(new Label(Msg.getElement(
						Env.getCtx(),
						MPrintFormatItem.COLUMNNAME_IsRelativePosition)));
		relativePosition = new WYesNoEditor(
				MPrintFormatItem.COLUMNNAME_IsRelativePosition, "", null,
				false, false, true);
		row.appendChild(relativePosition.getComponent());
		relativePosition.addValueChangeListener(pfe);	
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		row.appendChild(new Label(Msg.getElement(
						Env.getCtx(),
						MPrintFormatItem.COLUMNNAME_IsSuppressNull)));
		supressNull = new WYesNoEditor(
				MPrintFormatItem.COLUMNNAME_IsSuppressNull, "", null,
				false, false, true);
		row.appendChild(supressNull.getComponent());
		supressNull.addValueChangeListener(pfe);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		row.appendChild(new Label(Msg.getElement(
				Env.getCtx(),
				MPrintFormatItem.COLUMNNAME_IsSetNLPosition)));
		setNLPosition = new WYesNoEditor(
				MPrintFormatItem.COLUMNNAME_IsSetNLPosition, "", null,
				false, false, true);
		row.appendChild(setNLPosition.getComponent());
		setNLPosition.addValueChangeListener(pfe);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		row.appendChild(new Label( Msg.getElement(
						Env.getCtx(),
						MPrintFormatItem.COLUMNNAME_IsFilledRectangle)));
		fillShape = new WYesNoEditor(
				MPrintFormatItem.COLUMNNAME_IsFilledRectangle,"", null,
				false, false, true);
		row.appendChild(fillShape.getComponent());
		fillShape.addValueChangeListener(pfe);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		row.appendChild(new Label(Msg.getElement(Env.getCtx(),
						MPrintFormatItem.COLUMNNAME_IsNextPage)));
		nextPage = new WYesNoEditor(MPrintFormatItem.COLUMNNAME_IsNextPage,
				"", null, false,
				false, true);
		row.appendChild(nextPage.getComponent());
		nextPage.addValueChangeListener(pfe);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		row.appendChild(new Label(Msg.getElement(Env.getCtx(),
						MPrintFormatItem.COLUMNNAME_IsNextLine)));
		nextLine = new WYesNoEditor(MPrintFormatItem.COLUMNNAME_IsNextLine,
				"", null, false,
				false, true);
		row.appendChild(nextLine.getComponent());
		nextLine.addValueChangeListener(pfe);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		row.appendChild(new Label(Msg.getElement(
						Env.getCtx(),
						MPrintFormatItem.COLUMNNAME_IsHeightOneLine)));
		oneLineOnly = new WYesNoEditor(
				MPrintFormatItem.COLUMNNAME_IsHeightOneLine, "", null,
				false, false, true);
		row.appendChild(oneLineOnly.getComponent());
		oneLineOnly.addValueChangeListener(pfe);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		row.appendChild(new Label(Msg.getElement(
						Env.getCtx(),
						MPrintFormatItem.COLUMNNAME_ImageIsAttached)));
		imageAttached = new WYesNoEditor(
				MPrintFormatItem.COLUMNNAME_ImageIsAttached, "", null,
				false, false, true);
		row.appendChild(imageAttached.getComponent());
		imageAttached.addValueChangeListener(pfe);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		row.appendChild(new Label(Msg.getElement(Env.getCtx(),
						MPrintFormatItem.COLUMNNAME_IsImageField)));
		imageField = new WYesNoEditor(MPrintFormatItem.COLUMNNAME_IsImageField,
				"", null, false,
				false, true);
		row.appendChild(imageField.getComponent());
		imageField.addValueChangeListener(pfe);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		row.appendChild(new Label(Msg.getElement(Env.getCtx(),
						MPrintFormatItem.COLUMNNAME_IsFixedWidth)));
		fixedWidth = new WYesNoEditor(MPrintFormatItem.COLUMNNAME_IsFixedWidth,
				"", null, false,
				false, true);
		row.appendChild(fixedWidth.getComponent());
		fixedWidth.addValueChangeListener(pfe);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		label = new Label(Msg.getElement(Env.getCtx(),
				MPrintFormatItem.COLUMNNAME_PrintAreaType));
		int columnID = MColumn.getColumn_ID(MPrintFormatItem.Table_Name,
				MPrintFormatItem.COLUMNNAME_PrintAreaType);
		MLookup lookup = MLookupFactory.get(Env.getCtx(), 0, 0, columnID,
				DisplayType.List);
		printAreaType = new WTableDirEditor(
				MPrintFormatItem.COLUMNNAME_PrintAreaType, false, false, true,
				lookup);
		printAreaType.fillHorizontal();
		row.appendChild(label.rightAlign());
		row.appendChild(printAreaType.getComponent());
		printAreaType.addValueChangeListener(pfe);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		label = new Label(Msg.getElement(Env.getCtx(),
				MPrintFormatItem.COLUMNNAME_AD_PrintFont_ID));
		columnID = MColumn.getColumn_ID(MPrintFormatItem.Table_Name,
				MPrintFormatItem.COLUMNNAME_AD_PrintFont_ID);
		lookup = MLookupFactory.get(Env.getCtx(), 0, 0, columnID,
				DisplayType.TableDir);
		printFont = new WTableDirEditor(
				MPrintFormatItem.COLUMNNAME_AD_PrintFont_ID, false, false,
				true, lookup);
		printFont.fillHorizontal();
		row.appendChild(label.rightAlign());
		row.appendChild(printFont.getComponent());
		printFont.addValueChangeListener(pfe);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		label = new Label(Msg.getElement(Env.getCtx(),
				MPrintFormatItem.COLUMNNAME_AD_PrintColor_ID));
		columnID = MColumn.getColumn_ID(MPrintFormatItem.Table_Name,
				MPrintFormatItem.COLUMNNAME_AD_PrintColor_ID);
		lookup = MLookupFactory.get(Env.getCtx(), 0, 0, columnID,
				DisplayType.TableDir);
		printColor = new WTableDirEditor(
				MPrintFormatItem.COLUMNNAME_AD_PrintColor_ID, false, false,
				true, lookup);
		printColor.fillHorizontal();
		row.appendChild(label.rightAlign());		
		row.appendChild(printColor.getComponent());
		printColor.addValueChangeListener(pfe);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		label = new Label(Msg.getElement(Env.getCtx(),
				MPrintFormatItem.COLUMNNAME_AD_PrintFormatChild_ID));
		columnID = MColumn.getColumn_ID(MPrintFormatItem.Table_Name,
				MPrintFormatItem.COLUMNNAME_AD_PrintFormatChild_ID);
		lookup = MLookupFactory.get(Env.getCtx(), 0, 0, columnID,
				DisplayType.Table);
		includedPrintFormat = new WTableDirEditor(
				MPrintFormatItem.COLUMNNAME_AD_PrintFormatChild_ID, false,
				false, true, lookup);
		row.appendChild(label.rightAlign());
		row.appendChild(includedPrintFormat.getComponent());
		includedPrintFormat.addValueChangeListener(pfe);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		label = new Label(Msg.getElement(Env.getCtx(),
				MPrintFormatItem.COLUMNNAME_LineAlignmentType));
		columnID = MColumn.getColumn_ID(MPrintFormatItem.Table_Name,
				MPrintFormatItem.COLUMNNAME_LineAlignmentType);
		lookup = MLookupFactory.get(Env.getCtx(), 0, 0, columnID,
				DisplayType.List);
		lineAlignment = new WTableDirEditor(
				MPrintFormatItem.COLUMNNAME_LineAlignmentType, false, false,
				true, lookup);
		lineAlignment.fillHorizontal();
		row.appendChild(label.rightAlign());
		row.appendChild(lineAlignment.getComponent());
		lineAlignment.addValueChangeListener(pfe);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		label = new Label(Msg.getElement(Env.getCtx(),
				MPrintFormatItem.COLUMNNAME_FieldAlignmentType));
		columnID = MColumn.getColumn_ID(MPrintFormatItem.Table_Name,
				MPrintFormatItem.COLUMNNAME_FieldAlignmentType);
		lookup = MLookupFactory.get(Env.getCtx(), 0, 0, columnID,
				DisplayType.List);
		fieldAlignment = new WTableDirEditor(
				MPrintFormatItem.COLUMNNAME_FieldAlignmentType, false, false,
				true, lookup);
		fieldAlignment.fillHorizontal();
		row.appendChild(label.rightAlign());
		row.appendChild(fieldAlignment.getComponent());
		fieldAlignment.addValueChangeListener(pfe);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		label = new Label(Msg.getElement(Env.getCtx(),
				MPrintFormatItem.COLUMNNAME_ShapeType));
		columnID = MColumn.getColumn_ID(MPrintFormatItem.Table_Name,
				MPrintFormatItem.COLUMNNAME_ShapeType);
		lookup = MLookupFactory.get(Env.getCtx(), 0, 0, columnID,
				DisplayType.List);
		shapeType = new WTableDirEditor(MPrintFormatItem.COLUMNNAME_ShapeType,
				false, false, true, lookup);
		row.appendChild(label.rightAlign());
		row.appendChild(shapeType.getComponent());
		shapeType.addValueChangeListener(pfe);
		row.setGroup(group);
		rows.appendChild(row);

		row = new Row();
		Separator esep = new Separator("horizontal");
		esep.setSpacing("10px");
		row.appendChild(esep);
		row.setGroup(group);
		rows.appendChild(row);

		return gridView;
	}	
}