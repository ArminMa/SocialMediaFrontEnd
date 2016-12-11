package org.kth.controller.chat;

import java.util.Date;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import org.kth.util.gsonX.GsonX;

public class ChatMessageDecoder implements Decoder.Text<ChatMessage> {
	@Override
	public void init(final EndpointConfig config) {
	}

	@Override
	public void destroy() {
	}

	@Override
	public ChatMessage decode(final String textMessage) throws DecodeException {

		ChatMessage chatMessage  = GsonX.gson.fromJson(textMessage, ChatMessage.class);
//		JsonObject obj = Json.createReader(new StringReader(textMessage))
//				.readObject();
//		chatMessage.setMessage(obj.getString("message"));
//		chatMessage.setSender(obj.getString("sender"));
		chatMessage.setReceived(new Date());
		return chatMessage;
	}

	@Override
	public boolean willDecode(final String s) {
		return true;
	}
}