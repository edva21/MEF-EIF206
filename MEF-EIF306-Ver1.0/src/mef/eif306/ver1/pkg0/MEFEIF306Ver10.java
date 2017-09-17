/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mef.eif306.ver1.pkg0;

import Controlador.Controlador;
import Modelo.Modelo;
import Vista.Vista;
import javax.swing.JOptionPane;

/**
 *
 * @author edva5
 */
public class MEFEIF306Ver10 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Modelo modelo= new Modelo();
        Vista vista=new Vista();
        Controlador controlador = new Controlador(modelo, vista);
        
        
    }
    
}
