package org.yoon.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yoon.domain.BoardAttachVO;
import org.yoon.domain.GBoardVO;
import org.yoon.domain.MemberAttachVO;
import org.yoon.domain.MemberVO;
import org.yoon.mapper.AttachMapper;
import org.yoon.mapper.MemberMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class MemberServiceImpl implements MemberService{

	private MemberMapper mapper;
	private AttachMapper AttachMapper;
	
	private PasswordEncoder pwen;
	@Override
	public int idCheck(String userid) {
		
		return mapper.idChk(userid);
	}

	@Transactional
	@Override
	public void insert(MemberVO vo) {
		log.info("==ȸ�� ���==");
		String pwd=pwen.encode(vo.getUserpw());
		vo.setUserpw(pwd);
		mapper.insert(vo);
		
	}


	@Override
	public MemberVO read(String userid) {
		log.info("===ȸ�� ���� ��������==="+userid);
		return mapper.read(userid);
	}

	@Transactional
	@Override
	public int update(MemberVO vo) {
		// TODO Auto-generated method stub
		log.info("==������ ȸ������==: "+vo);
		//������ �����ϱ� ���ؼ� ������ ������ ������ ���� �������ش�.
		AttachMapper.deleteUser(vo.getUserid());
		//���� ����
		int cnt = mapper.update(vo);
		
		//���� ������ ���泻�뿡 ������ ������ ���ԵǾ� ������ ������ ���뵵 ���� �������ش�.
		if(cnt!=0 && vo.getAttachList()!=null && vo.getAttachList().size()>0) {
			vo.getAttachList().forEach(attach ->{
				attach.setUserid(vo.getUserid());
				AttachMapper.insertUser(attach);
			});
		}
		
		return cnt;
	}


	@Override
	public List<BoardAttachVO> getAttachList(String userid) {
		log.info(userid+"ȸ���� ������ ���� ��������");
		return AttachMapper.findByUser(userid);
	}



}