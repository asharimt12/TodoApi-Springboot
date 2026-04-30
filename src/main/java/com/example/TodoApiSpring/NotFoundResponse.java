package com.example.TodoApiSpring;

import java.util.List;

public class NotFoundResponse {
    private String message;
    private long todoId;
    public NotFoundResponse(long todoId){
        this.todoId = todoId;
        this.message = "NO Entry Found for " + todoId;
    }
    public String getMessage(){
        return this.message;
    }


}
