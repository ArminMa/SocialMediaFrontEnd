package org.kth.controller.handlers;


import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat")
public class MyWebSocketEndPoint {
	private static final String USERNAME_KEY = "username";
	private static Map<String, Session> chatRooms = Collections.synchronizedMap(new LinkedHashMap());

	public MyWebSocketEndPoint() {
	}

	@OnOpen
	public void onOpen(Session session) throws Exception {
		Map parameter = session.getRequestParameterMap();
		List list = (List)parameter.get("username");
		String newUsername = (String)list.get(0);
		chatRooms.put(newUsername, session);
		session.getUserProperties().put("username", newUsername);
		String response = "newUser|" + String.join("|", chatRooms.keySet());
		session.getBasicRemote().sendText(response);
		Iterator var6 = chatRooms.values().iterator();

		while(var6.hasNext()) {
			Session client = (Session)var6.next();
			if(client != session) {
				client.getBasicRemote().sendText("newUser|" + newUsername);
			}
		}

	}

	@OnMessage
	public void onMessage(Session session, String message) throws Exception {
		String[] data = message.split("\\|");
		String destination = data[0];
		String messageContent = data[1];
		String sender = (String)session.getUserProperties().get("username");
		if(destination.equals("all")) {
			Iterator client = chatRooms.values().iterator();

			while(client.hasNext()) {
				Session response = (Session)client.next();
				if(!response.equals(session)) {
					response.getBasicRemote().sendText("message|" + sender + "|" + messageContent + "|all");
				}
			}
		} else {
			Session client1 = (Session)chatRooms.get(destination);
			String response1 = "message|" + sender + "|" + messageContent;
			client1.getBasicRemote().sendText(response1);
		}

	}

	@OnClose
	public void onClose(Session session) throws Exception {
		String username = (String)session.getUserProperties().get("username");
		chatRooms.remove(username);
		Iterator var3 = chatRooms.values().iterator();

		while(var3.hasNext()) {
			Session client = (Session)var3.next();
			client.getBasicRemote().sendText("removeUser|" + username);
		}

	}
}

