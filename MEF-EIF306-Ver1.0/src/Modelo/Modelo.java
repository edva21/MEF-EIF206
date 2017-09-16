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
    private ArrayList<Transition> transitions;//Cambios de Estados
    private ArrayList<State> tempStates;//Pila de Estados
    private boolean selected;
    public Modelo() {
        states= new ArrayList<State>();        
        transitions=new ArrayList<Transition>();
        tempStates=new ArrayList<State>();
        selected=false;
    }
    /**
     * @return the transitions
     */
    public ArrayList<Transition> getTransitions() {
        return transitions;
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
            getTransitions().add(new Transition(syntax.charAt(i)+"", tempStates.get(0), tempStates.get(1)));
        }
        //
        selected=false;
        tempStates.clear();
        setChanged();
        notifyObservers(getTransitions().get(getTransitions().size()-1));
    }
    public void changeStatePosition(String s,int x,int y,boolean isFinal){
        State auxState=states.stream().filter(z->z.getStateId().equals(s)).collect(Collectors.toList()).get(0);
        auxState.setPosition(new Position(x,y));
        setChanged();
        notifyObservers(auxState);
        for (int i = 0; i < getTransitions().size(); i++) {
            if (getTransitions().get(i).getFrom()==auxState) {
                setChanged();
                getTransitions().get(i).setFinalPosition(isFinal);
                notifyObservers(getTransitions().get(i));
            }
            else if(getTransitions().get(i).getTo()==auxState){
                setChanged();
                getTransitions().get(i).setFinalPosition(isFinal);
                notifyObservers(getTransitions().get(i));
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
                    states.add(new State(stateId, type));
                    setChanged();
                    notifyObservers(states.get(states.size()-1));
                }
            }
        }
        else{
            states.add(new State(stateId, type));
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
}
