package org.iomedia.galen.extras;

public class SessionContext {
	private static SessionContext instance = new SessionContext();
	private String session_ID = null;

	private SessionContext() {
		// to enforce singleton
	}

	public static SessionContext get() {
		return instance;
	}

	public String nbsp = "\u00a0";

	public String getSession_ID() {
		if (session_ID == null) {
			session_ID = String.valueOf(System.currentTimeMillis());
		}
		return session_ID;
	}

}
