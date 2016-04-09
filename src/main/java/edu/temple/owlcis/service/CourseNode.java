//for schedule
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.owlcis.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Lam
 */
public class CourseNode {
    String name;
    String parent;
    private List<CourseNode> children;
    
    CourseNode() {
        this.children = new ArrayList<>(); 
    }
    
    CourseNode(String n, String p) {
        this();
        this.name = n;
        this.parent = p;
    }
    
    public void addCourse(Schedule sch) {
        CourseNode node = new CourseNode();
        node.name = sch.courseID;
        node.parent = this.name;
        this.children.add(node);
    }

    /**
     * @return the children
     */
    public List<CourseNode> getChildren() {
        return this.children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(List<CourseNode> children) {
        this.children = children;
    }
}