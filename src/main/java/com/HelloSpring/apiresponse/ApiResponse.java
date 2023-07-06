package com.HelloSpring.apiresponse;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ApiResponse {
	
	private Integer code;
	 
	 
	 private String message;
	 @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="dd-MM-yyyy hh:mm:ss")
	 private LocalDateTime timestamp=LocalDateTime.now();

	 
	 private Object data;
	 public ApiResponse(Integer code, String message, Object data) {

	  this.code = code;
	  this.message = message;
	  this.data = data;
	 }
	 public ApiResponse(Integer code, String message) {
	 
	  this.code = code;
	  this.message = message;
	 }
	@Override
	public String toString() {
		return "ApiResponse [code=" + code + ", message=" + message + ", timestamp=" + timestamp + ", data=" + data + "]";
	}

}
