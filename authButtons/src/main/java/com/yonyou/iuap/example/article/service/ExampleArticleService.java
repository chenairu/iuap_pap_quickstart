package com.yonyou.iuap.example.article.service;

import com.yonyou.iuap.example.article.dao.ExampleArticleMapper;
import com.yonyou.iuap.example.article.entity.ExampleArticle;
import com.yonyou.iuap.mvc.type.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * com.yonyou.iuap.example.article.service
 *
 * @author guoxh
 * @date 2018/5/31 16:58
 * @description
 */
@Service
public class ExampleArticleService {

    @Autowired
    private ExampleArticleMapper exampleArticleMapper;

    public Page<ExampleArticle> selectAllByPage(PageRequest pageRequest, SearchParams searchParams){
        Page<ExampleArticle> tmpData = exampleArticleMapper.selectAllByPage(pageRequest,searchParams.getSearchMap()).getPage();
        return tmpData;
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(List<ExampleArticle> list){
        for(ExampleArticle article : list){
            if(article.getId() == null){
                this.insert(article);
            }else{
                this.update(article);
            }
        }
    }

    public int insert(ExampleArticle article){
        return exampleArticleMapper.insert(article);
    }
    public int update(ExampleArticle article){
        return exampleArticleMapper.updateByPrimaryKey(article);
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<ExampleArticle> list){
        for(ExampleArticle article : list){
            this.delete(article);
        }
    }
    public int delete(ExampleArticle article){
        return exampleArticleMapper.deleteByPrimaryKey(article.getId());
    }

}
