/*
 * （C）2018 用友网络科技股份有限公司. yonyou Group, All right reserved.
 *
 * 项目名称 : example
 * 创建日期 : 2018年6月1日
 */

package com.yonyou.iuap.example.base.business.notice.service;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.example.base.business.notice.entity.ExNotice;
import com.yonyou.iuap.mvc.type.SearchParams;

/**
  * @description 通知公告业务接口
  * @author 姚春雷
  * @date 2018年6月1日 下午1:26:25
  * @version 1.0.0
  *
  *                    修改信息说明：
  * @updateDescription
  * @updateAuthor
  * @updateDate
  */
public interface ExNoticeService {

    /**
      * 分页查询通知公告，根据分页需求和查询参数
      * @param pageRequest  分页需求
      * @param searchParams 查询参数
      * @return  分页数据
     */
    public Page<ExNotice> selectAllByPage(PageRequest pageRequest, SearchParams searchParams);

    /**
      * 查询通知公告，根据主键
      * @param pkNotice   通知公告主键
      * @return 通知公告
     */
    public ExNotice selectByPrimaryKey(String pkNotice);

    /**
      * 保存通知公告
      * @param exNotice   通知公告
     */
    public void save(ExNotice exNotice);

    /**
      * 更新通知公告
      * @param exNotice   通知公告
     */
    public void update(ExNotice exNotice);

    /**
      * 删除通知公告
      * @param pkNotice   通知公告主键
     */
    public void delete(String pkNotice);

    /**
      * 执行Excel导出
      * @param pkNotices    通知公告主键集合
      * @param response    http响应
      * @throws Exception   异常信息
     */
    public void executeExcelExport(List<String> pkNotices, HttpServletResponse response) throws Exception;

    /**
      * 执行Excel模板下载
      * @param response    http响应
      * @throws Exception   异常信息
     */
    public void executeDownloadExcelTpl(HttpServletResponse response) throws Exception;

    /**
      * 执行Excel导入，本方法更新插入比对是主键，在其他业务中可以根据具体的业务需求进行记录比对
      * @param excelStream  文件输入流
      * @throws Exception   异常信息
     */
    public void executeExcelImport(InputStream excelStream) throws Exception;

}
