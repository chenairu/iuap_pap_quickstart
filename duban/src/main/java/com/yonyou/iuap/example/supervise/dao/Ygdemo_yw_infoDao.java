package com.yonyou.iuap.example.supervise.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yonyou.iuap.base.dao.BaseMetadataDao;
import com.yonyou.iuap.example.supervise.entity.Ygdemo_yw_info;
import com.yonyou.iuap.example.supervise.entity.Ygdemo_yw_sub;
import com.yonyou.iuap.persistence.bs.dao.DAOException;
import com.yonyou.iuap.persistence.vo.BaseEntity;


@Repository
public class Ygdemo_yw_infoDao extends BaseMetadataDao<Ygdemo_yw_info> {

    @SuppressWarnings("unchecked")
	@Override
	public Ygdemo_yw_info save(Ygdemo_yw_info parent,
			List<? extends BaseEntity>... children) throws DAOException {
		List<Ygdemo_yw_sub> lsChild = parent.getId_ygdemo_yw_sub();
    	return super.save(parent, lsChild);
	}

}
