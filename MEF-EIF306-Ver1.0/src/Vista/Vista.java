/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.Controlador;
import LogicaDeNegocio.AnimationMessage;
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
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author edva5
 */
public class Vista extends javax.swing.JFrame implements Observer{

    /**
     * @return the jScrollPane
     */
    public JScrollPane getjScrollPane() {
        return jScrollPane;
    }

    /**
     * @param jScrollPane the jScrollPane to set
     */
    public void setjScrollPane(JScrollPane jScrollPane) {
        this.jScrollPane = jScrollPane;
    }

    /**
     * @return the table
     */
    public JTable getTable() {
        return table;
    }

    /**
     * @return the automatas
     */
    public ArrayList<RoundButton> getAutomatas() {
        return automatas;
    }

    /**
     * @return the modelo
     */
    public Modelo getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    /**
     * @param controlador the controlador to set
     */
    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
        this.jCheckBox.addActionListener(controlador);
        this.addKeyListener(controlador);
        this.addMouseMotionListener(controlador);
        this.jComboBox.addActionListener(controlador);
        undoMnuItm.addActionListener(controlador);
        redoMnuItm.addActionListener(controlador);
        guardarMnuItm.addActionListener(controlador);
        recuperarMnuItm.addActionListener(controlador);
        limpiarMnuItm.addActionListener(controlador);
        inicialMnuItm.addActionListener(controlador);
        intermedioMnuItm.addActionListener(controlador);
        finalMnuItm.addActionListener(controlador);
        hileraMnuItm.addActionListener(controlador);
    }
    private Graphics2D g2;
    private JMenuItem guardarMnuItm,recuperarMnuItm,limpiarMnuItm,inicialMnuItm,intermedioMnuItm,finalMnuItm,hileraMnuItm,undoMnuItm,redoMnuItm;
    private JMenuBar menuBar;
    private JMenu archivoMnu,estadoMnu,verificarMnu,ediMnu;
    private ArrayList<RoundButton> automatas;
    private Controlador controlador;
    private JTable table;
    private DefaultTableModel tableModel;
    private Modelo modelo;
    private JComboBox jComboBox;
    private JScrollPane jScrollPane;
    private JCheckBox jCheckBox;
    
    /**Im
     * Creates new form Vista
     */
    public Vista() {
        this.setFocusable(true);
        table = new JTable();
        tableModel = (DefaultTableModel) table.getModel();
        jScrollPane = new JScrollPane(getTable());
        this.add(jScrollPane);
        jScrollPane.setBounds(500, 50, 300, 300);
        //table.setBounds(500, 50, 300, 300);
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
        if (x1==x2&&y1==y2) {
            this.getGraphics().drawRect(x2-15, y2+15, 30, 30);
            //this.getGraphics().drawOval(x1, y1, 10, 10);
        }
        else
        {            
            this.getGraphics().drawLine(x1, y1, x2-20, y2-20);
            this.getGraphics().drawOval(x2-20, y2-20, 10, 10);
        }
            
    }   
    public void addToTable(String From,String To,String Sintax){
        String[] aux=new String[3];
        aux[0]=From;
        aux[1]=To;
        aux[2]=Sintax;
        tableModel.addRow(aux);
    }
    private void InitiaizePrimaryButtons(){
        this.addKeyListener(controlador);
        this.setFocusable(true);
        menuBar = new JMenuBar();        
            ediMnu= new JMenu("Edit");
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
            undoMnuItm= new JMenuItem("Undo");
            ediMnu.add(undoMnuItm);
            redoMnuItm= new JMenuItem("Redo");
            ediMnu.add(redoMnuItm);
        menuBar.add(ediMnu);
             
    setJMenuBar(menuBar);
    String[] preloadedLangauges={"Libre","Decimal","Binario","Hexadecimal"};
    jComboBox = new JComboBox(preloadedLangauges);
    jComboBox.setVisible(true);
    jCheckBox= new JCheckBox();
        JLabel tableckBxLbl= new JLabel("Mostrar Tabla");
        tableckBxLbl.setBounds(700, -25, 100, 100);
    jCheckBox.setBounds(800, 0, 25, 50);
    jComboBox.setBounds(850, 50, 100, 25);
    this.add(jComboBox);
    this.add(tableckBxLbl);
    this.add(jCheckBox);
        jScrollPane.setVisible(false);
    }
    private void updateStateView(Object arg){
        if (getAutomatas().size()!=0) {
                if (!automatas.stream().anyMatch(x->x.getText().equals(((State)arg).getStateId()))&& ((State)arg).isVisible()) {
                    getAutomatas().add(new RoundButton(((State)arg).getStateId()));
                    getAutomatas().get(getAutomatas().size()-1).addKeyListener(controlador);
                    getAutomatas().get(getAutomatas().size()-1).setBounds(((State)arg).getPosition().getX()+100,((State)arg).getPosition().getY()+100, 48, 48);
                    this.add(getAutomatas().get(getAutomatas().size()-1));
                    getAutomatas().get(getAutomatas().size()-1).addMouseMotionListener(controlador);
                    getAutomatas().get(getAutomatas().size()-1).addMouseListener(controlador);
                    switch(((State)arg).getType()){
                        case 1:
                        getAutomatas().get(getAutomatas().size()-1).setBackground(Color.BLUE);                       
                         break;
                        case 2:
                            getAutomatas().get(getAutomatas().size()-1).setBackground(Color.GREEN);
                         break;
                        case 3:
                            getAutomatas().get(getAutomatas().size()-1).setBackground(Color.RED);
                         break;
                    }
                     this.add(getAutomatas().get(getAutomatas().size()-1));
                }
                else if(((State)arg).isVisible())
                {
                    RoundButton auxRB=(RoundButton) getAutomatas().stream().filter(x->x.getText().equals(((State)arg).getStateId())).collect(Collectors.toList()).get(0);//actualiza estado en vista
                    auxRB.setBounds(((State)arg).getPosition().getX(),((State)arg).getPosition().getY(), 50, 50);
                }
            }
        else if(((State)arg).isVisible()){
                getAutomatas().add(new RoundButton(((State)arg).getStateId()));                
                getAutomatas().get(0).setBounds(((State)arg).getPosition().getX(),((State)arg).getPosition().getY(), 50, 50);
                this.add(getAutomatas().get(0));
                getAutomatas().get(0).addMouseMotionListener(controlador);
                getAutomatas().get(0).addMouseListener(controlador);
                getAutomatas().get(0).addKeyListener(controlador);
                switch(((State)arg).getType()){
                    case 1:
                    getAutomatas().get(0).setBackground(Color.BLUE);
                     break;
                    case 2:
                        getAutomatas().get(0).setBackground(Color.GREEN);
                     break;
                    case 3:
                        getAutomatas().get(0).setBackground(Color.RED);
                     break;
                }
                this.add(getAutomatas().get(0));
               
                //automatas.get(0).setVisible(true);
            }
        if(!((State)arg).isVisible()){
            int temp = getAutomatas().size();
            this.remove(getAutomatas().get(temp-1));
            getAutomatas().remove(temp-1);
            this.revalidate();
            this.repaint();
        }
        
    }
    private void updateTransitionsView(Object arg){
        ((Transition)arg).getFrom().getPosition().getX();
            ((Transition)arg).getTo().getPosition().getX();
            RoundButton fromAux = this.getAutomatas().stream().filter(x->x.getText().equals(((Transition)arg).getFrom().getStateId())).collect(Collectors.toList()).get(0);
            RoundButton toAux = this.getAutomatas().stream().filter(x->x.getText().equals(((Transition)arg).getTo().getStateId())).collect(Collectors.toList()).get(0);            
            if (((Transition)arg).isFinalPosition()) {
               updateTable();
               updateLines();                
            }
            else{                
                repaint();              
                updateLines();
            }
    }
    public void updateTable(){
                tableModel.setRowCount(0);
               modelo.getTransitionsTemp().stream().forEach(x->addToTable(x.getFrom().getStateId(), x.getTo().getStateId(),x.getSyntax()));
    }
    public void updateLines(){
        modelo.getTransitionsTemp().stream().forEach(x->paintLine(x.getFrom().getPosition().getX()+50, x.getFrom().getPosition().getY()+80, x.getTo().getPosition().getX()+50, x.getTo().getPosition().getY()+80));
    }
    public void animateSintaxisCheck(AnimationMessage am){
        if (am.getPriority().equals("Pivot")) {
            for (int i = 0; i < automatas.size(); i++) {
                if (automatas.get(i).getBackground().equals(Color.ORANGE)) {
                    switch(modelo.getStates().stream().filter(x->x.getStateId().equals(am.getStateId())).collect(Collectors.toList()).get(0).getType())
                    {
                        case 1:
                        automatas.get(i).setBackground(Color.BLUE);
                        break;
                        case 2:
                            automatas.get(i).setBackground(Color.GREEN);
                        break;
                        case 3:
                            automatas.get(i).setBackground(Color.RED);
                        break;    
                    }
                }
            }
            automatas.stream().filter(x->x.getText().equals(am.getStateId())).collect(Collectors.toList()).get(0).setBackground(Color.ORANGE);
        }
        else{
            for (int i = 0; i < automatas.size(); i++) {
                if (automatas.get(i).getBackground().equals(Color.ORANGE)) {
                    switch(modelo.getStates().stream().filter(x->x.getStateId().equals(am.getStateId())).collect(Collectors.toList()).get(0).getType())
                    {
                        case 1:
                        automatas.get(i).setBackground(Color.BLUE);
                        break;
                        case 2:
                            automatas.get(i).setBackground(Color.GREEN);
                        break;
                        case 3:
                            automatas.get(i).setBackground(Color.RED);
                        break;    
                    }
                }
            }
            automatas.stream().filter(x->x.getText().equals(am.getStateId())).collect(Collectors.toList()).get(0).setBackground(Color.ORANGE);
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
        modelo=(Modelo) o;
        if (arg instanceof State){
            updateStateView(arg);
        }      
        else if (arg instanceof Transition) {
            updateTransitionsView(arg);
            updateLines();
            updateTable();
        }
        else if (arg instanceof String) {
            JOptionPane.showConfirmDialog(null, ((String)arg));            
        }
        else if (arg instanceof AnimationMessage) {
        //    animateSintaxisCheck(((AnimationMessage)arg));
        }
       
        this.revalidate();
            this.repaint();
                
    }
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
