/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oopie_mandar;

/**
 *
 * @author manda
 */
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Program_info implements Serializable {

    private String projectName;
  
    private ArrayList<File> codeFilesArray;
    public Program_info(){
        codeFilesArray = new ArrayList<>();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }



    public ArrayList<File> getCodeFilesArray() {
        return codeFilesArray;
    }

    public void setCodeFilesArray(ArrayList<File> codeFilesArray) {
        this.codeFilesArray = codeFilesArray;
    }
}
