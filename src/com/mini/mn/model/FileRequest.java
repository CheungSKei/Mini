package com.mini.mn.model;

import java.util.ArrayList;
import java.util.List;
/**
 * �ͻ��˷����ļ����ݽṹ
 *
 * @version 1.0.0
 * @date 2014-2-20
 * @author S.Kei.Cheung
 */
public class FileRequest extends AbstractRequest {

	private static final long serialVersionUID = -4264439118166879834L;

	// �����ļ�
	private List<FileContent> files;
	
	public FileRequest() {
		files = new ArrayList<FileContent>();
	}

    public List<FileContent> getFiles() {
        return files;  
    }

    public void setFiles(List<FileContent> files) {
        this.files = files;
    }
}
