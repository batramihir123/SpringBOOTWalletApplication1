package com.HelloSpring.GlobalException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.HelloSpring.apiresponse.ApiResponse;

import lombok.extern.slf4j.Slf4j;



@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnAuthorizedUser.class)
	public ResponseEntity<ApiResponse> handleUnAuthorizedUser(EntityAlreadyExistException ex,
																		  WebRequest webRequest)
	{
		log.info("handleUnAuthorizedUser fired");
		return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED.value(),  ex.getMessage()),
				HttpStatus.UNAUTHORIZED);
	}


    @ExceptionHandler(EntityAlreadyExistException.class)
    public ResponseEntity<ApiResponse> handleEntityAlreadyExistsException(EntityAlreadyExistException ex,
                                                                          WebRequest webRequest) 
    {
    	log.info("handleEntityAlreadyExistsException fired");
        return new ResponseEntity<>(new ApiResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
	

	 @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<ApiResponse> handleResourecNotFoundExecption(ResourceNotFoundException ex, WebRequest webRequest) {
	    	log.info("handleResourecNotFoundExecption fired");
	    	
	        return new ResponseEntity<>(new ApiResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage()),
	                HttpStatus.UNAUTHORIZED);
	    } 
	
}
