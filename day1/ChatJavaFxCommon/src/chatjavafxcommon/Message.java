/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatjavafxcommon;

import java.io.Serializable;

/**
 *
 * @author salma
 */
public class Message implements Serializable{
    
    private String messageBody;
    private byte array[];

    public Message(String messageBody, byte[] array) {
        this.messageBody = messageBody;
        this.array = array;
    }

    public Message(String messageBody) {
        this.messageBody=messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public void setArray(byte[] array) {
        this.array = array;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public byte[] getArray() {
        return array;
    }
    
    
}
