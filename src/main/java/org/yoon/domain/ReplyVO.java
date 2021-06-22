package org.yoon.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyVO {
		private Long rno; // 댓글 번호
		private Long bno; // 글 번호
		
		private String reply; //댓글 내용
		private String replayer; //댓글 작성자
		private Date replyDate; //작성일
		private Date updateDate; //수정일
		
		private char hide; // 비밀글 체크
}
