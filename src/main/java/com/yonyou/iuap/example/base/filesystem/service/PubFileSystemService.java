/*
 * （C）2018 用友网络科技股份有限公司. yonyou Group, All right reserved.
 *
 * 项目名称 : example
 * 创建日期 : 2018年6月12日
 */

package com.yonyou.iuap.example.base.filesystem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.example.base.filesystem.entity.PubFileSystem;
import com.yonyou.iuap.mvc.type.SearchParams;

/**
  * @description 附件管理业务接口
  * @author 姚春雷
  * @date 2018年6月12日 下午1:09:42
  * @version 1.0.0
  *
  *                    修改信息说明：
  * @updateDescription
  * @updateAuthor
  * @updateDate
  */
public interface PubFileSystemService {

    /**
     * 分页查询附件管理，根据分页需求和查询参数
     * @param pageRequest  分页需求
     * @param searchParams 查询参数
     * @return  分页数据
    */
    public Page<PubFileSystem> selectAllByPage(PageRequest pageRequest, SearchParams searchParams);
}
