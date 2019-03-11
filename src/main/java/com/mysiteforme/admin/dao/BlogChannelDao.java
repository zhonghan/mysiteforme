package com.mysiteforme.admin.dao;

import com.mysiteforme.admin.entity.BlogChannel;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mysiteforme.admin.entity.VO.ZtreeVO;
import com.mysiteforme.admin.service.vo.MenuNode;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 博客栏目 Mapper 接口
 * </p>
 *
 * @author wangl
 * @since 2018-01-17
 */
public interface BlogChannelDao extends BaseMapper<BlogChannel> {

    List<MenuNode> getAllVisibleMenus();

    List<ZtreeVO> selectZtreeData(Map<String,Object> map);

    List<BlogChannel> selectChannelData(Map<String, Object> map);
}
