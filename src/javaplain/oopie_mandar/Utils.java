package javaplain.oopie_mandar;

import java.util.HashMap;
import java.util.Map;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javaplain.JavaPlainParser;
import javaplain.JavaPlainParser.Method;
import javaplain.JavaPlainParser.Variable;

public class Utils {
    
	public static boolean First=true;
	
	
	public static JavaPlainParser parserFirst;
        public static JavaPlainParser parserSecond;
        private static StringBuilder output = new StringBuilder();
        public static String secondFile_Path ;
        private static void init() {
            Date date=java.util.Calendar.getInstance().getTime();  
            output.append("\n////////////////////////////////////////////////////////////////////\n//*****AutoDoc Commentary*****\n");
            output.append("// AutoDoc scanned this class on ").append(date);
            output.append(" and noted the following changes \n");
        }
	
        public static void compareParsers(){
            if(parserFirst==null || parserSecond==null) return;
            init();
            compareVariables();
            compareMethods();
            compareAncestors();
            output.append("//***** End AutoDoc Commentary for class ").append(parserFirst.classes.get(0));
            output.append("\n////////////////////////////////////////////////////////////////////");
            output.append("\n");
            appendToFile();
        } 
        
        private static void appendToFile() {
            StringBuilder currContent = new StringBuilder();
            File myObj = new File(Utils.secondFile_Path);
            try(Scanner myReader = new Scanner(myObj);) {
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    currContent.append(data).append("\n");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            
            FileWriter fw = null;
            BufferedWriter bw = null;
            PrintWriter pw = null;
            try (FileWriter f = new FileWriter(Utils.secondFile_Path, false); BufferedWriter b = new BufferedWriter(f); PrintWriter p = new PrintWriter(b);) { 
                p.println(output.toString());
                p.println(currContent.toString());
            } catch (IOException i) {
                i.printStackTrace();
            }
        }
        
        private static void compareAncestors() {
            Map<String,String> var1 = parserFirst.ancestor;
            Map<String,String> var2 = parserSecond.ancestor;
            
            for(Map.Entry<String, String> E : var2.entrySet()){
                if(!var1.get(E.getKey()).equals(E.getValue())) {
                    output.append("//").append(E.getKey() +" parent has been changed from " + var1.get(E.getKey()) + " to " + E.getValue() + " since the previous scan");
                    output.append("\n");
                }
            }
            

        }
        
        private static void compareVariables(){
            HashMap<String,List<Variable>> var1 = parserFirst.hm_class_variables_2;
            HashMap<String,List<Variable>> var2 = parserSecond.hm_class_variables_2;
            
            for(Map.Entry<String, List<Variable>> E : var2.entrySet()){
                
                if(var1.containsKey(E.getKey())){
                    List<Variable> list1 = var1.get(E.getKey());
                    List<Variable> list2 = var2.get(E.getKey());
                
                    output.append("//").append(" Difference in class " + E.getKey() + "'s variables: ");
                     output.append("\n");
                   for(Variable l1: list1) {
                        List<Variable> list2copy = new ArrayList<>(list2);
                        boolean changed=false;
                        for(Variable l2: list2copy) {
                            switch(l1.compareTo(l2)) {
                                case 2: output.append("//").append(" Access specifier for method " + l1.getVName() + " changed from " + l1.getAccessS() + " to " + l2.getAccessS() +" since the previous scan");
                                                        output.append("\n");
list2.remove(l2);
                                    changed=true;
                                    break;
                                case 3: output.append("//").append(" Data type for " + l1.getVName() + " changed from " +  l1.getDataType()+ " to " + l2.getDataType() + " since the previous scan");
                                                        output.append("\n");
list2.remove(l2);
                                    changed=true;
                                    break;
                                case 0: list2.remove(l1);
                                    changed=true;
                                    break;
                                default:
                                break;
                            }
                        }
                        if(!changed) {output.append("//").append(" Variable " + l1.getVName() + " deleted" + " since the previous scan");                    output.append("\n");
}
                    }
                    
                    for(Variable l2: list2) {
                         output.append("//").append(" Variable " + l2.getVName() + " added" + " since the previous scan");
                                        output.append("\n");
}
                }
                else {// var2 has added new class
                    
                }
                
            }
            //deleted class output var1.keyset-var2.keyset
        }
        
        private static void compareMethods() {
            Map<String,List<Method>> var1 = parserFirst.hm_class_methods_2;
            Map<String,List<Method>> var2 = parserSecond.hm_class_methods_2;
            
            for(Map.Entry<String, List<Method>> E : var2.entrySet()){
                //output.append("//").append(var2.entrySet());
                
                if(var1.containsKey(E.getKey())){
                    List<Method> list1 = var1.get(E.getKey());
                    List<Method> list2 = var2.get(E.getKey());
                
                    output.append("//").append(" Difference in class " + E.getKey() + "'s methods: ");
                                        output.append("\n");
for(Method l1: list1) {
                        List<Method> list2copy = new ArrayList<>(list2);
                        boolean changed=false;
                        for(Method l2: list2copy) {
                            switch(l1.compareTo(l2)) {
                                case 2: output.append("//").append(" Access specifier for method " + l1.getVName() + " changed from " + l1.getAccessS() + " to " + l2.getAccessS() + " since the previous scan");
                                                        output.append("\n");
list2.remove(l2);
                                    changed=true;
                                    break;
                                case 3: output.append("//").append(" Return type for method " + l1.getVName() + " changed from " +  l1.getReturnType()+ " to " + l2.getReturnType()  + " since the previous scan");
                                                        output.append("\n");
list2.remove(l2);
                                    changed=true;
                                    break;
                                case 0: list2.remove(l1);
                                    changed=true;
                                    break;
                                default:
                                break;
                            }
                        }
                        if(!changed) {output.append("//").append(" Method " + l1.getVName() + " deleted" + " since the previous scan");                    output.append("\n");
                        }
                    }
                    
                    for(Method l2: list2) {
                         output.append("//").append(" Method " + l2.getVName() + " added" + " since the previous scan");
                                        output.append("\n");
}
                }
                else {// var2 has added new class
                    
                }
                
            }
            //deleted class output var1.keyset-var2.keyset
        }
}
