/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oopie_mandar;

import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 *
 * @author manda
 */
public class ProjectName_Window extends JFrame {

    Container container = getContentPane();
    public FirstWindow mainWindow;
    public JLabel mainLabel, projectName;
    public JTextField projectName_text, productName_text, creator_text;
    public JTextArea comment_area;
    public JButton ok, cancel;

    public ProjectName_Window(FirstWindow parentFrame){
        this.mainWindow = parentFrame;
        container.setLayout(null);
        initComponent();
        addActionEvent();
        setSize(constants.NEW_PROJECT_WINDOW_WIDTH,constants.NEW_PROJECT__WINDOW_HEIGHT);
        setLocation(constants.NEW_PROJECT__WINDOW_LOCATION_X,constants.NEW_PROJECT__WINDOW_LOCATION_Y);
        setTitle("Enter New Project Name");
    }

    public void initComponent(){

        mainLabel = new JLabel("CECS 547");
        mainLabel.setBounds(0,20,330,20);
        mainLabel.setHorizontalAlignment(JLabel.CENTER);
        container.add(mainLabel);

        projectName = new JLabel("Project Name:");
        projectName.setBounds(20, 60,100,20);
        container.add(projectName);
        
        projectName_text = new JTextField();
        projectName_text.setBounds(120, 60,150,20);
        container.add(projectName_text);

   
        productName_text = new JTextField();
        productName_text.setBounds(120, 90,150,20);
        
        creator_text = new JTextField();
        creator_text.setBounds(120, 120,150,20);
       
        comment_area = new JTextArea();
        comment_area.setBounds(20, 175,360,100);
        

        // add button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBounds(100,100,200,50);
        container.add(buttonPanel);
        ok=new JButton("Ok");
        cancel=new JButton("Cancel");
        buttonPanel.add(ok);
        buttonPanel.add(cancel);

        container.add(buttonPanel);

    }
    
    public void addActionEvent() {
        SecondWindow_Listener listener = new SecondWindow_Listener(this);
        ok.addActionListener(listener);
        cancel.addActionListener(listener);
    }
    
    public void disposeWindow(){
        setVisible(false);
        dispose();
    }
}
