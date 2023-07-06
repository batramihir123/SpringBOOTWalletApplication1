package com.HelloSpring.GlobalException;
public class ResourceNotFoundException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException() {
        
    }

    public ResourceNotFoundException(String msg) {
        super(msg + " " + "Entity Not Found");
    }
}
