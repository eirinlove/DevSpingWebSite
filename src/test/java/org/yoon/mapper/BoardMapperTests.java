package org.yoon.mapper;


import java.util.List;

import org.junit.Test; 
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;
import org.yoon.service.BoardService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

//DB  CRUD �׽�Ʈ
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardMapperTests {

	@Setter(onMethod_=@Autowired)
	private BoardMapper mapper;
	
	/*
	 * @Test
	 *  public void getList(Criteria cri) {
	 * mapper.getListPaging(cri).forEach(board -> log.info(board)); }
	 */
	
	//�� ��� ��� �׽�Ʈ
	@Test
	 public void getList() {
	 mapper.getList().forEach(board -> log.info(board));
	 }
	
	//�� ��� �׽�Ʈ
	@Test
	public void testInsert() {
		BoardVO board = new BoardVO();
		board.setTitle("�׽�Ʈ2 ����");
		board.setContent("�׽�Ʈ2 ����");
		board.setWriter("�׽�Ʈ2 �ۼ���");
		
		mapper.insert(board);
		
		log.info(board);
	}
	
	//��ȸ�׽�Ʈ
	@Test
	public void read() {
		Long bno=1L;
		
		BoardVO board = mapper.read(bno);
		log.info(board);
	}
	
	//�����׽�Ʈ
	@Test
	public void delete() {
		Long bno=2L;
		
		int check=mapper.delete(bno);
		log.info("==1�� ������ �����Ϸ�==: "+check);		
	}
	
	//�����׽�Ʈ
	@Test
	public void update() {
		BoardVO board = new BoardVO();
		
		board.setBno(1L);
		board.setTitle("�׽�Ʈ1->���ֿ����� �����׽�Ʈ");
		board.setContent("�׽�Ʈ1->���ֿ����� �����׽�Ʈ");
		board.setWriter("���ֿ�");
		
		int check = mapper.update(board);
		log.info("==1�� ������ �����Ϸ�==: "+check);
	}
	
	//����¡ �׽�Ʈ
	@Test
	public void testPaging() {
		
		Criteria cri = new Criteria();
		cri.setAmount(10);
		cri.setPageNum(3);
		List<BoardVO> list= mapper.getListPaging(cri);
		
		list.forEach(board -> log.info(board));
	}
	
	@Test
	public void testSearch() {
		Criteria cri = new Criteria();
		cri.setKeyword("���ֿ�");
		cri.setType("TC");
		
		List<BoardVO>list=mapper.getListPaging(cri);
		list.forEach(board -> log.info(board));
	}
	
}
