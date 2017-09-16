/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogicaDeNegocio;

/**
 *
 * @author edva5
 */
public class Transition {//Esta clase reune los datos necesarios para denotar el cambio de un estado

    /**
     * @return the finalPosition
     */
    public boolean isFinalPosition() {
        return finalPosition;
    }

    /**
     * @param finalPosition the finalPosition to set
     */
    public void setFinalPosition(boolean finalPosition) {
        this.finalPosition = finalPosition;
    }
    private String syntax;
    private State From;
    private State To;
    private boolean finalPosition;

    public Transition(String syntax, State From, State To) {
        this.syntax = syntax;
        this.From = From;
        this.To = To;
        this.From.getSent().add(this);
        this.To.getReceived().add(this);
    }
    /**
     * @return the syntax
     */
    public String getSyntax() {
        return syntax;
    }

    /**
     * @param syntax the syntax to set
     */
    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }

    /**
     * @return the From
     */
    public State getFrom() {
        return From;
    }

    /**
     * @param From the From to set
     */
    public void setFrom(State From) {
        this.From = From;
    }

    /**
     * @return the To
     */
    public State getTo() {
        return To;
    }

    /**
     * @param To the To to set
     */
    public void setTo(State To) {
        this.To = To;
    }
    
    
    
}
