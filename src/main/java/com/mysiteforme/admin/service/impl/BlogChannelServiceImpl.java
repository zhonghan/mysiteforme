package com.mysiteforme.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mysiteforme.admin.dao.BlogChannelDao;
import com.mysiteforme.admin.entity.BlogChannel;
import com.mysiteforme.admin.entity.VO.ZtreeVO;
import com.mysiteforme.admin.service.BlogChannelService;
import com.mysiteforme.admin.service.vo.MenuNode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 博客栏目 服务实现类
 * </p>
 *
 * @author wangl
 * @since 2018-01-17
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BlogChannelServiceImpl extends ServiceImpl<BlogChannelDao, BlogChannel> implements BlogChannelService {

    @Override
    public List<MenuNode> getMenuNodeTree() {

        List<MenuNode>  allMenuNode = baseMapper.getAllVisibleMenus();

        List<MenuNode> root = allMenuNode.stream().filter(i->i.getParentId()== null).collect(Collectors.toList());
        Map<Long, MenuNode> map = root.stream().collect(Collectors.toMap(MenuNode::getId, a -> a,(k1,k2)->k1));
        List<MenuNode> others = allMenuNode.stream().filter(i->i.getParentId()!= null).collect(Collectors.toList());
        for(MenuNode node : others) {
            MenuNode parent = map.get(node.getParentId());
            parent.getSubMenuNodeList().add(node);
            map.put(node.getId(), node);
        }
        return root;
    }



    @Override
    public List<ZtreeVO> selectZtreeData() {
        Map<String,Object> map = Maps.newHashMap();
        map.put("pid",null);
        return baseMapper.selectZtreeData(map);
    }


    @Override
    public List<BlogChannel> selectChannelList() {
        Map<String,Object> map = Maps.newHashMap();
        map.put("parentId",null);
        List<BlogChannel> list = Lists.newArrayList();
        try {
            list = baseMapper.selectChannelData(map);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void saveOrUpdateChannel(BlogChannel blogChannel) {
        try {
            insertOrUpdate(blogChannel);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getCountByName(String name) {
        EntityWrapper<BlogChannel> wrapper = new EntityWrapper<>();
        wrapper.eq("name",name);
        wrapper.eq("del_flag",false);
        return selectCount(wrapper);
    }


    @Override
    public List<BlogChannel> getChannelListByWrapper(int limit, EntityWrapper<BlogChannel> wrapper) {
        return selectPage(new Page<>(1,limit),wrapper).getRecords();
    }


    @Override
    public List<BlogChannel> getParentsChannel(Long channelId) {
        EntityWrapper<BlogChannel> wrapper = new EntityWrapper<>();
        wrapper.eq("del_flag",false);
        wrapper.eq("id",channelId);
        BlogChannel blogChannel = selectOne(wrapper);
        String[] parentIds = blogChannel.getParentIds().split(",");
        List<Long> ids = Lists.newArrayList();
        for(int i=0;i<parentIds.length;i++){
            ids.add(Long.valueOf(parentIds[i]));
        }
        List<BlogChannel> channelList = selectBatchIds(ids);
        return channelList;
    }

    @Override
    public BlogChannel getChannelByHref(String href) {
        EntityWrapper<BlogChannel> wrapper = new EntityWrapper<>();
        wrapper.eq("del_flag",false);
        wrapper.eq("href",href);
        BlogChannel c  = selectOne(wrapper);
        return c;
    }
}
