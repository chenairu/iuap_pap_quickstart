package com.yonyou.iuap.example.article.web.controller;

import com.yonyou.iuap.base.web.BaseController;
import com.yonyou.iuap.example.article.entity.ExampleArticle;
import com.yonyou.iuap.example.article.service.ExampleArticleService;
import com.yonyou.iuap.mvc.type.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * com.yonyou.iuap.example.article.web.controller
 *
 * @author guoxh
 * @date 2018/5/31 16:58
 * @description
 */
@RestController
@RequestMapping("/exampleArticle")
public class ExampleArticleController extends BaseController {

    @Autowired
    private ExampleArticleService exampleArticleService;

    @RequestMapping(value = {"/list"},method = {RequestMethod.GET})
    public Object list(PageRequest pageRequest, SearchParams searchParams){
        Page<ExampleArticle> list = exampleArticleService.selectAllByPage(pageRequest,searchParams);

        return buildSuccess(list);
    }
    @RequestMapping(value = {"/save"},method = {RequestMethod.POST})
    public Object save(@RequestBody  List<ExampleArticle> exampleArticles){
        exampleArticleService.save(exampleArticles);
        return buildSuccess();
    }
    @RequestMapping(value = {"/delete"},method = {RequestMethod.POST})
    public Object delete(@RequestBody List<ExampleArticle> exampleArticles){
        exampleArticleService.batchDelete(exampleArticles);
        return buildSuccess();
    }

    @RequestMapping(value = "/updateStatus",method = RequestMethod.POST)
    public Object updateStatus(@RequestBody List<ExampleArticle> exampleArticles){
        exampleArticleService.updateSeletive(exampleArticles);
        return buildSuccess();
    }
}
