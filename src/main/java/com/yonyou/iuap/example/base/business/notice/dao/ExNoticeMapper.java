/*
 * （C）2018 用友网络科技股份有限公司. yonyou Group, All right reserved.
 *
 * 项目名称 : example
 * 创建日期 : 2018年6月1日
 */

package com.yonyou.iuap.example.base.business.notice.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.example.base.business.notice.entity.ExNotice;
import com.yonyou.iuap.mybatis.type.PageResult;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;

/**
  * @description 通知公告DAO接口
  * @author 姚春雷
  * @date 2018年6月1日 下午1:26:08
  * @version 1.0.0
  *
  *                    修改信息说明：
  * @updateDescription
  * @updateAuthor
  * @updateDate
  */
@MyBatisRepository
public interface ExNoticeMapper {

    /**
      * 插入通知公告
      * @param exNotice   通知公告
      * @return 插入条数
     */
    public int insert(ExNotice exNotice);

    /**
      * 更新通知公告，全字段更新
      * @param exNotice  通知公告
      * @return 更新条数
     */
    public int update(ExNotice exNotice);

    /**
      * 删除通知公告，根据主键
      * @param pkNotice  通知公告主键
      * @return 删除条数
     */
    public int delete(String pkNotice);

    /**
      * 查询通知公告，根据主键
      * @param pkNotice   通知公告主键
      * @return 通知公告
     */
    public ExNotice selectByPrimaryKey(String pkNotice);

    /**
      * 查询通知公告，根据主键集合
      * @param pkNotices    通知公告主键集合
      * @return 结果集合
     */
    public PageResult<ExNotice> selectByPrimaryKeys(List<String> pkNotices);

    /**
      * 查询存在的记录主键，根据主键集合
      * @param pkNotices    通知公告主键集合
      * @return 结果主键集合
     */
    public List<String> selectByPrimaryKeysExists(List<String> pkNotices);

    /**
      * 分页查询通知公告，根据分页需求和查询参数
      * @param pageRequest  分页需求
      * @param searchParams 查询参数
      * @return 分页结果集合
     */
    public PageResult<ExNotice> selectAllByPage(@Param("page") PageRequest pageRequest, @Param("search") Map<String, Object> searchParams);

}
