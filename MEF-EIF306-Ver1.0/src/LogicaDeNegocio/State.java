/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicaDeNegocio;

import java.util.ArrayList;

/**
 *
 * @author edva5
 */
public class State {//Esta Clase Representa los estados
    public static int INITIAL=1,INTERMEDIATE=2,FINAL=3;
    private String stateId;
    private int type;
    private ArrayList<Transition> sent;
    private ArrayList<Transition> received;
    private Position position;
    boolean visible;

    public State(String stateId,int _type) {
        visible= true;
        this.stateId = stateId;
        position = new Position(0, 0);
        sent = new ArrayList<Transition>();
        received= new ArrayList<Transition>();
        type=_type;
    }
    /**
     * @param stateId the stateId to set
     */
    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @param sent the sent to set
     */
    public void setSent(ArrayList<Transition> sent) {
        this.sent = sent;
    }

    /**
     * @param received the received to set
     */
    public void setReceived(ArrayList<Transition> received) {
        this.received = received;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * @return the sent
     */
    public ArrayList<Transition> getSent() {
        return sent;
    }

    /**
     * @return the received
     */
    public ArrayList<Transition> getReceived() {
        return received;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }
    
    /**
     * @return the stateId
     */
    public String getStateId() {
        return stateId;
    }

    /**
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    
}
