package musichub_server.business;

import java.lang.Exception;

public class NoElementFoundException extends Exception {
	/**
	 *returns the error message that there's no element
	 * @param msg
	 */
	public NoElementFoundException (String msg) {
		super(msg);
	}
}