/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oopie_mandar;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaplain.oopie_mandar.Utils;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import org.antlr.runtime.RecognitionException;

/**
 *
 * @author manda
 */

public class FirstWindow_Listener implements ActionListener {
	  private static FirstWindow_Listener actionListener_MainWindow_Instance = null;
	    private Component context;
	    private static JFrame activeSubWindow = null;

	    private HashMap<String, File> selectedCodeFiles = new HashMap<>();
	    Parse_MSM pf = new Parse_MSM();
	    private FirstWindow_Listener(Component context) {
	        this.context = context;
	    }

	    public static FirstWindow_Listener getInstance(Component context) {
	        if (actionListener_MainWindow_Instance == null)
	            actionListener_MainWindow_Instance = new FirstWindow_Listener(context);
	        return actionListener_MainWindow_Instance;
	    }

	    public void actionPerformed(ActionEvent e) {
	        switch (e.getActionCommand()) {
	            case constants.P_MENU_ITEM_FILE_NEW:
	                selectedCodeFiles = new HashMap<>();
	                activeSubWindow = newProjectWindow(context);
	                break;
	            case constants.P_MENU_ITEM_FILE_SAVE:
	                selectedCodeFiles = new HashMap<>();
                { 
                    try {
                        boolean isSaved = saveProject(Parse_MSM.getInstance().getProjectData());
                    } catch (IOException ex) {
                        Logger.getLogger(FirstWindow_Listener.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
	                break;
	            case constants.P_MENU_ITEM_PROJECT_CODE_ADD_CODE:
	                File[] selectedFiles  = openCodeFiles(context);
	                if(selectedFiles!=null) {
//                            Arrays.asList(selectedFiles).stream().forEach(System.out::println);
	                    for (File selectedFile : selectedFiles) {
	                    	Parse_MSM.getInstance().getProjectData().getCodeFilesArray().add(selectedFile);
	                        selectedCodeFiles.put(selectedFile.getName(), selectedFile);
                                Utils.secondFile_Path = selectedFile.getAbsolutePath();
	                    }
                            
	                    ((FirstWindow)context).toggleProjectCode(true);
	                }
	                break;
	            case constants.P_MENU_ITEM_METRICS_PROJECT_CODE_STATISTICS:
	                if(this.selectedCodeFiles.size() > 0){
	                    HashMap<String, File> codeFiles = selectedCodeFiles;
	                    for (Map.Entry mapElement : codeFiles.entrySet()) {
                                try {
                                    addCodeWindowPane((File)mapElement.getValue());
                                } catch (RecognitionException ex) {
                                    Logger.getLogger(FirstWindow_Listener.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IOException ex) {
                                    Logger.getLogger(FirstWindow_Listener.class.getName()).log(Level.SEVERE, null, ex);
                                }
	                    }
	                    selectedCodeFiles = new HashMap<>();
	                    ((FirstWindow)context).toggleProjectCode(false);
	                }
	            default:
	        }
	    }
	    private JFrame newProjectWindow(Component context) {
	        ProjectName_Window newProject = null;
	        if (context instanceof FirstWindow) {
	            if (activeSubWindow != null) {
	                activeSubWindow.dispose();
	                activeSubWindow = null;
	            }
	            newProject = new ProjectName_Window((FirstWindow) context);
	            newProject.setVisible(true);
	        }
	        return newProject;
	    }
	    private boolean saveProject(Program_info projectData) throws IOException  {
	    	FileOutputStream outFile = null;
	        String projectName = projectData.getProjectName();
	        File projectDir = new File(constants.PROJECT_DATA_PATH);
	        if (!projectDir.exists()) {
	            projectDir.mkdir();
	        }
	        File projectFile = new File(projectDir.getPath() + "/" + projectName + constants.PROJECT_EXTENSION);

	        List<File> codeFiles = Parse_MSM.getInstance().getProjectData().getCodeFilesArray();
	        projectData.setCodeFilesArray((ArrayList<File>) codeFiles);
	 
	        try {
				outFile = new FileOutputStream(projectFile);
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}
	        ObjectOutputStream outObject = new ObjectOutputStream(outFile);
	        outObject.writeObject(projectData);
	        outObject.close();
	        return true;
	    }
	    private File[] openCodeFiles(Component context) {
	        FileNameExtensionFilter filter = new FileNameExtensionFilter(constants.PROJECT_FILE_TYPES, constants.PROJECT_FILE_TYPE,"java");
	        JFileChooser fileChooser = new JFileChooser();
	        fileChooser.setFileFilter(filter);
	        fileChooser.setMultiSelectionEnabled(true);
	        File[] selectedFiles = null;
	        int fileChooserOption = fileChooser.showOpenDialog(context);
	        Program_info p1 = null;
	        if (fileChooserOption == JFileChooser.APPROVE_OPTION) {
	            selectedFiles = fileChooser.getSelectedFiles();

	        }
	        return selectedFiles;
	    }
	    private void addCodeWindowPane(File file) throws RecognitionException, IOException{
	            String filename = file.getName();
	            FirstWindow mainWindow = (FirstWindow) context;
	            Extracted_Info_Window codeWindow = new Extracted_Info_Window(file);
	            JComponent panel = codeWindow.createNewDataPanel();
	            mainWindow.Right_Pane.addTab(filename, null, panel, filename);
   
	    }
}

