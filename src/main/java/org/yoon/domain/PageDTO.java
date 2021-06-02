package org.yoon.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {

	private int startPage; //���� ������ ��ȣ(���� ǥ��)
	private int endPage; //�� ������ ��ȣ(���� ǥ��)
	private boolean prev; //���� ������
	private boolean next; //���� ������
	
	private int total; //��ü ������ ����
	private Criteria cri; 
	
	public PageDTO(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
	
		//�⺻ �� -> endPage:1 startPage: -8 
		this.endPage = (int) (Math.ceil(cri.getPageNum() / 10.0)) * 10;
		this.startPage = this.endPage - 9;
		
		//�� �� ���� �������� ������ ������ ��ȣ
		//mapper�� ���� �� �� ����->total ����->���� ����ȣ ����
		int realEnd = (int) (Math.ceil(total * 10.0) / cri.getAmount());
		
		if(realEnd < this.endPage) {
			this.endPage = realEnd;
		}
		
		
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEnd;
	}
	
	
}
