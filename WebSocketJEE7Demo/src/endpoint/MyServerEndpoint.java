package endpoint;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket")
public class MyServerEndpoint {

	// Create a Set to hold client sessions
	private static final Set<Session> clientSessions = Collections
			.synchronizedSet(new HashSet<Session>());

	/**
	 * Add client session to the Set
	 * 
	 * @param aClientSession
	 */
	@OnOpen
	public void onOpen(Session aClientSession) {
		clientSessions.add(aClientSession);
	}

	/**
	 * Remove client session from the Set
	 * 
	 * @param aClientSession
	 */
	@OnClose
	public void onClose(Session aClientSession) {
		clientSessions.remove(aClientSession);
	}

	/**
	 * Send Text back
	 * 
	 * @param message
	 * @param client
	 * @throws IOException
	 * @throws EncodeException
	 */
	@OnMessage
	public void onMessage(String message, Session client) throws IOException,
			EncodeException {

		// send data to all connected clients (including caller)
		for (Session clientSession : clientSessions) {
//			if (message.equals("Open Sesame")) {
//
//				JsonObjectBuilder builder = Json.createObjectBuilder();
//				builder.add("person",
//						Json.createObjectBuilder().add("firstName", "Michael")
//								.add("lastName", "Jo"));
//				JsonObject result = builder.build();
//				// StringWriter sw = new StringWriter();
//				// try(JsonWriter writer = Json.createWriter(sw))
//				// {
//				// writer.writeObject(result);
//				// }
//				//
//				message = result.toString();
//
//			}
			clientSession.getBasicRemote().sendText(message);

		}
	}

	@OnError
	public void onError(Session aclientSession, Throwable aThrowable) {
		System.out.println("Error : " + aclientSession);

	}

}
