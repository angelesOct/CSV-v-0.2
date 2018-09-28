/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Octaviano
 */
public class ModelCsv {
  private String limpiar_cajas="";
  private String path="C:\\archivos\\base.csv";
  private String nombre="";
  private String email="";
  private String guardar;

    public String getLimpiar_cajas() {
        return limpiar_cajas;
    }

    public void setLimpiar_cajas(String limpiar_cajas) {
        this.limpiar_cajas = limpiar_cajas;
    }

    public String getGuardar() {
        return guardar;
    }

    public void setGuardar(String guardar) {
        this.guardar = guardar;
    }
  
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
