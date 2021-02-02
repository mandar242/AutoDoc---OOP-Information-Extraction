/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oopie_mandar;
import javaplain.oopie_mandar.Utils;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.TreePath;
import javaplain.oopie_mandar.Oopie_MSM;
import org.antlr.runtime.RecognitionException;

/**
 *
 * @author manda
 */
public class Extracted_Info_Window {
    TreePath file;
    File selectedFile;

    public Extracted_Info_Window(TreePath file){
        this.file = file;
    }

    public Extracted_Info_Window(File file){
        this.selectedFile = file;
        this.selectedFile = file;
    }

    public JComponent createNewDataPanel () throws IOException, RecognitionException
    {
        
        JPanel panel = new JPanel(false);
        JPanel secondaryPanel = new JPanel(false);
        secondaryPanel.setLayout(new BoxLayout(secondaryPanel,BoxLayout.Y_AXIS));
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        Oopie_MSM pf = new Oopie_MSM();
        StringBuffer test = pf.getFinalResult(this.selectedFile.toString());
        JLabel fileMeta = new JLabel("<html>"+test+"</html>");
        fileMeta.setBorder(new EmptyBorder(0,10,0,0));
        secondaryPanel.add(fileMeta);
        fileMeta.setFont(new Font("Helvetica", Font.PLAIN, 25));
        JScrollPane scrollPane = new JScrollPane(secondaryPanel);
        panel.add(scrollPane);
        return panel;
    }
}