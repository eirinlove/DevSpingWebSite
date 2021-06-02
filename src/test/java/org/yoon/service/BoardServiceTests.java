package org.yoon.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yoon.domain.BoardVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;


//���������� crud �׽�Ʈ
@RunWith(SpringJUnit4ClassRunner.class)
@Log4j
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class BoardServiceTests {
	
	@Setter(onMethod_=@Autowired)
	private BoardService service;

	//�� ��� �׽�Ʈ
	@Test
	public void testRegister() {
		
		log.info("====��� �׽�Ʈ ����=====");
		BoardVO board = new BoardVO();
		board.setTitle("�� ��� �׽�Ʈ2");
		board.setContent("�� ��� �׽�Ʈ2");
		board.setWriter("�� ��� �׽�Ʈ2");
		
		service.register(board);
		
		log.info("�׽�Ʈ���� ��ϵ� �� ��ȣ��???>>>  " +board.getBno());
	}

	//�� ��ȸ �׽�Ʈ
	@Test
	public void testGet() {
			
		log.info("====��ȸ �׽�Ʈ ����=====");
		
		Long bno = 3L;
		
		log.info("��ȸ �׽�Ʈ ����� >>> "+ service.get(bno));
		}
	
	//�� ���� �׽�Ʈ
	@Test
	public void testRemove() {
		
		log.info("====���� �׽�Ʈ ����=====");
		
		Long bno = 3L;
		int check=service.remove(bno);
		
		log.info("1�̸� ���� �׽�Ʈ ����!: "+check);
	}
	
	//�� ���� �׽�Ʈ
	@Test
	public void testModify() {
		
		BoardVO board = service.get(1L);
		
		if(board==null) {
			return;
		}
		board.setTitle("�� ���� �׽�Ʈ1");
		board.setContent("�� ���� �׽�Ʈ1");
		board.setWriter("�� ���� �׽�Ʈ1");
		board.setBno(1L);
		
		service.modify(board);
	}
	
	
	
	
}
