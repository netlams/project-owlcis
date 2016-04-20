/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.owlcis.service;

import java.util.ArrayList;

/**
 *
 * @author Lam
 */
public class Prerequisite {
    private ArrayList<String> list;
    
    /**
     * Constructor
     */
    public Prerequisite() {
        this.list = new ArrayList(0);
    }
    
    /**
     * Constructor
     */
    public Prerequisite(ArrayList list) {
        this.list = list;
    }
    
    /**
     * @return the list
     */
    public ArrayList getList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(ArrayList list) {
        this.list = list;
    }
    
    @Override
    public String toString() {
        String temp = "";
        temp += list.stream();
        return temp;
    }  
}
