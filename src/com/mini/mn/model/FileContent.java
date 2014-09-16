package com.mini.mn.model;

import java.io.File;

import com.mini.mn.model.Entity.Builder;


/**
 * �ļ�����ʵ��
 * {@code fileName} �ļ���
 * {@code fileNameLength} �ļ�������
 * {@code fileLength} �ļ�����
 * {@code content} �ļ�
 * 
 * @version 1.0.0
 * @date 2014-02-20
 * @author S.Kei.Cheung
 */
public class FileContent extends Entity implements Builder {

	private static final long serialVersionUID = 4734133655754164403L;

	/**
	 * �ļ���
	 */
	private String fileName;
	
	/**
	 * �ļ�������
	 */
	private int fileNameLength;
	
	/**
	 * �ļ�����
	 */
	private int fileLength;
	
	/**
	 * �ļ�
	 */
	private File fileContent;

	public String getFileName() {
        return fileName;  
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public int getFileNameLength() {
        return fileNameLength;  
    }

    public void setFileNameLength(int fileNameLength) {
        this.fileNameLength = fileNameLength;
    }
	
    public int getFileLength() {  
        return fileLength;  
    }  

    public void setFileLength(int fileLength) {  
        this.fileLength = fileLength;  
    }  

    public File getFileContent() {  
        return fileContent;  
    }  

    public void setFileContent(File fileContent) {  
        this.fileContent = fileContent;
    }

}
