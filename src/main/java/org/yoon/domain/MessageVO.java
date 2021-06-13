package org.yoon.domain;


import java.util.Date;

import lombok.Data;

@Data
public class MessageVO {
	private long mno;
    private String receiver;
    private Date msgDate;
    private String content;
    private String sender;
    private String readYN;
}
