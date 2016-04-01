/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.owlcis.service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lam
 */
public class StudentFlowchart {
    private List<CourseNode> semesterList;
    private ArrayList<HeaderItem> header;
    
    public StudentFlowchart() {
        semesterList = new ArrayList<>();
    }
    
    /**
     * @return the semesterList
     */
    public List<CourseNode> getSemesterList() {
        return semesterList;
    }

    /**
     * @param semesterList the semesterList to set
     */
    public void setSemesterList(ArrayList<CourseNode> semesterList) {
        this.semesterList = semesterList;
    }

    /**
     * @return the header
     */
    public ArrayList getHeader() {
        return header;
    }

    /**
     * @param header the header to set
     */
    public void setHeader(ArrayList header) {
        this.header = header;
    }
}

class HeaderItem {
    String semester;
    int level;
    HeaderItem(String s, int l) {
        this.semester = s;
        this.level = l;
    }
}
