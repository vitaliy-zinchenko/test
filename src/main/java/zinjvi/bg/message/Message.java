package zinjvi.bg.message;

import java.io.Serializable;

public abstract class Message implements Serializable {

    private Long clientId;

    private MessageAction action;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public MessageAction getAction() {
        return action;
    }

    public void setAction(MessageAction action) {
        this.action = action;
    }
}
