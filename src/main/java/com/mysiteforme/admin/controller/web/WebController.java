package com.mysiteforme.admin.controller.web;

import com.mysiteforme.admin.entity.BlogArticle;
import com.mysiteforme.admin.service.BlogArticleService;
import com.mysiteforme.admin.service.BlogChannelService;
import com.mysiteforme.admin.service.vo.MenuNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("web")
@Controller
public class WebController {

    @Autowired
    private BlogChannelService blogChannelService;
    @Autowired
    private BlogArticleService blogArticleService;

    @RequestMapping("menuTree")
    @ResponseBody
    public List<MenuNode> getMenuNodeTree() {
        return blogChannelService.getMenuNodeTree();
    }

    @RequestMapping("getByMenuId")
    @ResponseBody
    public BlogArticle getBlogArticle(Long menuId) {
        return blogArticleService.selectOneChannelId(menuId);
    }
}
