package com.mysiteforme.admin.controller;

import com.google.common.collect.Lists;
import com.mysiteforme.admin.annotation.SysLog;
import com.mysiteforme.admin.base.BaseController;
import com.mysiteforme.admin.base.MySysUser;
import com.mysiteforme.admin.entity.Menu;
import com.mysiteforme.admin.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

@Controller
public class LoginController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@Value("${server.port}")
	private String port;

	@GetMapping("login")
	public String login(HttpServletRequest request) {
		return "redirect:index";
	}

	
	@GetMapping("index")
	public String showView(Model model){
		return "index";
	}



	@GetMapping("main")
	public String main(Model model){
		Map map = userService.selectUserMenuCount();
		User user = userService.findUserById(MySysUser.id());
		Set<Menu> menus = user.getMenus();
		java.util.List<Menu> showMenus = Lists.newArrayList();
		if(menus != null && menus.size()>0){
			for (Menu menu : menus){
				if(StringUtils.isNotBlank(menu.getHref())){
					Long result = (Long)map.get(menu.getPermission());
					if(result != null){
						menu.setDataCount(result.intValue());
						showMenus.add(menu);
					}
				}
			}
		}
		showMenus.sort(new MenuComparator());
		model.addAttribute("userMenu",showMenus);
		return "main";
	}

	/**
	 *  空地址请求
	 * @return
	 */
	@GetMapping(value = "")
	public String index() {
		LOGGER.info("这事空地址在请求路径");
		Subject s = SecurityUtils.getSubject();
		return s.isAuthenticated() ? "redirect:index" : "login";
	}

	@GetMapping("systemLogout")
	@SysLog("退出系统")
	public String logOut(HttpServletRequest request, HttpServletResponse response){
		request.getSession().removeAttribute("USER_SESSION_KEY");
		return "redirect:/index";
	}

	@GetMapping("callback")
	@SysLog("callback")
	public String loginCallback(){
		return "redirect:/index";
	}
}

class MenuComparator implements Comparator<Menu>{

	@Override
	public int compare(Menu o1, Menu o2) {
		if(o1.getSort()>o2.getSort()){
			return -1;
		}else {
			return 0;
		}

	}
}