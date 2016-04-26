/**
 * CIS4398 Projects
 * Spring 2016
 * 4/10/2016
 */
package edu.temple.owlcis.service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lam
 */
public class StudentFlowchart {

    private List<List> list;
    private Member member;
    
    /**
     * Constructor
     */
    public StudentFlowchart() {
        this.list = new ArrayList<>();
        this.member = new Member();
    }
    
    /**
     * Constructor 
     * @param list
     * @param member 
     */
    public StudentFlowchart(List<List> list, Member member) {
        this();
        this.list = list;
        this.member = member;
    }

    /**
     * @return the list
     */
    public List<List> getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(List<List> list) {
        this.list = list;
    }

    /**
     * @return the member
     */
    public Member getMember() {
        return member;
    }

    /**
     * @param member the member to set
     */
    public void setMember(Member member) {
        this.member = member;
    }
    
}
