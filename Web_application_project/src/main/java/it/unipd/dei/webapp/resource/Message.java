package it.unipd.dei.webapp.resource;

import org.json.JSONObject;

public class Message {

	private final String message;
	private final boolean isError;


	public Message(boolean error, String message) {
		this.isError = error;
		this.message = message;
	}

	public Message(final String message) {
		this.message = message;
		this.isError = false;
	}

	public final String getMessage() {
		return message;
	}


	public final boolean isError() {
		return isError;
	}


	public JSONObject toJSON(){
		JSONObject result = new JSONObject();
		result.put("error", this.isError);
		result.put("message", this.message);
		return result;
	}

}
