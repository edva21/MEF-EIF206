/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import LogicaDeNegocio.RoundButton;
import LogicaDeNegocio.State;
import Modelo.Modelo;
import Vista.Vista;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author edva5
 */
public class Controlador implements ActionListener,MouseListener,MouseMotionListener,KeyListener {
    private Modelo modelo;
    private Vista vista;

    public Controlador(Modelo modelo, Vista vista) {
        this.modelo = modelo;
        this.vista = vista;
        vista.setControlador(this);
        modelo.addObserver(vista);        
        vista.setVisible(true);
/************************Prueba****************************************/   //Agrego Estados Para Prueba    
        modelo.createState("A", State.INITIAL);
       modelo.createState("B", State.INTERMEDIATE);
        modelo.createState("C", State.INTERMEDIATE);
        /*modelo.createState("D", State.INTERMEDIATE);
        modelo.createState("E", State.INTERMEDIATE);
        modelo.createState("F", State.INTERMEDIATE);
        modelo.createState("G", State.INTERMEDIATE);
        modelo.createState("H", State.INTERMEDIATE);
        modelo.createState("I", State.INTERMEDIATE);*/
        modelo.createState("J", State.FINAL);
/****************************************************************/                  
    }

    

    @Override
    public void actionPerformed(ActionEvent e)  {
        switch(e.getActionCommand()){
            case "Guardar":
                break;
            case "Recuperar":
                break;
            case "Limpiar":
                break;
            case "Inicial":
                modelo.createState(vista.showImputDialog("Ingrese ID del Estado"+" Inicial"),State.INITIAL);
                break;
            case "Intermedio":
                modelo.createState(vista.showImputDialog("Ingrese ID del Estado"+" Intermedio"),State.INTERMEDIATE);
                break;
            case "Final":
                modelo.createState(vista.showImputDialog("Ingrese ID del Estado"+" Final"),State.FINAL);
                break;
            case "Hilera":
                break;
            case "Undo":
                modelo.undo();
                break;
            case "Redo":
                modelo.redo();
                break;
        }
    }    

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof RoundButton )    //RoundButton son los Objetos que representan los estados 
        {            
            if (!modelo.isSelected())
                modelo.addTransition(((RoundButton)e.getSource()).getText());   //Guarda estado en una pila, si la pila.size() ==2,  
            
            else                                                                //toma el primer estado guardado como Origen del cambio de Estado y el segundo como destino
                modelo.addTransition(((RoundButton)e.getSource()).getText(),vista.showImputDialog("Escriba Sintaxis"));                //Una vez ingresados los puntos se pide cual va a ser la sintaxis
        }          
          
    }

    @Override
    public void mousePressed(MouseEvent e) {
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() instanceof RoundButton) {
           
            modelo.changeStatePosition(((RoundButton)e.getSource()).getText(), e.getXOnScreen()-27,e.getYOnScreen()-80,true);
            //((RoundButton)e.getSource()).setBounds(e.getXOnScreen()-25, e.getYOnScreen()-80, 50, 50);            
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
  //      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
   //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() instanceof RoundButton) {
           
            modelo.changeStatePosition(((RoundButton)e.getSource()).getText(), e.getXOnScreen()-27,e.getYOnScreen()-80,false);
            //((RoundButton)e.getSource()).setBounds(e.getXOnScreen()-25, e.getYOnScreen()-80, 50, 50);            
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // System.out.println("Controlador.Controlador.keyPressed()"+e.getModifiersEx());
    }
    @Override
    public void keyPressed(KeyEvent e) {
        
        if(e.getKeyCode()==37){modelo.undo();}
        if(e.getKeyCode()==39){modelo.redo();}
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        
    }
}//JFileChooser ------- Para guardar o abrir un arhivo exterior

///recibir cualquier gramatica
//Eliminar antes
//mETER HILERASY HACER ANIMACION
//ACERLO  EN TRES DE
//Oficina 11 Jueves..........
