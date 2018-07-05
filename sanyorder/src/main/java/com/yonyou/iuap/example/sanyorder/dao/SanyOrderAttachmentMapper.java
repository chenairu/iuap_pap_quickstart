package com.yonyou.iuap.example.sanyorder.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yonyou.iuap.baseservice.persistence.mybatis.mapper.GenericExMapper;
import com.yonyou.iuap.example.sanyorder.entity.AttachmentEntity;
import com.yonyou.iuap.mybatis.anotation.MyBatisRepository;

@MyBatisRepository
public interface SanyOrderAttachmentMapper extends GenericExMapper<AttachmentEntity> {

	List<AttachmentEntity> getRefId(@Param("id")String id);

	
}
