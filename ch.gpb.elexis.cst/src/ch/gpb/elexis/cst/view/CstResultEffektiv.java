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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import ch.elexis.core.ui.util.Log;
import ch.elexis.data.LabItem;
import ch.elexis.data.LabResult;
import ch.gpb.elexis.cst.data.CstAbstract;
import ch.gpb.elexis.cst.data.CstGroup;
import ch.gpb.elexis.cst.data.CstProfile;
import ch.gpb.elexis.cst.data.ValueFinding;
import ch.gpb.elexis.cst.preferences.Messages;
import ch.gpb.elexis.cst.service.CstService;
import ch.gpb.elexis.cst.widget.CstDangerRangeCanvas;
import ch.gpb.elexis.cst.widget.CstVorwertCanvas;

/**
 * 
 * @author daniel ludin ludin@swissonline.ch
 * 27.06.2015
 * 
 * Class for displaying the Effektiv display mode.
 * 
 * Target width for display: 794px (Hälfte: 397 px) 
 * Target heigth for display: 1123px
 */

public class CstResultEffektiv extends CstResultPart {
    /**
     * The ID of the view as specified by the extension.
     */
    public static final String ID = "ch.gpb.elexis.cst.cstresultminimax";
    private int printHeigth = OUTPUTHEIGTH;

    @Override
    public void layoutDisplay(CstProfile aProfile) {
	if (aProfile != null) {

	    // false = a4hoch
	    if (aProfile.getAusgabeRichtung()) {
		baseComposite.setSize(OUTPUTHEIGTH, OUTPUTHEIGTH);
		printHeigth = OUTPUTWIDTH;
	    } else {
		baseComposite.setSize(OUTPUTWIDTH, OUTPUTHEIGTH);

	    }

	    log.info("Anzeigetyp:" + aProfile.getAnzeigeTyp());

	    // First remove all previous widgets from the display
	    for (Control control : baseComposite.getChildren()) {
		control.dispose();
	    }

	    HashMap<String, HashMap<String, HashMap<String, List<LabResult>>>> labResults = LabResult
		    .getGrouped(patient);

	    // the bottom most entry is the newest date
	    List<String> sortedDates = CstService.getDistinctDates(labResults);

	    if (sortedDates == null || sortedDates.isEmpty()) {
		return;
	    }

	    int newHeigth = 0;

	    baseComposite.setSize(OUTPUTWIDTH, 800);
	    baseComposite.setBounds(new Rectangle(0, 0, OUTPUTWIDTH, OUTPUTHEIGTH));
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
	    Map<String, Integer> itemRanking = aProfile.getMap(CstGroup.ITEMRANKING);
	    GroupSorter groupSorter = new GroupSorter(itemRanking);

	    List<CstGroup> cstGroups = aProfile.getCstGroups();
	    Collections.sort(cstGroups, groupSorter);

	    int count = 0;

	    for (CstGroup group : cstGroups) {

		Label l1 = new Label(baseComposite, SWT.NONE);
		//GridData gd = new GridData(300, 22);
		GridData gd = new GridData(SWT.DEFAULT, 22);
		l1.setLayoutData(gd);
		l1.setText(" " + group.getName() + " ");
		l1.setFont(fontBig);
		l1.setBackground(GRAY);
		l1.setForeground(WHITE);
		List<LabItem> labitems = group.getLabitems();

		@SuppressWarnings("unchecked")
		Map<String, Integer> itemRanking2 = group.getMap(CstGroup.ITEMRANKING);

		LabItemSorter labItemSorter = new LabItemSorter(itemRanking2);

		Collections.sort(labitems, labItemSorter);

		for (LabItem labItem : labitems) {

		    Label l2 = new Label(baseComposite, SWT.NONE);
		    l2.setLayoutData(new GridData(794, 20));

		    String txL2 = "         " + String.valueOf(++count) + ": " + labItem.getName() + " ";

		    if (labItem.getEinheit().length() > 0) {
			txL2 += " (" + labItem.getEinheit() + ")";

		    }
		    if (labItem.getKuerzel() != null) {
			txL2 += "  " + labItem.getKuerzel();
		    }
		    l2.setText(txL2);
		    l2.setFont(fontMedium);
		    l2.setBackground(LIGHTGRAY);

		    Composite lineCompo = new Composite(baseComposite, SWT.FILL);

		    GridLayout lineLayout = new GridLayout();
		    lineLayout.numColumns = 2;
		    lineCompo.setLayout(lineLayout);
		    GridData lineData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		    lineData.grabExcessHorizontalSpace = true;
		    lineData.grabExcessVerticalSpace = true;
		    lineCompo.setSize(OUTPUTWIDTH, 100);
		    lineCompo.setBackground(WHITE);
		    lineCompo.setLayoutData(lineData);

		    Composite leftCompo = new Composite(lineCompo, SWT.NONE);
		    GridLayout leftLayout = new GridLayout();
		    leftCompo.setLayout(leftLayout);
		    GridData leftData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		    leftData.grabExcessHorizontalSpace = false;
		    leftData.grabExcessVerticalSpace = true;
		    leftCompo.setSize(400, 100);
		    leftCompo.setLayoutData(leftData);
		    leftCompo.setBackground(WHITE);

		    Composite rightCompo = new Composite(lineCompo, SWT.NONE);
		    GridLayout rightLayout = new GridLayout();
		    rightLayout.numColumns = 1;
		    rightCompo.setLayout(rightLayout);
		    rightCompo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		    rightCompo.setSize(400, 100);
		    rightCompo.setBackground(WHITE);

		    newHeigth += (lineCompo.getSize().y);

		    // neuestes Datum aus der Liste der in den LabResults vorhandenen Daten holen
		    String sDateOfLatestLabresult = sortedDates.get(sortedDates.size() - 1);
		    log.info(
			    "Searching result for date:  " + sDateOfLatestLabresult + "\tLabitem: "
				    + labItem.getName()
				    + "\tPat.ID:"
				    + aProfile.getKontaktId(),
			    Log.INFOS);

		    // Algorithm of Lab Result selection:
		    // If the Crawlback is set to 0, we take the newest date from the dates with labresults,
		    // an use this date for the DangerRangeCanvas even if there is no result on this date and labitem.
		    // The dates before this latest date are used for VorwertCanvas as far as crawlback goes back.
		    // 
		    // If the Crawlback is greater than 0, we take the first date that has a Labresult for the
		    // DangerRangeCanvas, and the remaining Labresults for the VorwertCanvas as far as crawlback goes back.s

		    LabResult labResultLatest = CstService.getValueForNameAndDate(labItem.getName(),
			    sDateOfLatestLabresult,
			    labItem.getKuerzel(),
			    labResults);

		    ArrayList<String> datesForVorwert = new ArrayList<String>(sortedDates);

		    // if the crawlback is not set to 0, we search for the next date before 
		    // containing a result
		    // else we leave the result for DangerRangeCanvas on the latest lab date, which is zero,
		    // and provide all dates except the newest for the Findings loop of VorwertCanvas

		    if (aProfile.getCrawlBack() > 0) {

			String sNewestDate = sortedDates.get(sortedDates.size() - 1);

			if (labResultLatest == null) {

			    for (int i = sortedDates.size() - 1; i >= 0; i--) {

				// starts with bottom most date (= newest)
				String sDateAtIndex = sortedDates.get(i);

				datesForVorwert.remove(i);

				long daysBetween = CstService.getDayCountFromCompact(sDateAtIndex,
					sNewestDate);
				long crawlBack = aProfile.getCrawlBack();

				if (daysBetween > crawlBack) {
				    // the date where the crawlback interrupts marks the date (and all newer dates)
				    // that must be removed from the date list for the findings loop that gets values
				    // for the vorwertcanvas.

				    break;
				}

				LabResult labResultIndex = CstService.getValueForNameAndDate(labItem.getName(),
					sDateAtIndex, labItem.getKuerzel(), labResults);

				if (labResultIndex != null) {
				    labResultLatest = labResultIndex;
				    sDateOfLatestLabresult = sDateAtIndex;
				    break;
				}
			    }
			}
			else {
			    // there is a value already on the newest date, so we remove this from the date list
			    datesForVorwert.remove(datesForVorwert.size() - 1);

			}
		    } else {
			datesForVorwert.remove(datesForVorwert.size() - 1);
		    }

		    // process the Ranges of Labresult for Display, again there is crappy data, so lots of checks are necessary
		    String sRangeStart = "0";

		    if (patient.getGeschlecht().toLowerCase().equals("m")) {

			if (labItem.getRefM() != null) {
			    sRangeStart = labItem.getRefM();
			} else {
			    if (labItem.getRefW() != null) {
				sRangeStart = labItem.getRefW();
			    }
			}

		    } else {
			if (labItem.getRefW() != null) {
			    sRangeStart = labItem.getRefW();
			} else {
			    if (labItem.getRefM() != null) {
				sRangeStart = labItem.getRefM();
			    }
			}
		    }

		    sRangeStart = sRangeStart.trim();
		    double dRangeStart = 0;
		    double dRangeEnd = 0;

		    try {
			if (sRangeStart.startsWith("-")) {
			    sRangeStart = sRangeStart.replace("-", "");
			    dRangeEnd = Double.parseDouble(sRangeStart);
			    dRangeStart = 0;
			} else if (sRangeStart.startsWith("<")) {
			    sRangeStart = sRangeStart.replace("<", "");
			    dRangeEnd = Double.parseDouble(sRangeStart);
			    dRangeStart = 0;

			} else if (sRangeStart.startsWith(">")) {
			    sRangeStart = sRangeStart.replace(">", "");
			    dRangeStart = Double.parseDouble(sRangeStart);
			    dRangeEnd = 0;

			} // if there is only a single number, it's probably the End of range value.
			else if (sRangeStart.matches("\\d*")) {
			    dRangeEnd = Double.parseDouble(sRangeStart);
			    dRangeStart = 0;

			} else {
			    String[] values = sRangeStart.split("-");
			    dRangeStart = Double.parseDouble(values[0]);
			    dRangeEnd = Double.parseDouble(values[1]);
			}
		    } catch (NumberFormatException e) {
			log.error("NumberFormatException for start range of  Pat ID:" + aProfile.getKontaktId()
				+ ":" + labItem.getName() + ":" + "/" + sRangeStart + e.getMessage(), Log.ERRORS);
		    } catch (ArrayIndexOutOfBoundsException e) {
			log.error("ArrayIndexOutOfBoundsException for start range of " + labItem.getName() + ":"
				+ "/" + sRangeStart + e.getMessage(), Log.ERRORS);
		    }
		    /*
		     * System.err.println("Formatting Refs: " + sRangeStart
		     * + " => " + dRangeStart + "/" + dRangeEnd);
		     */
		    log.debug("Formatting Reference Values of Labitem: " + labItem.getName() + ":\t" + sRangeStart
			    + " => " + dRangeStart + "/" + dRangeEnd);

		    String sResult = "";

		    try {
			if (labResultLatest != null && labResultLatest.getResult() != null) {
			    sResult = labResultLatest.getResult();
			    System.out.println("raw result: " + sResult);
			}
		    } catch (Exception e1) {
			log.info("Error opening result view: " + e1.getMessage() + " " + labItem.getName(),
				Log.INFOS);
		    }

		    double dResult = -1;

		    dResult = CstService.getNumericFromLabResult(sResult);

		    // Fetch the LabResults for the Vorwert Graphic
		    List<ValueFinding> findings = new ArrayList<ValueFinding>();

		    Collections.reverse(datesForVorwert);

		    for (String fDate : datesForVorwert) {

			Date dateResult = CstService.getDateFromCompact(fDate);
			Date startDateProfile = CstService.getDateFromCompact(profile.getValidFrom());
			if (dateResult.compareTo(startDateProfile) < 0) {
			    continue;
			}

			LabResult resultVorwert = CstService.getValueForNameAndDate(labItem.getName(), fDate,
				labItem.getKuerzel(),
				labResults);

			// might be null, not every date has a value
			if (resultVorwert == null) {
			    //log.info("No LabResult for: " + labItem.getName() + "/" + fDate, Log.INFOS);
			    continue;

			}

			String sResultV = null;

			try {
			    sResultV = resultVorwert.getResult();
			} catch (Exception e) {
			    log.error("Error getting result effektiv: " + e.getMessage(), Log.ERRORS);
			    continue;
			}

			double dResultV = 0;
			dResultV = CstService.getNumericFromLabResult(sResultV);

			ValueFinding f = new ValueFinding();
			if (patient.getGeschlecht().toLowerCase().equals("m")) {
			    f.setRefMstart(dRangeStart);
			    f.setRefMend(dRangeEnd);
			    f.setRefFstart(0);
			    f.setRefFend(0);
			} else {
			    f.setRefFstart(dRangeStart);
			    f.setRefFend(dRangeEnd);
			    f.setRefMstart(0);
			    f.setRefMend(0);

			}

			f.setValue(dResultV);
			f.setDateOfFinding(CstService.getDateFromCompact(fDate));
			f.setParam(sResultV);
			findings.add(f);

		    }

		    CstVorwertCanvas vCanvas = new CstVorwertCanvas(
			    leftCompo, profile.getAusgabeRichtung(),
			    SWT.NONE);
		    vCanvas.setFindings(findings);
		    GridLayout vorwertLayout = new GridLayout();
		    vCanvas.setLayout(vorwertLayout);
		    GridData vorwertData = new GridData();
		    vorwertData.horizontalAlignment = GridData.FILL;
		    vorwertData.grabExcessHorizontalSpace = true;
		    vCanvas.setLayoutData(vorwertData);

		    if (dResult == -1) {
			//Label label = new Label(rightCompo, SWT.NONE);
			Label label = new Label(leftCompo, SWT.NONE);
			label.setText("No result for Lab Item " + labItem.getName() + " on "
				+ CstService.getGermanFromCompact(sDateOfLatestLabresult));
			GridData gdLabelNoValue = new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1);
			label.setLayoutData(gdLabelNoValue);
		    } else {

			CstDangerRangeCanvas drc2 = new CstDangerRangeCanvas(
				leftCompo,
				profile.getAusgabeRichtung(), SWT.NONE,
				dRangeStart,
				dRangeEnd, dResult, sResult, labItem.getName(),
				CstService.getGermanFromCompact(sDateOfLatestLabresult));
			GridLayout drcLayout = new GridLayout();
			GridData drcData = new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1);
			drcData.verticalIndent = 30;
			drcData.horizontalAlignment = SWT.BEGINNING;
			drc2.setLayout(drcLayout);
			drc2.setLayoutData(drcData);

		    }

		    Text txtAbstract = new Text(rightCompo, /*SWT.MULTI |SWT.BORDER | SWT.V_SCROLL |*/
		    SWT.READ_ONLY | SWT.WRAP);
		    txtAbstract.setFont(fontSmall);
		    txtAbstract.setSize(210, 190);
		    GridData gdTxtAbstract = new GridData(GridData.FILL_VERTICAL);
		    gdTxtAbstract.verticalAlignment = SWT.TOP;
		    gdTxtAbstract.horizontalAlignment = SWT.BEGINNING;
		    gdTxtAbstract.widthHint = 210;
		    gdTxtAbstract.heightHint = 190;
		    gdTxtAbstract.grabExcessVerticalSpace = true;
		    txtAbstract.setLayoutData(gdTxtAbstract);
		    txtAbstract.setBackground(LIGHTGRAY);
		    CstAbstract cabstract = CstAbstract.getByLaboritemId(labItem.getId());
		    if (cabstract != null) {
			txtAbstract.setText(cabstract.getDescription1());
		    } else {
			txtAbstract.setText(Messages.Cst_Text_no_abstract_available);
		    }
		    newHeigth += (lineCompo.getSize().y + 40);

		    checkPageBreak(baseComposite);

		} // end loop lab items
	    } // end loop cst groups

	    // fill up the page before adding findings
	    baseComposite.pack();
	    int currentHeigth = baseComposite.getSize().y;

	    int pageCnt = currentHeigth / printHeigth;
	    int rmn = ((pageCnt + 1) * printHeigth) - currentHeigth;

	    if (rmn < printHeigth) {
		addLine(baseComposite, rmn);

	    }
	    addBefunde(baseComposite);

	    baseComposite.pack();

	}

    }

    @Override
    public void visible(boolean mode) {
	//super.visible(mode);
    }

}
