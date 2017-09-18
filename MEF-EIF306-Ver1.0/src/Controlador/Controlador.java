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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
        vista.setModelo(modelo);
/************************Prueba****************************************/   //Agrego Estados Para Prueba    
        /*modelo.createState("A", State.INITIAL);
       modelo.createState("B", State.INTERMEDIATE);
        modelo.createState("C", State.INTERMEDIATE);
        modelo.createState("D", State.INTERMEDIATE);
        modelo.createState("E", State.INTERMEDIATE);
        modelo.createState("F", State.INTERMEDIATE);
        modelo.createState("G", State.INTERMEDIATE);
        modelo.createState("H", State.INTERMEDIATE);
        modelo.createState("I", State.INTERMEDIATE);
        modelo.createState("J", State.FINAL);*/
/****************************************************************/                  
    }

    

    @Override
    public void actionPerformed(ActionEvent e)  {
        if (e.getSource() instanceof JComboBox) {
            switch((String)((JComboBox)e.getSource()).getSelectedItem()){
                case "Libre"://"Libre","Decimal","Binario","Hexadecimal"}
                    modelo.clearModelo();
                    vista.getAutomatas().forEach(x->x.setText(""));
                    vista.getAutomatas().forEach(x->x.setVisible(false));
                    vista.getAutomatas().clear();
                    vista.updateLines();
                    vista.updateTable();                    
                break;
                case "Decimal":
                    modelo.clearModelo();
                    vista.getAutomatas().forEach(x->x.setText(""));
                    vista.getAutomatas().forEach(x->x.setVisible(false));
                    vista.getAutomatas().clear();
                    modelo.createState("I", State.INITIAL);
                    modelo.createState("E", State.FINAL);
                    modelo.createState("D", State.INTERMEDIATE);
                    modelo.createState("F", State.FINAL);
                    modelo.addTransition("I");
                    modelo.addTransition("E","0123456789");
                    modelo.addTransition("E");
                    modelo.addTransition("E","0123456789");
                    modelo.addTransition("E");
                    modelo.addTransition("D",".");
                    modelo.addTransition("D");
                    modelo.addTransition("F","0123456789");
                    modelo.addTransition("F");
                    modelo.addTransition("F","0123456789");
                    modelo.changeStatePosition("I", 0, 0, true);
                    modelo.changeStatePosition("E", 100, 100, true);
                    modelo.changeStatePosition("D", 200, 200, true);
                    modelo.changeStatePosition("F", 300, 300, true);
                    vista.updateLines();
                    vista.updateTable();
                break;
                case "Binario":
                    modelo.clearModelo();
                    vista.getAutomatas().forEach(x->x.setText(""));
                    vista.getAutomatas().forEach(x->x.setVisible(false));
                    vista.getAutomatas().clear();
                    modelo.createState("I", State.INITIAL);
                    modelo.createState("E", State.FINAL);
                    modelo.createState("D", State.INTERMEDIATE);
                    modelo.createState("F", State.FINAL);
                    modelo.addTransition("I");
                    modelo.addTransition("E","01");
                    modelo.addTransition("E");
                    modelo.addTransition("E","01");
                    modelo.addTransition("E");
                    modelo.addTransition("D",".");
                    modelo.addTransition("D");
                    modelo.addTransition("F","01");
                    modelo.addTransition("F");
                    modelo.addTransition("F","01");
                    modelo.changeStatePosition("I", 0, 0, true);
                    modelo.changeStatePosition("E", 100, 100, true);
                    modelo.changeStatePosition("D", 200, 200, true);
                    modelo.changeStatePosition("F", 300, 300, true);
                    vista.updateLines();
                    vista.updateTable();
                break;
                case "Hexadecimal":
                    modelo.clearModelo();
                    vista.getAutomatas().forEach(x->x.setText(""));
                    vista.getAutomatas().forEach(x->x.setVisible(false));
                    vista.getAutomatas().clear();
                    modelo.createState("I", State.INITIAL);
                    modelo.createState("E", State.FINAL);
                    modelo.createState("D", State.INTERMEDIATE);
                    modelo.createState("F", State.FINAL);
                    modelo.addTransition("I");
                    modelo.addTransition("E","0123456789ABCDEF");
                    modelo.addTransition("E");
                    modelo.addTransition("E","0123456789ABCDEF");
                    modelo.addTransition("E");
                    modelo.addTransition("D",".");
                    modelo.addTransition("D");
                    modelo.addTransition("F","0123456789ABCDEF");
                    modelo.addTransition("F");
                    modelo.addTransition("F","0123456789ABCDEF");
                    modelo.changeStatePosition("I", 0, 0, true);
                    modelo.changeStatePosition("E", 100, 100, true);
                    modelo.changeStatePosition("D", 200, 200, true);
                    modelo.changeStatePosition("F", 300, 300, true);
                    vista.updateLines();
                    vista.updateTable();
                break;
            }
        }
        else if(e.getSource() instanceof JCheckBox){
            if (((JCheckBox)e.getSource()).isSelected())
                vista.getjScrollPane().setVisible(true);
            else
                vista.getjScrollPane().setVisible(false);
            
        }
        else
        {
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
                    modelo.tryHilera(vista.showImputDialog("Ingrese la Hilera a probar"));
                    break;
                case "Undo":
                    modelo.undo();
                    vista.updateTable();
                    break;
                case "Redo":
                    modelo.redo();
                    vista.updateTable();
                    break;
            }
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
        vista.updateLines();
        vista.updateTable();       
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
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
    if (e.getSource() instanceof Vista) {
            vista.updateLines();
            vista.updateTable();       
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // System.out.println("Controlador.Controlador.keyPressed()"+e.getModifiersEx());
    }
    @Override
    public void keyPressed(KeyEvent e) {
        
        if(e.getKeyCode()==37){
            modelo.undo();
            vista.updateLines();
            vista.updateTable();       
        }
        else if(e.getKeyCode()==39){
            modelo.redo();
            vista.updateLines();
            vista.updateTable();       
        }
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
