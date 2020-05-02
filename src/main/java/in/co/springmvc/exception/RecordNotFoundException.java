package in.co.springmvc.exception;

/**
 * RecordNotFoundException thrown when a record not found occurred
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */

public class RecordNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RecordNotFoundException(String msg) {
		super(msg);

	}

}
