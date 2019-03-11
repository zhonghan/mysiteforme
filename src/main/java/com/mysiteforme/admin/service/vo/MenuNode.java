package com.mysiteforme.admin.service.vo;

import java.util.ArrayList;
import java.util.List;

public class MenuNode implements Comparable<MenuNode> {
    private Long id;
    private String name;
    private Long parentId;
    private int sequence;
    private int level;
    private List<MenuNode> subMenuNodeList;


    public Long getId() {
        return id;
    }

    public MenuNode setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public MenuNode setName(String name) {
        this.name = name;
        return this;
    }

    public Long getParentId() {
        return parentId;
    }

    public MenuNode setParentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public MenuNode setLevel(int level) {
        this.level = level;
        return this;
    }

    public int getSequence() {
        return sequence;
    }

    public MenuNode setSequence(int sequence) {
        this.sequence = sequence;
        return this;
    }

    public List<MenuNode> getSubMenuNodeList() {
        if(subMenuNodeList==null) {
            subMenuNodeList = new ArrayList<>();
        }
        return subMenuNodeList;
    }

    public MenuNode setSubMenuNodeList(List<MenuNode> subMenuNodeList) {
        this.subMenuNodeList = subMenuNodeList;
        return this;
    }

    @Override
    public int compareTo(MenuNode o) {
        return this.sequence - o.getSequence();
    }
}
