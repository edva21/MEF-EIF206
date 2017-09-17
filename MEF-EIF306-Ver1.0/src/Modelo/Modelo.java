/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;


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
        return states.stream().anyMatch(x->x.getStateId().equals(s));
    }
    public void addTransition(String s){        
         tempStates.add(states.stream().filter(x->x.getStateId().equals(s)).collect(Collectors.toList()).get(0));        
        selected=true;   
    }
    public void addTransition(String s,String syntax){
        addTransition(s);
        for (int i = 0; i < syntax.length(); i++) {
            getTransitionsTemp().add(new Transition(syntax.charAt(i)+"", tempStates.get(0), tempStates.get(1)));
        }
        while(historial.size()>actualH){
              historial.remove(historial.size()-1);
        }   
        historial.add("transitio");
        actualH = historial.size();
        actualT = transitionsTemp.size();
        transition= (ArrayList<Transition>) transitionsTemp.clone();
        selected=false;
        tempStates.clear();
        setChanged();
        notifyObservers(getTransitionsTemp().get(getTransitionsTemp().size()-1));
    }
    public void changeStatePosition(String s,int x,int y,boolean isFinal){
        State auxState=states.stream().filter(z->z.getStateId().equals(s)).collect(Collectors.toList()).get(0);
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
        if (states.size()>0) {
            if (!states.stream().anyMatch(x->x.getStateId().equals(stateId))) {
                if (type==State.INITIAL&&states.stream().anyMatch(x->x.getType()==State.INITIAL)) {
                    setChanged();
                    notifyObservers("Ya Exite un Estado Inicio");
                }               
                else{
                        while(states.size()>actualS){
                            states.remove(states.size()-1);
                        }   
                        
                        while(historial.size()>actualH){
                            historial.remove(historial.size()-1);
                        }   
                    states.add(new State(stateId, type));
                    historial.add("estado");
                     actualH=historial.size();
                     actualS=states.size();
                    setChanged();
                    notifyObservers(states.get(states.size()-1));
                }
            }
        }
        else{
            
            states.add(new State(stateId, type));
            historial.add("estado");
            actualH=historial.size();
            actualS=states.size();
            setChanged();
            notifyObservers(states.get(states.size()-1));
        }
    }
    public void tryHilera(String s){
        State stateAux = states.stream().filter(x->x.getType()==State.INITIAL).collect(Collectors.toList()).get(0);
        tryHilera(stateAux,s,0);//Envia primer estado y la Hilera a un metodo recursivo para revisar si la hilera cumple con las reglas establesidas
    }
    private void tryHilera(State s,String hilera,int index){//Metodo recursivo
        int transitionIndex=-1;//numero en lista de cambo de estados, por el mmomento sera -1(que no tome ningun valor posible dentro de la lista)
        String auxString;//String de prueba para tomar los tados a revisar en la hilera
        for (int i = 0; i < s.getSent().size(); i++) {//recorre todoss los cambios de estado posibles
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
                    actualH= historial.size();
                    actualS= states.size();
                    actualT=transitionsTemp.size();
                }
                
                if(actualS>0 && historial.get(actualH-1)=="estado" ){
                    System.out.println("Modelo.Modelo.undo()"+"circulo");
                    actualH--;
                    actualS--;
                    states.get(actualS).setVisible(false);
                    setChanged();
                    notifyObservers(states.get(actualS));
                }
                if(actualT>0 && historial.get(actualH-1)=="transitio"){
                    System.out.println("Modelo.Modelo.undo()"+"raya");
                    actualH--;
                    actualT--;
                    transitionsTemp.remove(actualT);
                    actualT--;
                    transitionsTemp.remove(actualT);
                    setChanged();
                    if(transitionsTemp.size()==0){notifyObservers(null);}
                    else{notifyObservers(getTransitionsTemp().get(getTransitionsTemp().size()-1));}
                }
            }
        }
    public void redo(){
         if(!historial.isEmpty()){
                if(actualH==-1){ 
                    actualH= historial.size();
                    actualS= states.size();
                }         
                if(historial.size()>actualH && historial.get(actualH)=="estado"){
                    System.out.println("Modelo.Modelo.redo()"+"Circulo");
                    states.get(actualS).setVisible(true);
                    setChanged();
                    notifyObservers(states.get(actualS));
                    actualH++;
                    actualS++;
                }
                
                if(historial.size()>actualH && historial.get(actualH)=="transitio"){
                    System.out.println("Modelo.Modelo.redo()" +"raya");
                    actualT++;
                    transitionsTemp.add(transition.get(actualT-1));
                    actualT++;
                    transitionsTemp.add(transition.get(actualT-1));
                    actualH++;
                    setChanged();
                    notifyObservers(getTransitionsTemp().get(getTransitionsTemp().size()-1));
                }
            }
    }
}
