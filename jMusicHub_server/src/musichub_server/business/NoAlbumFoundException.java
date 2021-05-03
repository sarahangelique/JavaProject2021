package musichub_server.business;

import java.lang.Exception;

public class NoAlbumFoundException extends Exception {
	/**
	 *returns the error message that there's no album
	 */
	public NoAlbumFoundException (String msg) {
		super(msg);
	}
}