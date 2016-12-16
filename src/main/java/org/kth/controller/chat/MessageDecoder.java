package org.kth.controller.chat;

import java.io.Serializable;
import org.primefaces.push.Decoder;

/**
 * A Simple {@link org.primefaces.push.Decoder} that decode a String into a {@link Message} object.
 */
public class MessageDecoder implements Decoder<String,Message> , Serializable {

	//@Override
	public Message decode(String s) {
		String[] userAndMessage = s.split(":");
		if (userAndMessage.length >= 2) {
			return new Message().setUser(userAndMessage[0]).setText(userAndMessage[1]);
		}
		else {
			return new Message(s);
		}
	}
}