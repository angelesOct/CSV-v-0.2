/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JOptionPane;
import models.ModelCsv;
import views.ViewCsv;
    
/**
 *
 * @author Octaviano
 */
public class ControllerCsv {
    ModelCsv  modelCsv = new ModelCsv();
    ViewCsv viewCsv = new ViewCsv();

    ActionListener al = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()== viewCsv.jb_nuevo) jb_nuevo_action_performed();
            if(e.getSource()== viewCsv.jb_guardar) jb_guardar_action_performed();
        }
    };

    public ControllerCsv(ViewCsv viewCsv, ModelCsv modelCsv) {
        viewCsv.setVisible(true);
        this.modelCsv = modelCsv;
        this.viewCsv = viewCsv;
        this.viewCsv.jb_nuevo.addActionListener(al);
        this.viewCsv.jb_guardar.addActionListener(al);
    }
    
    public void jb_nuevo_action_performed(){
        this.Limpiar();
    }
    /**
    * En este metodo se llama al metodo de writeFile
    * y se le agrega su parametro para que pueda realizar su funcion de manera correcta
    */
    public void jb_guardar_action_performed(){
       this.writeFile(modelCsv.getPath());
    }
    /**
    * Escribe nuevas lineas en el archivo
    */
    public String writeFile (String path) {
        try{
            File  file = new File(path); //Ruta del arhivo que se abrira
            FileWriter fileWriter = new FileWriter(file,true);
            modelCsv.setNombre(viewCsv.jtf_nombre.getText());
            modelCsv.setEmail(viewCsv.jtf_email.getText());
            modelCsv.setGuardar(modelCsv.getNombre() + "," + modelCsv.getEmail());
            try(PrintWriter printWriter = new PrintWriter(fileWriter)){               
                printWriter.println(modelCsv.getGuardar());
                printWriter.close();  
                JOptionPane.showMessageDialog(null,"Guardado con Exito");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"File not found:"+e.getMessage());
        }
        }catch(IOException err){
            JOptionPane.showMessageDialog(null,"error on IO operation:"+err.getMessage());
        } 
       return null;   
    }//WriteFile   
    public String Limpiar(){
        viewCsv.jtf_nombre.setText(modelCsv.getLimpiar_cajas());
        viewCsv.jtf_email.setText(modelCsv.getLimpiar_cajas());
        return null;
    }
}
