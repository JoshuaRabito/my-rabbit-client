package com.joshuacodes.rabbit.client.domain;

public class Message {
	String contents;

	public Message(String contents) {
		super();
		this.contents = contents;
	}

	public String getContents() {
		return contents;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contents == null) ? 0 : contents.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (contents == null) {
			if (other.contents != null)
				return false;
		} else if (!contents.equals(other.contents))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Message [contents=").append(contents).append("]");
		return builder.toString();
	}
	
	

}
