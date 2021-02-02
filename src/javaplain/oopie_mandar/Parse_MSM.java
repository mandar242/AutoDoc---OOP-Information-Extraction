/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oopie_mandar;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import javaplain.JavaPlainParser;
import javaplain.JavaPlainLexer;
import javaplain.JavaPlainLexer;
import javaplain.JavaPlainParser;

/**
 *
 * @author manda
 */
public class Parse_MSM {
     private static Parse_MSM Parse_MSM_Instance = null;

    private Program_info projectData;

    

    public Program_info getProjectData() {
        return projectData;
    }
    public void setProjectData(Program_info projectData) {
        this.projectData = projectData;
    }


    public static Parse_MSM getInstance(){
        if(Parse_MSM_Instance == null){
            Parse_MSM_Instance = new Parse_MSM();
        }
        return Parse_MSM_Instance;
    }


	public void displaymainwindow() {
		FirstWindow mw = new FirstWindow(Parse_MSM.getInstance());
    	        mw.setVisible(true);
		
	}
}
