/*******************************************************************************
 * Copyright (c) 2015, Daniel Ludin
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Daniel Ludin (ludin@hispeed.ch) - initial implementation
 *******************************************************************************/
package ch.gpb.elexis.cst.view;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import ch.elexis.core.ui.util.Log;
import ch.elexis.data.LabItem;
import ch.elexis.data.LabResult;
import ch.elexis.data.Patient;
import ch.gpb.elexis.cst.data.CstGroup;
import ch.gpb.elexis.cst.data.CstProfile;
import ch.gpb.elexis.cst.data.MinimaxValue;
import ch.gpb.elexis.cst.service.CstService;
import ch.gpb.elexis.cst.widget.MinimaxCanvas;

/**
 * @author daniel ludin ludin@swissonline.ch
 * 27.06.2015
 * 
 */
public class CstResultMiniMax extends CstResultPart {
    /**
     * The ID of the view as specified by the extension.
     */
    public static final String ID = "ch.gpb.elexis.cst.cstresulteffektiv";

    @Override
    public void layoutDisplay(CstProfile aProfile) {
	if (aProfile != null) {

	    log.info("Anzeigetyp:" + aProfile.getAnzeigeTyp(), Log.INFOS);

	    // override eventual a4 quer setting
	    baseComposite.setSize(OUTPUTWIDTH, OUTPUTHEIGTH);

	    // First remove all previous widgets from the display
	    for (Control control : baseComposite.getChildren()) {
		control.dispose();
	    }

	    Patient patient = Patient.load(profile.getKontaktId());

	    HashMap<String, HashMap<String, HashMap<String, List<LabResult>>>> labResults = LabResult
		    .getGrouped(patient);

	    // the bottom most entry is the newest date
	    List<String> sortedDates = CstService.getDistinctDates(labResults);

	    if (sortedDates == null || sortedDates.isEmpty()) {
		return;
	    }

	    int newHeigth = 0;

	    baseComposite.setSize(820, 120);
	    baseComposite.layout();

	    baseComposite.setBackground(WHITE);

	    Label labelPatientName = new Label(baseComposite, SWT.NONE);
	    labelPatientName.setLayoutData(new GridData());

	    labelPatientName.setText(getHeader(patient));

	    labelPatientName.setSize(600, 40);
	    labelPatientName.setFont(fontMedium);

	    Label labelProfileData = new Label(baseComposite, SWT.NONE);
	    labelProfileData.setLayoutData(new GridData());


	    labelProfileData.setText(getSubTitle(patient, aProfile));

	    labelProfileData.setSize(600, 40);
	    labelProfileData.setFont(fontSmall);

	    // Sort the list of CstGroups of this profile according to its ranking
	    @SuppressWarnings("unchecked")
	    Map<String, Integer> itemRanking = (Map<String, Integer>) aProfile.getMap(CstGroup.ITEMRANKING);
	    GroupSorter groupSorter = new GroupSorter(itemRanking);

	    List<CstGroup> cstGroups = aProfile.getCstGroups();
	    Collections.sort(cstGroups, groupSorter);

	    for (CstGroup group : cstGroups) {
		List<LabItem> labitems = group.getLabitems();
		@SuppressWarnings("unchecked")
		Map<String, Integer> itemRanking2 = group.getMap(CstGroup.ITEMRANKING);
		LabItemSorter labItemSorter = new LabItemSorter(itemRanking2);
		Collections.sort(labitems, labItemSorter);

		for (LabItem labItem : labitems) {

		    Composite lineCompo = new Composite(baseComposite, SWT.NONE);
		    lineCompo.setSize(790, 120);
		    lineCompo.setBackground(WHITE);

		    GridData lineData = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false, 1, 1);
		    lineData.grabExcessHorizontalSpace = false;
		    lineData.verticalAlignment = SWT.BEGINNING;
		    lineData.horizontalAlignment = SWT.BEGINNING;
		    lineData.widthHint = 790;

		    lineCompo.setLayoutData(lineData);

		    newHeigth += (lineCompo.getSize().y);

		    String txL2 = "";

		    if (labItem.getEinheit().length() > 0) {
			txL2 += " (" + labItem.getEinheit() + ")";

		    }
		    if (labItem.getKuerzel() != null) {
			txL2 += "  " + labItem.getKuerzel();
		    }

		    MinimaxValue minimaxValue = new MinimaxValue();
		    minimaxValue.setName(
			    group.getName() +
				    ": " +
				    labItem.getName() + txL2);

		    Date dateNow = new Date();

		    Date dateBefore = CstService.getDateFromCompact(aProfile.getPeriod1DateStart());
		    minimaxValue.setDateStartOfSpan1(dateBefore);
		    minimaxValue.setDateEndOfSpan1(CstService.getDateFromCompact(aProfile.getPeriod1DateEnd()));

		    LabResult labResultMax1 = CstService.getMaxValueForTimespan(labItem.getName(),
			    labItem.getKuerzel(), dateBefore, dateNow,
			    labResults);
		    if (labResultMax1 != null) {
			minimaxValue.setMaxOfSpan1(CstService.getNumericFromLabResult(labResultMax1.getResult()));
		    } else {
			minimaxValue.setMaxOfSpan1(-1);
		    }

		    LabResult labResultMin1 = CstService.getMinValueForTimespan(labItem.getName(),
			    labItem.getKuerzel(), dateBefore, dateNow,
			    labResults);
		    if (labResultMin1 != null) {
			minimaxValue.setMinOfSpan1(CstService.getNumericFromLabResult(labResultMin1.getResult()));
		    } else {
			minimaxValue.setMinOfSpan1(-1);
		    }

		    Date dateBefore2 = CstService.getDateFromCompact(aProfile.getPeriod2DateStart());
		    dateBefore = CstService.getDateFromCompact(aProfile.getPeriod2DateEnd());
		    minimaxValue.setDateStartOfSpan2(dateBefore2);
		    minimaxValue.setDateEndOfSpan2(dateBefore);

		    LabResult labResultMax2 = CstService.getMaxValueForTimespan(labItem.getName(),
			    labItem.getKuerzel(), dateBefore2,
			    dateBefore, labResults);
		    if (labResultMax2 != null) {
			minimaxValue.setMaxOfSpan2(CstService.getNumericFromLabResult(labResultMax2.getResult()));
		    } else {
			minimaxValue.setMaxOfSpan2(-1);
		    }

		    LabResult labResultMin2 = CstService.getMinValueForTimespan(labItem.getName(),
			    labItem.getKuerzel(), dateBefore2,
			    dateBefore, labResults);
		    if (labResultMin2 != null) {
			minimaxValue.setMinOfSpan2(CstService.getNumericFromLabResult(labResultMin2.getResult()));
		    } else {
			minimaxValue.setMinOfSpan2(-1);
		    }

		    Date dateBefore3 = CstService.getDateFromCompact(aProfile.getPeriod3DateStart());
		    dateBefore2 = CstService.getDateFromCompact(aProfile.getPeriod3DateEnd());
		    minimaxValue.setDateStartOfSpan3(dateBefore3);
		    minimaxValue.setDateEndOfSpan3(dateBefore2);

		    LabResult labResultMax3 = CstService.getMaxValueForTimespan(labItem.getName(),
			    labItem.getKuerzel(), dateBefore3,
			    dateBefore2, labResults);
		    if (labResultMax3 != null) {
			minimaxValue.setMaxOfSpan3(CstService.getNumericFromLabResult(labResultMax3.getResult()));
		    } else {
			minimaxValue.setMaxOfSpan3(-1);
		    }

		    LabResult labResultMin3 = CstService.getMinValueForTimespan(labItem.getName(),
			    labItem.getKuerzel(), dateBefore3,
			    dateBefore2, labResults);
		    if (labResultMin3 != null) {
			minimaxValue.setMinOfSpan3(CstService.getNumericFromLabResult(labResultMin3.getResult()));
		    } else {
			minimaxValue.setMinOfSpan3(-1);
		    }

		    MinimaxCanvas minimaxCanvas = new MinimaxCanvas(lineCompo, SWT.BORDER);
		    minimaxCanvas.setFinding(minimaxValue);

		    checkPageBreak(baseComposite);


		} // end loop lab items
	    } // end loop cst groups



	    baseComposite.pack();
	    int currentHeigth = baseComposite.getSize().y;

	    int printHeigth = profile.getAusgabeRichtung() ? 794 : 1123;

	    int pageCnt = currentHeigth / printHeigth;
	    int rmn = ((pageCnt + 1) * printHeigth) - currentHeigth;

	    if (rmn < printHeigth) {
		addLine(baseComposite, rmn);

	    }
	    addBefunde(baseComposite);

	}

    }



    @Override
    public void visible(boolean mode) {
	// TODO Auto-generated method stub
	//super.visible(mode);
    }

}
