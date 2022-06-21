package websocket;


import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/chatroomServerEndpoint/{room}")
public class ChatroomServerEndpoint {

	static List<Set<Session>> chatrooms = new LinkedList<Set<Session>>();
	static HashMap<Integer, Integer> hashmap = new HashMap<Integer, Integer>();
	
	@OnOpen
	public void handleOpen(Session userSession, @PathParam("room") Integer room) {
		
		if (hashmap.get(room) != null) {
			//soba postoji
			chatrooms.get(hashmap.get(room)).add(userSession);
		}
		else {
			//soba ne postoji
			Set<Session> chatroom = Collections.synchronizedSet(new HashSet<Session>());
			chatroom.add(userSession);
			hashmap.put(room, hashmap.size());
			chatrooms.add(chatroom);	
		}
			
	}
	
	@OnClose
	public void handleClose(Session userSession, @PathParam("room") Integer room) {
		chatrooms.get(hashmap.get(room)).remove(userSession);
	}
	
	@OnMessage
	public void handleMessage(String message, Session userSession, @PathParam("room") Integer room) throws IOException {
		
		//System.out.println("Dobio poruku " + message);
		String username = (String) userSession.getUserProperties().get("username");
		if (username == null) {
			userSession.getUserProperties().put("username", message);
			userSession.getBasicRemote().sendText(buildJsonData("System", "you are now connected as " + message));
			
		}
		else {
			Iterator<Session> iterator = chatrooms.get(hashmap.get(room)).iterator();
			while(iterator.hasNext()) {
				iterator.next().getBasicRemote().sendText(buildJsonData(username, message));
				
				
			}
			
		}
		
	}
	private String buildJsonData(String username, String message) {
		JsonObject jsonObject = Json.createObjectBuilder().add("message", username + ": " + message).build();
		StringWriter stringWriter = new StringWriter();
		try (JsonWriter jsonWriter = Json.createWriter(stringWriter))
		{
			jsonWriter.write(jsonObject);
		}
		return stringWriter.toString();
	}
	
}
