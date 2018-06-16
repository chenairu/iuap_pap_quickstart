/*
 * （C）2018 用友网络科技股份有限公司. yonyou Group, All right reserved.
 *
 * 项目名称 : example
 * 创建日期 : 2018年6月14日
 */

package com.yonyou.iuap.example.base.common.dao;

import com.yonyou.iuap.example.base.common.entity.ProcessEntity;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;

/**
  * @description TODO 添加类/接口功能描述
  * @author yaochunlei
  * @date 2018年6月14日 上午10:36:36
  * @version 1.0.0
  *
  *                    修改信息说明：
  * @updateDescription
  * @updateAuthor
  * @updateDate
  */
@MyBatisRepository
public interface CommonMapper {

    public int updateProcessToBusinessTable(ProcessEntity processEntity);
}
