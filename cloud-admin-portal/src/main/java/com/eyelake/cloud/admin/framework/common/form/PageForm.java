/**
 * 
 */
package com.eyelake.cloud.admin.framework.common.form;

/**
 * 分页from父类
 * 
 * @author CC
 *
 */
public class PageForm {

	private int pageSize;
	
	private int pageNum;

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the pageNum
	 */
	public int getPageNum() {
		return pageNum;
	}

	/**
	 * @param pageNum the pageNum to set
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
	
}
