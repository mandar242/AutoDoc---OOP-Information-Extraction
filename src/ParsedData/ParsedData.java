/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParsedData;


import java.io.File;
import org.antlr.runtime.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javaplain.JavaPlainLexer;
import javaplain.JavaPlainParser;
import oopie_mandar.Parse_MSM;
//import javaplain.file_constants;

import java.io.File; 
/**
 *
 * @author manda
 */
public class ParsedData{
    
    public static final String ROOT_PROJECT_PATH = System.getProperty("user.dir");
    public static final String PROJECT_DATA_PATH = ROOT_PROJECT_PATH + "/projectData/";
    public static final String PROJECT_EXTENSION = ".ms";
    
    public static void main(String[] args) throws IOException, RecognitionException {
        // TODO code application logic here
//        System.out.println("Hello There");    
        Parse_MSM pf = new Parse_MSM();
        pf.displaymainwindow();
    }
    //public void storeResult(List classes,Map<String, ArrayList<String>> hm_class_methods ,HashMap<String,List<String>> hm_class_variables,HashMap<String,List<String>> hm_class_localvariable, HashMap<String,HashSet> hm_class_aggregates,HashMap<String,HashSet> hm_class_associates,HashMap<String,HashSet> ancestor, Map<String,HashSet> descendent) throws IOException, RecognitionException{
       
//        
//        System.out.println(classes);   
//        System.out.println(hm_class_methods);
//        System.out.println( hm_class_variables);   
//        System.out.println(hm_class_localvariable);
//        System.out.println(ancestor);
//        System.out.println(descendent);        
//        System.out.println(hm_class_associates);       
//        System.out.println(hm_class_aggregates);
    public void storeResult(StringBuffer gui_data){
        
        File myObj = new File("storedresult.java"); 
        
    }
}