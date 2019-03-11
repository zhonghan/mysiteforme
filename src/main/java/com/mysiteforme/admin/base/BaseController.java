package com.mysiteforme.admin.base;

import com.mysiteforme.admin.config.UserHolder;
import com.mysiteforme.admin.entity.User;
import com.mysiteforme.admin.service.BlogArticleService;
import com.mysiteforme.admin.service.BlogChannelService;
import com.mysiteforme.admin.service.BlogCommentService;
import com.mysiteforme.admin.service.BlogTagsService;
import com.mysiteforme.admin.service.DictService;
import com.mysiteforme.admin.service.LogService;
import com.mysiteforme.admin.service.MenuService;
import com.mysiteforme.admin.service.RescourceService;
import com.mysiteforme.admin.service.RoleService;
import com.mysiteforme.admin.service.SiteService;
import com.mysiteforme.admin.service.TableService;
import com.mysiteforme.admin.service.UploadInfoService;
import com.mysiteforme.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
	
	public User getCurrentUser() {
		return UserHolder.get();
	}

	@Autowired
	protected UserService userService;

	@Autowired
	protected MenuService menuService;

	@Autowired
	protected RoleService roleService;

	@Autowired
	protected DictService dictService;

	@Autowired
	protected RescourceService rescourceService;

	@Autowired
	protected TableService tableService;

	@Autowired
	protected SiteService siteService;

	@Autowired
	protected LogService logService;

	@Autowired
	protected BlogArticleService blogArticleService;

	@Autowired
	protected BlogChannelService blogChannelService;

	@Autowired
	protected BlogCommentService blogCommentService;

	@Autowired
	protected BlogTagsService blogTagsService;


	@Autowired
	protected UploadInfoService uploadInfoService;
}
