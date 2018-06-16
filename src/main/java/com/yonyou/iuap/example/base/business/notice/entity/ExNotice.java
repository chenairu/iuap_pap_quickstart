/*
 * （C）2018 用友网络科技股份有限公司. yonyou Group, All right reserved.
 *
 * 项目名称 : example
 * 创建日期 : 2018年6月1日
 */

package com.yonyou.iuap.example.base.business.notice.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonyou.iuap.example.base.common.entity.AbsGenericEntity;

/**
  * @description 通知公告实体,实体使用@JsonIgnoreProperties(ignoreUnknown = true)注解，
  * 是为了防止实体中存在的字段,前台表单中不存在,在springmvc的Controller层中又使用实体作为接收参数，页面报400错误。
  * 继承AbsGenericEntity必须复写getMetaDefinedName方法和getNamespace方法，否则页面报404错误
  * @author 姚春雷
  * @date 2018年6月1日 下午1:22:22
  * @version 1.0.0
  *
  *                    修改信息说明：
  * @updateDescription
  * @updateAuthor
  * @updateDate
  */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExNotice extends AbsGenericEntity {

    /**
      *
      */
    private static final long serialVersionUID = 1L;

    private String pkNotice; //通知主键

    private String noticeCode; //通知编号

    private String noticeName; //通知名称

    private String dspDept; //发文单位

    private String dspDeptName; //发文单位

    private Date dspDate; //发文时间

    private String noticeContent; //发文内容

    //属性使用包装类，不要使用基本类型，否则会出现问题，例如int和Integer
    //int型属性值如果为空，取出来默认为0，而Integer型属性为空，取出来依然是空
    private Integer emergency; //紧急程度

    public String getPkNotice() {
        return pkNotice;
    }

    public void setPkNotice(String pkNotice) {
        this.pkNotice = pkNotice;
    }

    public String getNoticeCode() {
        return noticeCode;
    }

    public void setNoticeCode(String noticeCode) {
        this.noticeCode = noticeCode;
    }

    public String getNoticeName() {
        return noticeName;
    }

    public void setNoticeName(String noticeName) {
        this.noticeName = noticeName;
    }

    public String getDspDept() {
        return dspDept;
    }

    public void setDspDept(String dspDept) {
        this.dspDept = dspDept;
    }

    public String getDspDeptName() {
        return dspDeptName;
    }

    public void setDspDeptName(String dspDeptName) {
        this.dspDeptName = dspDeptName;
    }

    public Date getDspDate() {
        return dspDate;
    }

    public void setDspDate(Date dspDate) {
        this.dspDate = dspDate;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public Integer getEmergency() {
        return emergency;
    }

    public void setEmergency(Integer emergency) {
        this.emergency = emergency;
    }

    @Override
    public String getMetaDefinedName() {
        return "example_print";
    }

    @Override
    public String getNamespace() {
        return "example";
    }

}
