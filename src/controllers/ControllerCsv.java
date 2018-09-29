/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
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
    /**
     * Va verificando que boton es el que se ha seleccionado y llama a su funcion que le corresponde
     */
    ActionListener al = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()== viewCsv.jb_nuevo) jb_nuevo_action_performed(); //boton NUEVO
            if(e.getSource()== viewCsv.jb_guardar) jb_guardar_action_performed(); //boton GUARDAR
            if(e.getSource()== viewCsv.jb_primero) jb_primero_action_performed(); //boton PRIMERO
            if(e.getSource()== viewCsv.jb_siguiente) jb_siguiente_action_performed(); //boton SIGUIENTE
            if(e.getSource()== viewCsv.jb_anterior) jb_anterior_action_performed(); //boton ANTERIOR
            if(e.getSource()== viewCsv.jb_ultimo) jb_ultimo_action_performed(); //boton ULTIMO
        }
    };
    /**
     * integra los componentes del modelo MVC
     * @param viewCsv
     * @param modelCsv 
     */
    public ControllerCsv(ViewCsv viewCsv, ModelCsv modelCsv) {
        viewCsv.setVisible(true);
        this.modelCsv = modelCsv;
        this.viewCsv = viewCsv;
        this.viewCsv.jb_nuevo.addActionListener(al);
        this.viewCsv.jb_guardar.addActionListener(al);
        this.viewCsv.jb_primero.addActionListener(al);
        this.viewCsv.jb_siguiente.addActionListener(al);
        this.viewCsv.jb_anterior.addActionListener(al);
        this.viewCsv.jb_ultimo.addActionListener(al);
        this.primero(modelCsv.getPath()); //mostrara el primer registro al iniiar la aplicacion 
    }    
    /**
     *-----Metodos para cada boton, y este tenga su respectiva funcion-----
     */
    
    /**
    * En este metodo se llama al metodo de limpiar
    */  
    public void jb_nuevo_action_performed(){
        this.Limpiar();
    }
    /**
    * En este metodo se llama al metodo de writeFile
    * y se le agrega su parametro para que pueda realizar su funcion de manera correcta
    */
    public void jb_guardar_action_performed(){
       this.writeFile(modelCsv.getPath());
       this.ultimo(modelCsv.getPath()); //llama al ultimo registro guardado
    }
    /**
    * En este metodo se llama al metodo de primero
    * y se le agrega su parametro para que pueda realizar su funcion de manera correcta
    */ 
    public void jb_primero_action_performed(){
        this.primero(modelCsv.getPath());
    }
    /**
    * En este metodo se llama al metodo de siguiente
    * y se le agrega su parametro para que pueda realizar su funcion de manera correcta
    */ 
    public void jb_siguiente_action_performed(){
        this.siguiente(modelCsv.getPath());
    }
    /**
    * En este metodo se llama al metodo de anterior
    * y se le agrega su parametro para que pueda realizar su funcion de manera correcta
    */ 
    public void jb_anterior_action_performed(){
        this.anterior(modelCsv.getPath());
    }
    /**
    * En este metodo se llama al metodo de ultimo
    * y se le agrega su parametro para que pueda realizar su funcion de manera correcta
    */ 
    public void jb_ultimo_action_performed(){
        this.ultimo(modelCsv.getPath());
    }
    /**
    * Escribe nuevas lineas en el archivo
    */
    public String writeFile (String path) {
        try{
            File  file = new File(path); //Ruta del arhivo que se abrira
            FileWriter fileWriter = new FileWriter(file,true);
            modelCsv.setNombre(viewCsv.jtf_nombre.getText()); // se agrega el contenido de la caja de texto a las variables
            modelCsv.setEmail(viewCsv.jtf_email.getText());
            modelCsv.setGuardar(modelCsv.getNombre() + ";" + modelCsv.getEmail());
            try(PrintWriter printWriter = new PrintWriter(fileWriter)){               
                printWriter.println(modelCsv.getGuardar()); //se guarda cada campo agregado
                printWriter.close();  
                JOptionPane.showMessageDialog(null,"Guardado con Exito"); //notificacion de que los datos fueron guardados
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"File not found:"+e.getMessage());
        }
        }catch(IOException err){
            JOptionPane.showMessageDialog(null,"error on IO operation:"+err.getMessage());
        } 
       return null;   
    }//WriteFile  
    /**
     * este metodo va a limpiar cada caja de texto, para poder ingresar nuevos datos
     * @return 
     */
    public String Limpiar(){
        viewCsv.jtf_nombre.setText(modelCsv.getLimpiar_cajas());
        viewCsv.jtf_email.setText(modelCsv.getLimpiar_cajas());
        return null;
    }   
//----------Metodos para los botones de movimientos agregados a la version 2 de la aplicacion--------
    /**
     * va ha mostrar en cada caja de texto el primer dato dentro del archivo
     * @param path
     * @return 
     */
    public String primero(String path){
        try{  
            String acumula_lineas =""; //acomulara cada linea para 
            String row; // indica una fila
            try (FileReader archivo = new FileReader(path)){              
                BufferedReader bufferedReader = new BufferedReader(archivo);
                    while ((row=bufferedReader.readLine())!= null){
                       acumula_lineas+=row+"¬"; // si el archivo tiene mas filas con esta linea ira mostrando cada una de ellas
                    }
                    String datos[] =  acumula_lineas.split("¬");//la sepacion de cada linea sera el simbolo (¬)
                    modelCsv.setPosicion(0);
                    String registro[] = datos[modelCsv.getPosicion()].split(";");
                    viewCsv.jtf_nombre.setText(registro[0]);
                    viewCsv.jtf_email.setText(registro[1]); // posicion de los registros que seran mostrados              
            } catch(FileNotFoundException err){
                JOptionPane.showMessageDialog(null,"File not found"+err.getMessage());
            }        
        } catch(IOException err){
            JOptionPane.showMessageDialog(null,"error on IO operation:"+err.getMessage());
        } 
        return null;    
    }
     /**
     * va ha mostrar en cada caja de texto el siguiente dato dentro del archivo
     * @param path
     * @return 
     */
    public String siguiente(String path){
        try{  
            String acumula_lineas =""; //acomulara cada linea para 
            String row; // indica una fila
            try (FileReader archivo = new FileReader(path)){              
                BufferedReader bufferedReader = new BufferedReader(archivo);
                int contador =0; //contador de cada fila
                    while ((row=bufferedReader.readLine())!= null){
                       contador = contador + 1;
                       acumula_lineas+=row+"¬"; // si el archivo tiene mas filas con esta linea ira mostrando cada una de ellas
                    }
                    String datos[] =  acumula_lineas.split("¬");//la sepacion de cada linea sera el simbolo (¬)  
                    modelCsv.setPosicion(modelCsv.getPosicion()+1); //va al siguiente valor
                    if (modelCsv.getPosicion() < contador){
                        String registro[] = datos[modelCsv.getPosicion()].split(";"); // ira a los siguientes datos antes del ultimo
                        viewCsv.jtf_nombre.setText(registro[0]);
                        viewCsv.jtf_email.setText(registro[1]); // posicion de los registros que seran mostrados 
                    }  
                    else{
                        modelCsv.setPosicion(modelCsv.getPosicion()-1); 
                        JOptionPane.showMessageDialog(null,"ha llegado al ultimo dato");
                    }
            } catch(FileNotFoundException err){
                JOptionPane.showMessageDialog(null,"File not found"+err.getMessage());
            }        
        } catch(IOException err){
            JOptionPane.showMessageDialog(null,"error on IO operation:"+err.getMessage());
        } 
        return null;    
    }
    /**
     * va ha mostrar en cada caja de texto el anterior dato dentro del archivo
     * @param path
     * @return 
     */
    public String anterior(String path){
        try{  
            String acumula_lineas =""; //acomulara cada linea para 
            String row; // indica una fila
            try (FileReader archivo = new FileReader(path)){              
                BufferedReader bufferedReader = new BufferedReader(archivo);
                int contador =0; //contador de cada fila
                    while ((row=bufferedReader.readLine())!= null){
                       contador = contador + 1;
                       acumula_lineas+=row+"¬"; // si el archivo tiene mas filas con esta linea ira mostrando cada una de ellas
                    }
                    String datos[] =  acumula_lineas.split("¬");//la sepacion de cada linea sera el simbolo (¬)  
                    modelCsv.setPosicion(modelCsv.getPosicion()-1); //va al siguiente valor
                    if (modelCsv.getPosicion()>=0){
                        String registro[] = datos[modelCsv.getPosicion()].split(";"); // ira a los siguientes datos antes del ultimo
                        viewCsv.jtf_nombre.setText(registro[0]);
                        viewCsv.jtf_email.setText(registro[1]); // posicion de los registros que seran mostrados 
                    }  
                    else{
                        modelCsv.setPosicion(modelCsv.getPosicion()+1); 
                        JOptionPane.showMessageDialog(null,"ha llegado al primer dato");
                    }
            } catch(FileNotFoundException err){
                JOptionPane.showMessageDialog(null,"File not found"+err.getMessage());
            }        
        } catch(IOException err){
            JOptionPane.showMessageDialog(null,"error on IO operation:"+err.getMessage());
        }        
        return null;    
    }
    /**
     * va ha mostrar en cada caja de texto el ultimo dato dentro del archivo
     * @param path
     * @return 
     */
    public String ultimo(String path){
        try{  
            String acumula_lineas =""; //acomulara cada linea para 
            String row; // indica una fila
            try (FileReader archivo = new FileReader(path)){              
                BufferedReader bufferedReader = new BufferedReader(archivo);
                int contador=0;
                    while ((row=bufferedReader.readLine())!= null){
                        contador = contador + 1;
                       acumula_lineas+=row+"¬"; // si el archivo tiene mas filas con esta linea ira mostrando cada una de ellas
                    }
                    String datos[] =  acumula_lineas.split("¬");//la sepacion de cada linea sera el simbolo (¬)
                    modelCsv.setPosicion(contador-1);
                    String registro[] = datos[modelCsv.getPosicion()].split(";");
                    viewCsv.jtf_nombre.setText(registro[0]);
                    viewCsv.jtf_email.setText(registro[1]); // posicion de los registros que seran mostrados              
            } catch(FileNotFoundException err){
                JOptionPane.showMessageDialog(null,"File not found"+err.getMessage());
            }        
        } catch(IOException err){
            JOptionPane.showMessageDialog(null,"error on IO operation:"+err.getMessage());
        } 
        return null;    
    }
}
