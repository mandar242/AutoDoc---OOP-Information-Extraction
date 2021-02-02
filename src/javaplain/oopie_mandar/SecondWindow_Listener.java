/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oopie_mandar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javaplain.oopie_mandar.Utils;
/**
 *
 * @author manda
 */
public class SecondWindow_Listener implements ActionListener {

    private static SecondWindow_Listener self = null;
    private ProjectName_Window newProjectWindow;

    public SecondWindow_Listener(ProjectName_Window newProjectWindow){
        this.newProjectWindow = newProjectWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== this.newProjectWindow.ok){
            String projectName_str = this.newProjectWindow.projectName_text.getText();
           
            boolean closeWindow = false;
            Program_info project = new Program_info();

            if(projectName_str == null || projectName_str.length()==0){
              
                this.newProjectWindow.projectName_text.requestFocusInWindow();
                return;
            } else {
               
                File projectDir = new File(constants.PROJECT_DATA_PATH);
                File tempFile = new File(projectDir.getPath() + "/" + projectName_str + constants.PROJECT_EXTENSION);
                Utils.secondFile_Path = projectDir.getPath() + "/   " + constants.PROJECT_FILE_TYPES + "/ " + constants.P_MENU_ITEM_PROJECT_CODE_ADD_CODE ;
                if(tempFile.exists() == true){
           
                    this.newProjectWindow.projectName_text.requestFocusInWindow();
                    return;
                }

                closeWindow = true;
                project.setProjectName(projectName_str);
            }



            if(closeWindow){
                //pass project name to main frame
                String title = constants.PROJECT_TITLE + " - "+ projectName_str;
                this.newProjectWindow.mainWindow.setTitle(title);
                Parse_MSM.getInstance().setProjectData(project);
                this.newProjectWindow.disposeWindow();
                this.newProjectWindow.mainWindow.createNewProject();
            }
        }

        if(e.getSource()== this.newProjectWindow.cancel){
            this.newProjectWindow.disposeWindow();
        }
    }
}