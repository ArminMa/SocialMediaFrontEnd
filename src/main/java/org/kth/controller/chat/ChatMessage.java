package org.kth.controller.chat;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import org.kth.util.gsonX.GsonX;

@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatMessage {
	private String message;
	private String sender;
	private Date received;

	public ChatMessage() {
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public Date getReceived() {
		return received;
	}

	public void setReceived(Date received) {
		this.received = received;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ChatMessage that = (ChatMessage) o;

		if (message != null ? !message.equals(that.message) : that.message != null) return false;
		if (sender != null ? !sender.equals(that.sender) : that.sender != null) return false;
		return received != null ? received.equals(that.received) : that.received == null;
	}

	@Override
	public int hashCode() {
		int result = message != null ? message.hashCode() : 0;
		result = 31 * result + (sender != null ? sender.hashCode() : 0);
		result = 31 * result + (received != null ? received.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return GsonX.gson.toJson(this);
	}
	// getter, setter, toString omitted..
}