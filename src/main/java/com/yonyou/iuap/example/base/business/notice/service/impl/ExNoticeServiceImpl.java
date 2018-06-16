/*
 * （C）2018 用友网络科技股份有限公司. yonyou Group, All right reserved.
 *
 * 项目名称 : example
 * 创建日期 : 2018年6月1日
 */

package com.yonyou.iuap.example.base.business.notice.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.common.utils.ExcelExportImportor;
import com.yonyou.iuap.example.base.business.notice.dao.ExNoticeMapper;
import com.yonyou.iuap.example.base.business.notice.entity.ExNotice;
import com.yonyou.iuap.example.base.business.notice.service.ExNoticeService;
import com.yonyou.iuap.example.base.utils.reflect.EntityUtils;
import com.yonyou.iuap.example.base.utils.system.RulesCodeUtils;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.mybatis.type.PageResult;
import com.yonyou.uap.ieop.busilog.config.annotation.BusiLogConfig;

/**
  * @description 通知公告业务实现
  * @author 姚春雷
  * @date 2018年6月1日 下午1:27:16
  * @version 1.0.0
  *
  *                    修改信息说明：
  * @updateDescription
  * @updateAuthor
  * @updateDate
  */
@Service("exNoticeService")
public class ExNoticeServiceImpl implements ExNoticeService {

    @Autowired
    private ExNoticeMapper exNoticeMapper;

    /**
      * 分页查询通知公告，根据分页需求和查询参数
      * @param pageRequest  分页需求
      * @param searchParams 查询参数
      * @return  角色分页数据
     */
    @Override
    public Page<ExNotice> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
        return exNoticeMapper.selectAllByPage(pageRequest, searchParams.getSearchMap()).getPage();
    }

    /**
      * 查询通知公告，根据主键
      * @param pkNotice   通知公告主键
      * @return 通知公告
     */
    @Override
    public ExNotice selectByPrimaryKey(String pkNotice) {
        return exNoticeMapper.selectByPrimaryKey(pkNotice);
    }

    /**
      * 保存通知公告
      * @param exNotice   通知公告
     */
    @BusiLogConfig(method = "ex_info_save", busiName = "通知公告")
    @Transactional
    @Override
    public void save(ExNotice exNotice) {
        //如果主键为空，则设置主键，否则不予设置。防止有些业务是前置主键的
        if (StringUtils.isBlank(exNotice.getPkNotice())) {
            //设置实体主键
            exNotice.setPkNotice(UUID.randomUUID().toString());
        }
        //设置通知编号
        exNotice.setNoticeCode(RulesCodeUtils.getBusinessCode("TZGG", "", exNotice));
        //给公共属性赋值
        EntityUtils.commonAttrAssiNewObject(exNotice);
        exNoticeMapper.insert(exNotice);
    }

    /**
      * 更新通知公告
      * @param exNotice   通知公告
     */
    @BusiLogConfig(method = "ex_info_update", busiName = "通知公告")
    @Transactional
    @Override
    public void update(ExNotice exNotice) {
        //给公共属性赋值
        EntityUtils.commonAttrAssiUpdateObject(exNotice);
        exNoticeMapper.update(exNotice);

    }

    /**
      * 删除通知公告
      * @param pkNotice   通知公告主键
     */
    @BusiLogConfig(method = "ex_info_del", busiName = "通知公告")
    @Transactional
    @Override
    public void delete(String pkNotice) {
        exNoticeMapper.delete(pkNotice);
    }

    /**
      * 执行Excel导出
      * @param pkNotices    通知公告主键集合
      * @param response    http响应
      * @throws Exception   异常信息
     */
    @Override
    public void executeExcelExport(List<String> pkNotices, HttpServletResponse response) throws Exception {

        //根据主键集合获得需要导出的数据
        PageResult<ExNotice> pageResult = exNoticeMapper.selectByPrimaryKeys(pkNotices);

        //调用excel导出服务，传递参数依次是：http响应,list数据集合,实体显示的中文map集合,sheet页名称,文件名
        ExcelExportImportor.writeExcel(response, pageResult.getContent(), getExcelHeadInfo(), "通知公告", "通知公告文件");
    }

    /**
      * 执行Excel模板下载
      * @param response    http响应
      * @throws Exception   异常信息
     */
    @Override
    public void executeDownloadExcelTpl(HttpServletResponse response) throws Exception {
        ExcelExportImportor.downloadExcelTemplate(response, getExcelHeadInfo(), "通知公告", "通知公告数据导入模板");
    }

    /**
      * 执行Excel导入，本方法更新插入比对是主键，在其他业务中可以根据具体的业务需求进行记录比对
      * @param excelStream  文件输入流
      * @throws Exception   异常信息
     */
    @Override
    public void executeExcelImport(InputStream excelStream) throws Exception {
        //根据实体对象装在excel数据
        List<ExNotice> exNoticeList = ExcelExportImportor.loadExcel(excelStream, getExcelHeadInfo(), ExNotice.class);
        List<String> pkNotices = new ArrayList<String>();
        //得到excle数据中的实体主键集合
        for (ExNotice exNotice : exNoticeList) {
            pkNotices.add(exNotice.getPkNotice());
        }

        //根据excel中的主键获取数据库中的主键
        List<String> dbPkList = exNoticeMapper.selectByPrimaryKeysExists(pkNotices);

        //迭代excel数据集
        for (ExNotice exNotice : exNoticeList) {
            //如果excel中的数据主键在数据库中存在，则更新，否则新增。
            if (dbPkList.contains(exNotice.getPkNotice())) {
                exNoticeMapper.update(exNotice);
            } else {
                exNoticeMapper.insert(exNotice);
            }
        }
    }

    /**
      * 获得excel头部信息
      * @return 信息map集合
     */
    @SuppressWarnings({ "unchecked" })
    private Map<String, String> getExcelHeadInfo() {
        //建立excel头部标题和实体属性对应map对象
        String values = "{'pkNotice':'业务主键','noticeCode':'通知编码','noticeName':'通知名称','dspDeptName':'发文单位','dspDate':'发文时间','noticeContent':'发文内容'}";
        return net.sf.json.JSONObject.fromObject(values);
    }
}
