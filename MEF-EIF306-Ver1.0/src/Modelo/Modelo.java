/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;


import LogicaDeNegocio.AnimationMessage;
import LogicaDeNegocio.Position;
import LogicaDeNegocio.State;
import LogicaDeNegocio.Transition;
import java.util.ArrayList;
//import java.util.HashMap;
import java.util.Observable;
import java.util.Stack;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

/**
 *
 * @author edva5
 */
public class Modelo extends Observable {

    /**
     * @return the states
     */
    public ArrayList<State> getStates() {
        return states;
    }

    /**
     * @param states the states to set
     */
    public void setStates(ArrayList<State> states) {
        this.states = states;
    }

    /**
     * @param transitionsTemp the transitionsTemp to set
     */
    public void setTransitionsTemp(ArrayList<Transition> transitionsTemp) {
        this.transitionsTemp = transitionsTemp;
    }

    /**
     * @return the tempStates
     */
    public ArrayList<State> getTempStates() {
        return tempStates;
    }

    /**
     * @param tempStates the tempStates to set
     */
    public void setTempStates(ArrayList<State> tempStates) {
        this.tempStates = tempStates;
    }

    /**
     * @return the transition
     */
    public ArrayList<Transition> getTransition() {
        return transition;
    }

    /**
     * @param transition the transition to set
     */
    public void setTransition(ArrayList<Transition> transition) {
        this.transition = transition;
    }

    /**
     * @return the historial
     */
    public ArrayList<String> getHistorial() {
        return historial;
    }

    /**
     * @param historial the historial to set
     */
    public void setHistorial(ArrayList<String> historial) {
        this.historial = historial;
    }
    private ArrayList<State> states;//Estado
    private ArrayList<Transition> transitionsTemp;//Cambios de Estados
    private ArrayList<State> tempStates;//Pila de Estados
    private ArrayList<Transition> transition;
    private ArrayList<String> historial;//historial de estamdos y transiciones 
    private boolean selected;
    private int actualH;
    private int actualS;
    private int actualT;
    public Modelo() {
        actualH= -1;
        actualS= -1;
        actualT= -1;
        states= new ArrayList<State>();        
        transitionsTemp=new ArrayList<Transition>();
        transition = new ArrayList<Transition>();
        tempStates=new ArrayList<State>();
        historial= new ArrayList<String>();
        selected=false;
    }
    /**
     * @return the transitionsTemp
     */
    public void clearModelo(){
        getTransitionsTemp().clear();
        getHistorial().clear();
        getStates().clear();
        getTempStates().clear();
        getTransition().clear();
    }
    public ArrayList<Transition> getTransitionsTemp() {
        return transitionsTemp;
    }

    /**
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }
    
    private boolean stateExists(String s){
        return getStates().stream().anyMatch(x->x.getStateId().equals(s));
    }
    public void addTransition(String s){        
         getTempStates().add(getStates().stream().filter(x->x.getStateId().equals(s)).collect(Collectors.toList()).get(0));        
        selected=true;   
    }
    public void addTransition(String s,String syntax){
        addTransition(s);
        for (int i = 0; i < syntax.length(); i++) {
           
           getTransitionsTemp().add(new Transition(syntax.charAt(i)+"", getTempStates().get(0), getTempStates().get(1)));
           getHistorial().add("transitio");
        }
        while(getHistorial().size()>actualH){
              getHistorial().remove(getHistorial().size()-1);
        }   
        getHistorial().add("transitio");
        actualH = getHistorial().size();
        actualT = getTransitionsTemp().size();
        setTransition((ArrayList<Transition>) getTransitionsTemp().clone());
        selected=false;
        getTempStates().clear();
        setChanged();
        notifyObservers(getTransitionsTemp().get(getTransitionsTemp().size()-1));
    }
    public void changeStatePosition(String s,int x,int y,boolean isFinal){
        State auxState=getStates().stream().filter(z->z.getStateId().equals(s)).collect(Collectors.toList()).get(0);
        auxState.setPosition(new Position(x,y));
        setChanged();
        notifyObservers(auxState);
        for (int i = 0; i < getTransitionsTemp().size(); i++) {
            if (getTransitionsTemp().get(i).getFrom()==auxState) {
                setChanged();
                getTransitionsTemp().get(i).setFinalPosition(isFinal);
                notifyObservers(getTransitionsTemp().get(i));
            }
            else if(getTransitionsTemp().get(i).getTo()==auxState){
                setChanged();
                getTransitionsTemp().get(i).setFinalPosition(isFinal);
                notifyObservers(getTransitionsTemp().get(i));
            }   
        }
    }
    
    public void createState(String stateId,int type){
        if (getStates().size()>0) {
            if (!states.stream().anyMatch(x->x.getStateId().equals(stateId))) {
                if (type==State.INITIAL&&getStates().stream().anyMatch(x->x.getType()==State.INITIAL)) {
                    setChanged();
                    notifyObservers("Ya Exite un Estado Inicio");
                }               
                else{
                        while(getStates().size()>actualS){
                            getStates().remove(getStates().size()-1);
                        }   
                        
                        while(getHistorial().size()>actualH){
                            getHistorial().remove(getHistorial().size()-1);
                        }   
                    getStates().add(new State(stateId, type));
                    getHistorial().add("estado");
                     actualH=getHistorial().size();
                     actualS=getStates().size();
                    setChanged();
                    notifyObservers(getStates().get(getStates().size()-1));
                }
            }
        }
        else{
            
            getStates().add(new State(stateId, type));
            getHistorial().add("estado");
            actualH=getHistorial().size();
            actualS=getStates().size();
            setChanged();
            notifyObservers(getStates().get(getStates().size()-1));
        }
    }
    public void tryHilera(String s){
        State stateAux = getStates().stream().filter(x->x.getType()==State.INITIAL).collect(Collectors.toList()).get(0);
        tryHilera(stateAux,s,0);//Envia primer estado y la Hilera a un metodo recursivo para revisar si la hilera cumple con las reglas establesidas
    }
    private void tryHilera(State s,String hilera,int index){//Metodo recursivo
        int transitionIndex=-1;//numero en lista de cambo de estados, por el mmomento sera -1(que no tome ningun valor posible dentro de la lista)
        String auxString;//String de prueba para tomar los tados a revisar en la hilera
        /*setChanged();
        notifyObservers(new AnimationMessage(s.getStateId(), "Pivot"));*/
        for (int i = 0; i < s.getSent().size(); i++) {//recorre todoss los cambios de estado posibles
            /*setChanged();
            notifyObservers(new AnimationMessage(s.getSent().get(i).getTo().getStateId(), "NotPivot"));*/
            if (hilera.length()-index>=s.getSent().get(i).getSyntax().length()) {//se cumple que la hilera tiene almenos la misma cantidad de letras de la que requiere la sintaxis, entonces continue
                auxString=hilera.substring(index, index+s.getSent().get(i).getSyntax().length());//Se ingresa los datos a revisar en la hilera en un Sring auxiliar auxString
                if (auxString.equals(s.getSent().get(i).getSyntax())) {//Compara los Datos,
                    transitionIndex=i;//transitionIndex guarda el espacio si es posible el salto de estado
                    i=s.getSent().size();//se sale de el bucle
                }            
            }
            
        }        
        if (transitionIndex==-1) {//si no hay mas saltos de estado en el estado presente
            if (s.getType()==State.FINAL) {                          //comprueba que sea el final
                if (index<hilera.length())//si es el estado final y quedan mas letras por revisar entonces no se cumple la hilera
                    hileraIncorrecta();
                else
                    hileraCorrecta();//Hilera Correcta                
            }
            else
                hileraIncorrecta();//Hilera Incorrecta
        }
        else
            tryHilera(s.getSent().get(transitionIndex).getTo(), hilera, s.getSent().get(transitionIndex).getSyntax().length()+index);//Se llama de nuevo con el estado Siguiente
    }
    private void hileraCorrecta(){
        setChanged();
        notifyObservers("Hilera Correcta");
    }
    private void hileraIncorrecta(){
        setChanged();
        notifyObservers("Hilera Inorrecta");
    }
    public void undo(){
            if(!historial.isEmpty()){
                if(actualH==-1){ 
                    actualH= getHistorial().size();
                    actualS= getStates().size();
                    actualT=getTransitionsTemp().size();
                }
                
                if(actualS>0 && getHistorial().get(actualH-1)=="estado" ){
                    System.out.println("Modelo.Modelo.undo()"+"circulo");
                    actualH--;
                    actualS--;
                    getStates().get(actualS).setVisible(false);
                    setChanged();
                    notifyObservers(getStates().get(actualS));
                }
                if(actualT>0 && getHistorial().get(actualH-1)=="transitio"){
                    System.out.println("Modelo.Modelo.undo()"+"raya");
                    actualH--;
                    actualT--;
                    getTransitionsTemp().remove(actualT);
                    /*actualT--;
                    getTransitionsTemp().remove(actualT);*/
                    setChanged();
                    if(getTransitionsTemp().size()==0){notifyObservers(null);}
                    else{notifyObservers(getTransitionsTemp().get(getTransitionsTemp().size()-1));}
                }
            }
        }
    public void redo(){
         if(!historial.isEmpty()){
                if(actualH==-1){ 
                    actualH= getHistorial().size();
                    actualS= getStates().size();
                }         
                if(getHistorial().size()>actualH && getHistorial().get(actualH)=="estado"){
                    System.out.println("Modelo.Modelo.redo()"+"Circulo");
                    getStates().get(actualS).setVisible(true);
                    setChanged();
                    notifyObservers(getStates().get(actualS));
                    actualH++;
                    actualS++;
                }
                
                if(getHistorial().size()>actualH && getHistorial().get(actualH)=="transitio"){
                    System.out.println("Modelo.Modelo.redo()" +"raya");
                    actualT++;
                    getTransitionsTemp().add(getTransition().get(actualT-1));
                    actualT++;
                    getTransitionsTemp().add(getTransition().get(actualT-1));
                    actualH++;
                    setChanged();
                    notifyObservers(getTransitionsTemp().get(getTransitionsTemp().size()-1));
                }
            }
    }
}
