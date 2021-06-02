package org.yoon.mapper;


import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test; 
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;
import org.yoon.domain.ReplyVO;
import org.yoon.service.BoardService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

//DB  CRUD �׽�Ʈ
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyMapperTests {

	private Long[] boardArr= {11L, 12L, 13L, 14L, 15L};
	
	@Setter(onMethod_=@Autowired)
	private ReplyMapper mapper;
	
	@Test
	public void testMapper() {
		log.info(mapper);
	}
	
	//��� �߰� �׽�Ʈ
	@Test
	public void testCreate() {
		
		IntStream.rangeClosed(1, 10).forEach(i ->{
			ReplyVO vo = new ReplyVO();
			
			//�Խù��� ��ȣ
			vo.setBno(boardArr[i%5]);
			vo.setReply("��� �׽�Ʈ"+i);
			vo.setReplayer("replayer" +i);
			
			mapper.insert(vo);
		});
	}
	
	//��� ��ȸ
	@Test
	public void testRead() {
		Long Bno = 5L;
		
		ReplyVO vo = mapper.read(Bno);
		log.info(vo);
	}
	
	@Test
	public void testUpdate() {
		Long Bno = 10L;
		
		ReplyVO vo = mapper.read(Bno);
		vo.setReply("��� ���� �׽�Ʈ");
		
		int cnt = mapper.update(vo);
		log.info("������Ʈ �Ǿ�����? 1:�Ǿ���,  0:�ƴ�?" + cnt);
	}
	
	//��� ����¡ �׽�Ʈ
	@Test
	public void testList() {
		Criteria cri = new Criteria();
		
		List<ReplyVO> replies = mapper.getListPaging(cri, boardArr[0]);
		replies.forEach(reply -> log.info(reply));
	}
	
	//��� ���� �׽�Ʈ
	@Test
	public void testDelte() {
		Long rno = 1L;
		
		int cnt=mapper.delete(rno);
		log.info("1�� ������ ���� ����:  " +cnt);
	}
	
	@Test
	public void testPaging() {
		Criteria cri = new Criteria(1,10);
		
		List<ReplyVO> replies = mapper.getListPaging(cri, 5L);
		replies.forEach(reply -> log.info(reply));
 	}
}
