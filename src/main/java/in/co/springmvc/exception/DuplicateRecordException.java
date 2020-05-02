package in.co.springmvc.exception;

/**
 * DuplicateRecordException thrown when a duplicate record occurred
 * 
 * @author Session Facade
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */

public class DuplicateRecordException extends Exception {

	public DuplicateRecordException(String msg) {
		super(msg);
	}

}
