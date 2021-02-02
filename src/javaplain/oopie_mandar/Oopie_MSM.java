package javaplain.oopie_mandar;

import org.antlr.runtime.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javaplain.JavaPlainLexer;
import javaplain.JavaPlainParser;

import ParsedData.ParsedData;
import oopie_mandar.Parse_MSM;
/**
 *
 * @author manda
 */
public class Oopie_MSM {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws org.antlr.runtime.RecognitionException
     */
    public static void main(String[] args) throws IOException, RecognitionException {
        // TODO code application logic here
//        System.out.println("Hello There");    
        Parse_MSM pf = new Parse_MSM();
        pf.displaymainwindow();
    }
    public StringBuffer getFinalResult(String file) throws IOException, RecognitionException{
        StringBuffer result_string = new StringBuffer();
        JavaPlainLexer lexer = new JavaPlainLexer(new ANTLRFileStream(file));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaPlainParser parser = new JavaPlainParser(tokens); 
        
        parser.compilationUnit();
        
        if(Utils.First){
    //1. 
        Utils.parserFirst=parser;
        Utils.First = false;
        }
        else{
            Utils.parserSecond=parser;
        }
        ParsedData projectdata = new ParsedData(); 
        
        //String currentClass = null;
        String an_t1 = "";
        String an_t2 = "";
        String desc_t1 = "";
        String desc_t2 = "";
        String prevClass ="";
        
        HashMap<String,HashSet> class_ancestor = new HashMap<String,HashSet>();
       
        for(String currentClass: parser.ancestor.keySet()){
            String copyCurrClass = currentClass;
            int i=1;
            while(true) {
                String childClass=parser.ancestor.get(copyCurrClass);

                ArrayList temp = new ArrayList();
                temp.add(childClass);
                if(!class_ancestor.containsKey(copyCurrClass)){
                            class_ancestor.put(copyCurrClass,new HashSet());
                }
                class_ancestor.get(currentClass).add(childClass);
                copyCurrClass=childClass; 
                if(!parser.ancestor.containsKey(copyCurrClass)) break;
            }
        }
        
        
        
//        System.out.println("Buffer is : " + parser.myBuffer);
//        System.out.println("-------------------------------");
        
        System.out.print(parser);
      
        System.out.println("Ancestor : "+ parser.ancestor);

        System.out.println("classes are : " + parser.classes);
        System.out.println("-------------------------------");
        
        System.out.println("class and its methods : " + parser.hm_class_methods);
        System.out.println("-------------------------------");
        
        System.out.println("class and its variables : " + parser.hm_class_variables);
//        for(Map.Entry<String, List<Variable>> e: parser.hm_class_variables_2.entrySet()) {
//        	System.out.println(e.getKey());
//        	List<Variable> l1 = e.getValue();
//        	for(Variable l : e.getValue()) {
//        		System.out.println(l);
//        	}
//        	for(Variable l2:l1) {
//        		for(Variable l3: l1) {
//        			System.out.println(l2.compareTo(l3));
//        		}
//        	}
//        	
//        }
        
        
        System.out.println("-------------------------------");
        
        System.out.println("class and its Local Variables : " + parser.hm_class_localvariable);
        System.out.println("-------------------------------");
        
        System.out.println("ancestors are : " + class_ancestor);
        System.out.println("-------------------------------");
        
        System.out.println("descendents are : " +parser.descendent);
        System.out.println("-------------------------------");
        
        System.out.println("Associates are : " +parser.hm_class_associates);
        System.out.println("-------------------------------");
        
        System.out.println("Aggregates are : " +parser.hm_class_aggregates);
        System.out.println("-------------------------------");
        
        
        
        
        for(int i =0 ; i< parser.classes.size() ; i++){
            String currClass = parser.classes.get(i).toString();
            
            //adding class name
            result_string.append("Class : " + currClass +"<br>"+"</br>");
            
            //adding data members i.e. variables
            if(parser.hm_class_variables.containsKey(currClass)){
                result_string.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "Data Members :" +"<br>"+"</br>");
                
                List variables = parser.hm_class_variables.get(currClass);
                for(Object variable: variables){
//                    System.out.println("variable " + variable);
                    result_string.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+variable.toString() +"<br>"+"</br>");
                } 
            }
            
            //adding local data member i.e. variables
            if(parser.hm_class_localvariable.containsKey(currClass)){
                result_string.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "Local Variables/Parameters :" +"<br>"+"</br>");
                
                List variables = parser.hm_class_localvariable.get(currClass);
                for(Object variable: variables){
//                    System.out.println("variable " + variable);
                    result_string.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+variable.toString() +"<br>"+"</br>");
                } 
            }            
            
            //adding methods i.e. functions
            if(parser.hm_class_methods.containsKey(currClass)){
                result_string.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "Method Members :" +"<br>"+"</br>");
                
                List methods = parser.hm_class_methods.get(currClass);
                for(Object method: methods){
//                    System.out.println("variable " + method);
                    result_string.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+method.toString() + "<br>"+"</br>");
                } 
            }
            
            //addings aggregates
            if(parser.hm_class_aggregates.containsKey(currClass)){
                result_string.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "Aggregates :" +"<br>"+"</br>");
                
                HashSet aggregates = parser.hm_class_aggregates.get(currClass);
                for(Object aggregate: aggregates){
//                    System.out.println("variable " + method);
                    result_string.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+aggregate.toString() + "<br>"+"</br>");
                } 
            }
            
            //adding associates
            if(parser.hm_class_associates.containsKey(currClass)){
                result_string.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "Associates :" +"<br>"+"</br>");
                
                HashSet associates = parser.hm_class_associates.get(currClass);
                for(Object associate: associates){
//                    System.out.println("variable " + method);
                    result_string.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+associate.toString() + "<br>"+"</br>");
                } 
            }
            
            //adding ancestors 
            if(class_ancestor.containsKey(currClass)){
                result_string.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "Ancestors Classes :" +"<br>"+"</br>");
                
                HashSet ancestors = class_ancestor.get(currClass);
                for(Object ancestor: ancestors){
//                    System.out.println("variable " + method);
                    result_string.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ancestor.toString() + "<br>"+"</br>");
                } 
            }
            
            //adding descendants
            if(parser.descendent.containsKey(currClass)){
                result_string.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "Descendent Classes :" +"<br>"+"</br>");
                
                HashSet descendents = parser.descendent.get(currClass);
                for(Object descendent: descendents){
//                    System.out.println("variable " + method);
                    result_string.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+descendent.toString() + "<br>"+"</br>");
                } 
            }
            result_string.append("<br>" + "</br>");
            
        }
        
//        if(parser.packageName.length() > 0) result_string.append("Package : " + parser.packageName + "<br>" + "</br>");
//        
//        for(String s : parser.myBuffer){
//            String arr[] = s.split(" ");
//            
//            
//            if(arr.length >1 && arr[1].equals("extends")){
//                    System.out.println("current 1 :" + currentClass);
//                    String copyCurrClass=currentClass;
//                    //System.out.println("CURRENT CLASS: " + currentClass);
//                    int i=1;
//                    while(true) {
//                        String childClass=parser.ancestor.get(copyCurrClass);
//                        //System.out.println("Level: " + (i++) + " " + childClass);
//                        ArrayList temp = new ArrayList();
//                        temp.add(childClass);
//                        if(!class_ancestor.containsKey(copyCurrClass)){
//	                       	  	class_ancestor.put(copyCurrClass,new HashSet());
//                        }
//	                class_ancestor.get(currentClass).add(childClass);
//                        copyCurrClass=childClass; 
//                        if(!parser.ancestor.containsKey(copyCurrClass)) break;
//                    }   
//
//                    for(String key : parser.ancestor.keySet()){
//                        if(key.equalsIgnoreCase(currentClass)){
//                            an_t1 = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+"Ancestor Classes :"+"<br>"+"</br>";
//                            an_t2 = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + parser.ancestor.get(currentClass)+"<br>"+"</br>";
////                            if(class_ancestor.containsKey(currentClass)){
////                                HashSet temp =class_ancestor.get(currentClass);
////                                Iterator<String> it = temp.iterator();
////                                while(it.hasNext()){
////                                an_t2 += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + it.next()+"<br>"+"</br>";
////                                }
////                            }
//                        }
//                   }
//                }
//            
//            else if(arr[0].equalsIgnoreCase("Class")){
//                currentClass = arr[2];
//                System.out.println("current 2 : " + currentClass);
//                if(an_t1.length() > 1){
//                    result_string.append(an_t1);
//                    result_string.append(an_t2);
//                }
//                
//                if(parser.descendent.containsKey(prevClass)){
//                result_string.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+"Descendent Classes :"+"<br>"+"</br>");
//                result_string.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + parser.descendent.get(prevClass)+"<br>"+"</br>");
//                 }
//                String t = "<br>" +s;
//                t += "<br>"+"</br>";
//                result_string.append(t);
//                
//                if(parser.hm_class_variables.containsKey(currentClass)){
//                    result_string.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+"Data Members:"+"<br>"+"</br>");
//                    for(String x: parser.hm_class_variables.get(currentClass)){
//                        result_string.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + x +"<br>"+"</br>");
//                    }
//                }
//            }
//            
//            if(!arr[0].equalsIgnoreCase("Class") && !arr[0].equalsIgnoreCase("mm") && !arr[0].equalsIgnoreCase("dm")){
//                if (arr.length > 1){
//                    if(!arr[1].equalsIgnoreCase("extends")){
//                        String t = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + s;
//                        t += "<br>"+"</br>";
//                        result_string.append(t);
//                        prevClass = currentClass;
//                    }
//                    else{
//                        String t = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + s;
//                        t += "<br>"+"</br>";
//                        if(!arr[1].equalsIgnoreCase("extends")){
//                            result_string.append(t);
//                        }
//                        prevClass = currentClass;
//                    }
//                }
//            }
//            
//            else if(arr[0].equalsIgnoreCase("mm")){
//                String m = "";
//                m += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+"Method Members :"+"<br>"+"</br>";
//                result_string.append(m);   
//            }
//                  
//            else if(arr[0].equalsIgnoreCase("dm")){
//                String m = "";
//                m += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+"Method Members :"+"<br>"+"</br>";
//                result_string.append(m);   
//            }
//        }

    projectdata.storeResult(result_string);
    Utils.compareParsers();
    return result_string;
    }
}