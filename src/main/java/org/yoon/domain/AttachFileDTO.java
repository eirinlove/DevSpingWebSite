package org.yoon.domain;

import lombok.Data;

@Data
public class AttachFileDTO {

	private String uuid; //uuid
	private String fileName; //���� ���� �̸�
	private String uploadPath; //���ε� ���
	
	private boolean image; //�̹��� ����
}
