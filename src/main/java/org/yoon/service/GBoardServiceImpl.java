package org.yoon.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yoon.domain.Criteria;
import org.yoon.domain.GBoardAttachVO;
import org.yoon.domain.GBoardVO;
import org.yoon.mapper.GBoardAttachMapper;
import org.yoon.mapper.GBoardMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class GBoardServiceImpl implements GBoardService{

	@Setter(onMethod_= @Autowired)
	private GBoardMapper mapper;
	
	@Setter(onMethod_= @Autowired)
	private GBoardAttachMapper gattachMapper;
	
	@Transactional
	@Override
	public void register(GBoardVO gvo) {
		log.info("=============�Խñ� ���==============");
		 mapper.insertSelectKey(gvo);
		 
		 if(gvo.getAttachList() == null || gvo.getAttachList().size() <= 0) {
			 return;
		 }
		 gvo.getAttachList().forEach(attach -> {
			 attach.setGno(gvo.getGno());
			 gattachMapper.insert(attach);
		 });
	}

	@Transactional
	@Override
	public GBoardVO get(long gno) {
		log.info("=============�Խñ� �󼼺���==============");
		
		//��ȸ�� �ø���===============================================
		mapper.getMoreVisit(gno);
		
		return mapper.read(gno);
	}

	@Transactional
	@Override
	public boolean modify(GBoardVO gvo) {
		log.info("=============�Խñ� ����==============");
		
		gattachMapper.deleteAll(gvo.getGno());
		boolean modifyResult = mapper.update(gvo) ==1;
		
		if(modifyResult && gvo.getAttachList().size()>0) {
			gvo.getAttachList().forEach(attach -> {
				attach.setGno(gvo.getGno());
				gattachMapper.insert(attach);
			});
		}
		return modifyResult;
	}
	
	@Transactional
	@Override
	public boolean delete(long gno) {
		log.info("=============�Խñ� ����==============");
		gattachMapper.deleteAll(gno);
		return mapper.delete(gno)==1;
	}

	@Override
	public List<GBoardVO> getList(Criteria cri) {
		log.info("=============�Խñ� ��� ��ȸ==============");
		return mapper.getListPaging(cri);
	}

	@Override
	public int getTotal(Criteria cri) {
		log.info("=============�Խñ� �� ����==============");
		return mapper.getTotal(cri);
	}
	
	@Override
	public List<GBoardAttachVO> getAttachList(Long gno) {
		log.info("======�۹�ȣ�� ÷������ ����Ʈ ��ȸ=======:"+gno);
		return gattachMapper.findBygno(gno);
	}

	@Transactional
	@Override
	public void recommend(HashMap map) {
		log.info("=============�Խñ� ��õ�ϱ�==============");
		//�Խñ� ��õ �� ����
		mapper.getMoreRecommend(map.get("gno"));
		
		//��õ���̵� �� �۹�ȣ ����
		mapper.recommend(map);
	}

	@Override
	public int checkRecommend(HashMap map) {
		log.info("=============�Խñ� ��õ���� ��ȸ==============");
		return mapper.checkRecommend(map);
	}

	@Transactional
	@Override
	public void cancelRecommend(HashMap map) {
		log.info("=============�Խñ� ��õ���==============");
		//�Խñ� ��õ �� ����
		mapper.reduceRecommend(map.get("gno"));
		
		//��õ���̵� �� �۹�ȣ ����
		mapper.cancelRecommend(map);
	}

	@Override
	public List<GBoardVO> getNewList() {
		log.info("============�������Խ��� �ֽű� ��ȸ==============");
		return mapper.getNewList();
	}

	@Override
	public List<GBoardVO> getBestList() {
		log.info("============�������Խ��� ����Ʈ�� ��ȸ==================");
		return mapper.getBestList();
	}
	
	
	
	

}