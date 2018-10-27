package com.twoforboth.webcrawler;

import java.util.ArrayList;
import java.util.List;

public class Site {

    private String url;
    private Site parent;
    private List<Site> children = new ArrayList<>();
    private int level;

    public Site(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public Site getParent() {
        return parent;
    }

    public void setParent(Site parent) {
        this.parent = parent;
    }



    public void setUrl(String url) {
        this.url = url;
    }

    public List<Site> getChildren() {
        return children;
    }

    public void setChildren(List<Site> children) {
        this.children = children;
    }

    public void addChildSite(Site site) {
        children.add(site);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {

        String parentString;
        if (parent == null) {
            parentString = "null";
        }
        else {
            parentString = parent.getUrl();
        }

        return "URL: " + url + " Parent: " + parentString + " level: " + level + " children count: " + children.size();
    }

}
