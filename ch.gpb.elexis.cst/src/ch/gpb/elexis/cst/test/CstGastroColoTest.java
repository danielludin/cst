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
package ch.gpb.elexis.cst.test;
/*
 * 
 * DB Object for cstgroup_labitem_joint
 */

public class CstGastroColoTest  {
	String text1;
	String text2;
	String text3;
	String text4;
	
	String datumGastro;
	String datumColo;
	boolean gastroHistoBefund;
	
	boolean gastroMakroBefund;
	boolean coloHistoBefund;
	boolean coloMakroBefund;
	
	

	
	public CstGastroColoTest(String text1, String text2, String text3, String text4, String datumGastro, String datumColo, boolean gastroHistoBefund,
			boolean gastroMakroBefund, boolean coloHistoBefund, boolean coloMakroBefund) {
		super();
		this.text1 = text1;
		this.text2 = text2;
		this.text3 = text3;
		this.text4 = text4;
		this.datumGastro = datumGastro;
		this.datumColo = datumColo;
		this.gastroHistoBefund = gastroHistoBefund;
		this.gastroMakroBefund = gastroMakroBefund;
		this.coloHistoBefund = coloHistoBefund;
		this.coloMakroBefund = coloMakroBefund;
	}



	public static CstGastroColoTest getTestGastroColo() {
		CstGastroColoTest c = new CstGastroColoTest("Text 1", "Text 2", "Ein Test", "a test", "20140514", "20140618", false, true, true, false);
		
		return c;
	}
	
	
	public String getText1() {
		return text1;
	}
	public void setText1(String text1) {
		this.text1 = text1;
	}
	public String getText2() {
		return text2;
	}
	public void setText2(String text2) {
		this.text2 = text2;
	}
	public String getText3() {
		return text3;
	}
	public void setText3(String text3) {
		this.text3 = text3;
	}
	public String getText4() {
		return text4;
	}
	public void setText4(String text4) {
		this.text4 = text4;
	}
	public String getDatumGastro() {
		return datumGastro;
	}
	public void setDatumGastro(String datumGastro) {
		this.datumGastro = datumGastro;
	}
	public String getDatumColo() {
		return datumColo;
	}
	public void setDatumColo(String datumColo) {
		this.datumColo = datumColo;
	}
	
	
	public boolean isGastroHistoBefund() {
		return gastroHistoBefund;
	}
	public void setGastroHistoBefund(boolean gastroHistoBefund) {
		this.gastroHistoBefund = gastroHistoBefund;
	}
	public boolean isGastroMakroBefund() {
		return gastroMakroBefund;
	}
	public void setGastroMakroBefund(boolean gastroMakroBefund) {
		this.gastroMakroBefund = gastroMakroBefund;
	}
	public boolean isColoHistoBefund() {
		return coloHistoBefund;
	}
	public void setColoHistoBefund(boolean coloHistoBefund) {
		this.coloHistoBefund = coloHistoBefund;
	}
	public boolean isColoMakroBefund() {
		return coloMakroBefund;
	}
	public void setColoMakroBefund(boolean coloMakroBefund) {
		this.coloMakroBefund = coloMakroBefund;
	}

	 /*
	public void setDatumGastro(String datumGastro) {
		set("datumGastro", datumGastro);
	}
	public String getDatumGastro() {
		return get("datumGastro");
	}
	
	

	public void setDatumColo(String datumColo) {
		set("datumColo", datumColo);
	}
	public String getDatumColo() {
		return get("datumColo");
	}


	public void setText1(String text1) {
		set("text1", text1);
	}
	public String getText1() {
		return get("text1");
	}

	
	public void setText2(String text2) {
		set("text2", text2);
	}
	public String getText2() {
		return get("text2");
	}

	public void setText3(String text3) {
		set("text3", text3);
	}
	public String getText3() {
		return get("text3");
	}
	
	
	public void setGastroMakroBefund(String gastromakrobefund) {
		set("gastroMakroBefund", gastromakrobefund);
	}
	public String getGastroMakroBefund() {
		return get("gastroMakroBefund");
	}
	
	public void setGastroHistoBefund(String gastrohistobefund) {
		set("gastroHistoBefund", gastrohistobefund);
	}
	public String getGastroHistoBefund() {
		return get("gastroHistoBefund");
	}
	


	public void setColoMakroBefund(String colomakrobefund) {
		set("coloMakroBefund", colomakrobefund);
	}
	public String getColoMakroBefund() {
		return get("coloMakroBefund");
	}
	
	public void setColoHistoBefund(String colohistobefund) {
		set("coloHistoBefund", colohistobefund);
	}
	public String getColoHistoBefund() {
		return get("coloHistoBefund");
	}
	*/


}
