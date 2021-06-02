package org.yoon.cron;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.yoon.domain.BoardAttachVO;
import org.yoon.mapper.AttachMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Component
//db���� ��ϵ��ִ� ���������� ���� c��ο� �ִ� ������ ���� �񱳸� ���ؼ� db�� ���� ������ c��ο� ���� ������ �����ϴ� Ʈ����
public class FileCheck {

	@Setter(onMethod_=@Autowired)
	private AttachMapper attachMapper;
	
	//���� ��¥ ���� �޼���
	private String getFolderYesterDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String str= sdf.format(cal.getTime());
		return str.replace("-", File.separator);
	}
	//��,��,��,��,��,��,��   //���� ����2��
	@Scheduled(cron= "0 0 2 * * *")
	public void checkFiles() throws Exception{
		log.warn("���� ��� Ȯ��");
		log.warn("���� ��� Ȯ��");
		
		//���� ��¥ ������ �����ִ� ���� ����Ʈ ��������
		List<BoardAttachVO> fileList = attachMapper.getOldFiles();
		//���� �ּ� ��������
		List<Path>fileListPaths = fileList.stream()
				.map(vo -> Paths.get("c:\\upload1", vo.getUploadPath(),vo.getUuid()+"_"+vo.getFileName()))
				.collect(Collectors.toList());
		//���� Ÿ���� �̹��� Ÿ���̸�?
		fileList.stream().filter(vo ->vo.isFileType()==true)
			.map(vo -> Paths.get("c:\\upload1", vo.getUploadPath(),"s_"+vo.getUuid()+"_"+vo.getFileName()))
			.forEach(p->fileListPaths.add(p));
		
		log.warn("==================================");
		fileListPaths.forEach(p -> log.warn(p));
		
		File targetDir = Paths.get("c:\\upload1",getFolderYesterDay()).toFile();
		
		File[] removeFiles = targetDir.listFiles(file ->fileListPaths.contains(file.toPath())==false);
		
		log.warn("===================================");
		for(File file : removeFiles) {
			log.warn(file.getAbsolutePath());
			file.delete();
		}
		
		
	}
}
