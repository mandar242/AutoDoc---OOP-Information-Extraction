/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oopie_mandar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
/**
 *
 * @author manda
 */
public class FirstWindow extends JFrame {
    public Parse_MSM Parse_File ;
    public JTabbedPane Right_Pane = null;
    public JScrollPane Left_Pane = null;
    private JMenu project_code;
    private JMenuItem add_code, code_info;
	
	public FirstWindow(Parse_MSM parent){
	Initialize_Window();
        setTitle("The Object-Oriented Program Information Extraction System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(constants.MAIN_WINDOW_WIDTH,constants.MAIN_WINDOW_HEIGHT);
        setLocation(constants.MAIN_WINDOW_LOCATION_X,constants.MAIN_WINDOW_LOCATION_Y);
        this.Parse_File = parent;
    }
	
	public void Initialize_Window() //initializes and creates the main window 
        {
        addMenuBar(this);
        Left_Pane = addJTree(this, createJTree(constants.P_JTREE_EMPTY),false);
        Right_Pane = addTabbedPane(this);
    }
	
	 public void toggleProjectCode(boolean enable){
	        code_info.setEnabled(enable);
	    }

	    private JScrollPane addJTree(JFrame parentFrame, JTree jTree, boolean addMouseListener) {
	        JScrollPane tempScrollPane = new JScrollPane(jTree);
	        tempScrollPane.setPreferredSize(new Dimension(constants.JTREE_WINDOW_WIDTH, constants.JTREE_WINDOW_HEIGHT));
	        tempScrollPane.setMaximumSize(new Dimension(constants.JTREE_WINDOW_WIDTH, constants.JTREE_WINDOW_HEIGHT));
	        parentFrame.add(tempScrollPane, BorderLayout.WEST);
	        if(addMouseListener){
	        }
	        return tempScrollPane;
	    }

	    private JTabbedPane addTabbedPane(JFrame parentFrame) {
	        JTabbedPane tabbedPane = new JTabbedPane();
	        parentFrame.add(tabbedPane, BorderLayout.CENTER);
	        return tabbedPane;
	    }

	    private void addMenuBar(JFrame context){

	        JMenuBar menubar = new JMenuBar();
	        JMenu file;
	        
	        context.setJMenuBar(menubar);

	        file = addMenuToMenuBar(menubar,constants.P_MAIN_WINDOW_MENU_FILE);
	  
	        project_code = addMenuToMenuBar(menubar,constants.P_MAIN_WINDOW_MENU_PROJECT_CODE);
	        project_code.setEnabled(false);

	        addMenuItemToMenu(file,constants.P_MENU_ITEM_FILE_NEW);
	        add_code = addMenuItemToMenu(project_code,constants.P_MENU_ITEM_PROJECT_CODE_ADD_CODE);
	        code_info = addMenuItemToMenu(project_code,constants.P_MENU_ITEM_METRICS_PROJECT_CODE_STATISTICS);
	        toggleProjectCode(false);
	    }
            
	    private JMenuItem addMenuItemToMenu(JMenu menu, String itemName){
	        JMenuItem menuItem = new JMenuItem(itemName);
	        menu.add(menuItem);
	        menuItem.addActionListener(FirstWindow_Listener.getInstance(this));
	
	        return  menuItem;
	    }

	    private JMenu addMenuToMenuBar(JMenuBar menuBar, String menuName){
	        JMenu menu = new JMenu(menuName);
	        menuBar.add(menu);
	        return  menu;
	    }

	    public void createNewProject(){
	        this.Right_Pane.removeAll();
	        project_code.setEnabled(true);
	        this.revalidate();
                this.updateTree(Parse_File.getInstance().getProjectData());
	    }
	    private JTree createJTree(String rootName){
	        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootName);
	       JTree tree =  new JTree(root);
	        tree.getSelectionModel().setSelectionMode
	                (TreeSelectionModel.SINGLE_TREE_SELECTION);
	            return tree;
	   }
           public void updateTree(Program_info projectData){
                JTree tree = null;
	        boolean addRightClickListener = false;
	        if(projectData!=null){
	            tree = createJTree(projectData.getProjectName());
	        }else{
	            tree = createJTree(constants.P_JTREE_EMPTY);
	        }


	        this.remove(Left_Pane);
	        Left_Pane = addJTree(this, tree,addRightClickListener);
	        this.revalidate();
           }

}
