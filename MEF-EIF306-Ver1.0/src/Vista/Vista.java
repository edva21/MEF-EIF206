/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.Controlador;
import LogicaDeNegocio.RoundButton;
import LogicaDeNegocio.State;
import LogicaDeNegocio.Transition;
import Modelo.Modelo;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author edva5
 */
public class Vista extends javax.swing.JFrame implements Observer{

    /**
     * @param controlador the controlador to set
     */
    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
        guardarMnuItm.addActionListener(controlador);
        recuperarMnuItm.addActionListener(controlador);
        limpiarMnuItm.addActionListener(controlador);
        inicialMnuItm.addActionListener(controlador);
        intermedioMnuItm.addActionListener(controlador);
        finalMnuItm.addActionListener(controlador);
        hileraMnuItm.addActionListener(controlador);
    }
    private Graphics2D g2;
    private JMenuItem guardarMnuItm,recuperarMnuItm,limpiarMnuItm,inicialMnuItm,intermedioMnuItm,finalMnuItm,hileraMnuItm;
    private JMenuBar menuBar;
    private JMenu archivoMnu,estadoMnu,verificarMnu;
    private ArrayList<RoundButton> automatas;
    private Controlador controlador;
    private JTable table;
    private DefaultTableModel tableModel;
    /**Im
     * Creates new form Vista
     */
    public Vista() {
        table = new JTable();
        tableModel = (DefaultTableModel) table.getModel();
        this.add(table);
        table.setBounds(500, 50, 300, 300);
        initComponents();
        InitiaizePrimaryButtons();
        automatas = new ArrayList<RoundButton>();
        String[] aux = new String[2];
        aux[0]=null;
        aux[1]=null;
        tableModel.addColumn("From");
        tableModel.addColumn("To");
        tableModel.addColumn("Sintaxis");
        tableModel.addRow(aux);
    }
    public String showImputDialog(String message){
        return JOptionPane.showInputDialog(null, message);
    }
    
    public void removeLine (int x1,int y1,int x2,int y2) {
    Graphics2D g2 = (Graphics2D) this.getGraphics();  
    //g2.clearRect(x1, y1, x2, y2);    
    g2.setBackground(this.getBackground());
    g2.clearRect(x1-100, y1-100, x2+100, y2+100);    
    }   
    public void paintLine (int x1,int y1,int x2,int y2) {
    /*Graphics2D g2 = (Graphics2D) this.getGraphics();      
    g2.draw(new Line2D.Double(x1, y1, x2, y2));    */
    this.getGraphics().drawLine(x1, y1, x2, y2);
    }   
    public void addToTable(String From,String To,String Sintax){
        String[] aux=new String[3];
        aux[0]=From;
        aux[1]=To;
        aux[2]=Sintax;
        tableModel.addRow(aux);
    }
    private void InitiaizePrimaryButtons(){
        menuBar = new JMenuBar();        
        
            archivoMnu= new JMenu("Archivo");
                guardarMnuItm=new JMenuItem("Guardar");
            archivoMnu.add(guardarMnuItm);
                recuperarMnuItm= new JMenuItem("Recuperar");
            archivoMnu.add(recuperarMnuItm);
                limpiarMnuItm= new JMenuItem("Limpiar");
            archivoMnu.add(limpiarMnuItm);
            
        menuBar.add(archivoMnu);//inicialMnuItm,intermedioMnuItm,finalMnuItm        
            estadoMnu=new JMenu("Estado");
                inicialMnuItm= new JMenuItem("Inicial");
            estadoMnu.add(inicialMnuItm);
                intermedioMnuItm= new JMenuItem("Intermedio");
            estadoMnu.add(intermedioMnuItm);
                finalMnuItm  = new JMenuItem("Final");
            estadoMnu.add(finalMnuItm);             
            
        menuBar.add(estadoMnu);
            verificarMnu=new JMenu("Verificar");
                hileraMnuItm= new JMenuItem("Hilera");
            verificarMnu.add(hileraMnuItm);
        menuBar.add(verificarMnu);
        
    setJMenuBar(menuBar);
    }
    private void updateStateView(Object arg){
        if (automatas.size()!=0) {
                if (!automatas.stream().anyMatch(x->x.getText().equals(((State)arg).getStateId()))) {
                    automatas.add(new RoundButton(((State)arg).getStateId()));
                    automatas.get(automatas.size()-1).setBounds(((State)arg).getPosition().getX()+100,((State)arg).getPosition().getY()+100, 48, 48);
                    this.add(automatas.get(automatas.size()-1));
                    automatas.get(automatas.size()-1).addMouseMotionListener(controlador);
                    automatas.get(automatas.size()-1).addMouseListener(controlador);
                    switch(((State)arg).getType()){
                        case 1:
                        automatas.get(automatas.size()-1).setBackground(Color.BLUE);                       
                         break;
                        case 2:
                            automatas.get(automatas.size()-1).setBackground(Color.GREEN);
                         break;
                        case 3:
                            automatas.get(automatas.size()-1).setBackground(Color.RED);
                         break;
                    }
                     this.add(automatas.get(automatas.size()-1));
                }
                else
                {
                    RoundButton auxRB=(RoundButton) automatas.stream().filter(x->x.getText().equals(((State)arg).getStateId())).collect(Collectors.toList()).get(0);//actualiza estado en vista
                    auxRB.setBounds(((State)arg).getPosition().getX(),((State)arg).getPosition().getY(), 50, 50);
                }
            }
            else{
                automatas.add(new RoundButton(((State)arg).getStateId()));                
                automatas.get(0).setBounds(((State)arg).getPosition().getX(),((State)arg).getPosition().getY(), 50, 50);
                this.add(automatas.get(0));
                automatas.get(0).addMouseMotionListener(controlador);
                automatas.get(0).addMouseListener(controlador);
                switch(((State)arg).getType()){
                    case 1:
                    automatas.get(0).setBackground(Color.BLUE);
                     break;
                    case 2:
                        automatas.get(0).setBackground(Color.GREEN);
                     break;
                    case 3:
                        automatas.get(0).setBackground(Color.RED);
                     break;
                }
                this.add(automatas.get(0));
                //automatas.get(0).setVisible(true);
            }
    }
    private void updateTransitionsView(Observable o,Object arg){
        ((Transition)arg).getFrom().getPosition().getX();
            ((Transition)arg).getTo().getPosition().getX();
            RoundButton fromAux = this.automatas.stream().filter(x->x.getText().equals(((Transition)arg).getFrom().getStateId())).collect(Collectors.toList()).get(0);
            RoundButton toAux = this.automatas.stream().filter(x->x.getText().equals(((Transition)arg).getTo().getStateId())).collect(Collectors.toList()).get(0);
            
            if (((Transition)arg).isFinalPosition()) {
//*******************Updates table*********************************************************************//                
               tableModel.setRowCount(0);
                ((Modelo)o).getTransitions().stream().forEach(x->addToTable(x.getFrom().getStateId(), x.getTo().getStateId(),x.getSyntax()));
//****************************************************************************************//                                
                ((Modelo)o).getTransitions().stream().forEach(x->paintLine(x.getFrom().getPosition().getX()+50, x.getFrom().getPosition().getY()+80, x.getTo().getPosition().getX(), x.getTo().getPosition().getY()+80));
            }
            else{
                
                repaint();
              //  paintLine(((Transition)arg).getFrom().getPosition().getX()+25, ((Transition)arg).getFrom().getPosition().getY()+80, ((Transition)arg).getTo().getPosition().getX()+25, ((Transition)arg).getTo().getPosition().getY()+80);
                ((Modelo)o).getTransitions().stream().forEach(x->paintLine(x.getFrom().getPosition().getX()+50, x.getFrom().getPosition().getY()+80, x.getTo().getPosition().getX(), x.getTo().getPosition().getY()+80));
            }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof State){
            updateStateView(arg);
        }      
        else if (arg instanceof Transition) {
            updateTransitionsView(o, arg);
        }
        else if (arg instanceof String) {
            JOptionPane.showConfirmDialog(null, ((String)arg));            
        }
                
    }

  

 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
