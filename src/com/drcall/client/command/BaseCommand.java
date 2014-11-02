package com.drcall.client.command;



public class BaseCommand {
	private static int ROWS_IN_PAGE = 10;
	protected int page = 1;
	protected int pages = 1;
	protected int rows;
	protected int initRow;
	protected int rowsInPage;
	protected int beginPage;
	protected int endPage;
	protected boolean preciseMatch = true;
	protected String mode = "edit";
	protected int itemNo;
	
	
	public int getPage() {
		if(page == 0 || page > getPages()){
			return 1;
		} 
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPages() {
		if(rows == 0){
			return 1;
		} else {
			return rows/ROWS_IN_PAGE+1;
		}
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getInitRow() {
		return (getPage()-1)*ROWS_IN_PAGE;
	}
	public int getRowsInPage() {
		return ROWS_IN_PAGE;
	}
	
	public int getBeginPage(){
		if(getPage() < 5){
			return 1;
		} else {
			return getPage()-4;
		}
	}
	
	public int getEndPage(){
		if(getPage() < 5){
			if(getPages() < 10){
				return getPages();
			} else {
				return 9;
			}
		} else {
			if(getPage()+5 < getPages()){
				return getPage()+4;
			} else {
				return getPages();
			}
		}
	}
	public boolean isPreciseMatch() {
		return preciseMatch;
	}
	public void setPreciseMatch(boolean preciseMatch) {
		this.preciseMatch = preciseMatch;
	}
	
	
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public int getItemNo() {
		if(getPage() == 1){
			return 1;
		}
		return (getPage()-1)*ROWS_IN_PAGE+1;
	}
	
	
	
	
	
	

}
