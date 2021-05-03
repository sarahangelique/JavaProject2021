package musichub_server.business;

import java.lang.Exception;

public class NoPlayListFoundException extends Exception {
	/**
	 *returns the error message that there's no playlist
	 * @param msg
	 */
	public NoPlayListFoundException (String msg) {
		super(msg);
	}
}