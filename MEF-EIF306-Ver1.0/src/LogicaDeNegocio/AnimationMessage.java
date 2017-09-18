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
public class AnimationMessage {
    private String StateId;
    private String Priority;

    public AnimationMessage(String StateId, String Priority) {
        this.StateId = StateId;
        this.Priority = Priority;
    }
    
    /**
     * @return the State
     */
    public String getStateId() {
        return StateId;
    }

    /**
     * @return the Priority
     */
    public String getPriority() {
        return Priority;
    }
    
}
