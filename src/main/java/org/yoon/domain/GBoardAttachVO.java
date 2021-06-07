package org.yoon.domain;

import lombok.Data;

@Data
public class GBoardAttachVO {
	
	private String fileName;
	private String uploadPath;
	private String uuid;
	private boolean fileType;
	private Long bno;

}
