package com.project.todo.Exceptions;

import com.project.todo.Response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class HandleRepetitiveTask extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RepetitiveTaskException.class)
    public ResponseEntity<ResponseMessage> handleRepetitiveTask() {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("response.message"));
    }
}
