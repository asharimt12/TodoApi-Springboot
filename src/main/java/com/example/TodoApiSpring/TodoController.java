package com.example.TodoApiSpring;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/todos/")
public class TodoController {
    private static List<Todo> todoList;

    public TodoController(){
        todoList = new ArrayList<>();
        todoList.add(new Todo(1, false, "Todo 1", 1));
        todoList.add(new Todo(2, true, "Todo 2", 2));
        todoList.add(new Todo(3, true, "Todo 3", 3));
    }
    @GetMapping()
    public ResponseEntity<List<Todo>> getTodos(){
        return ResponseEntity.ok(todoList);
    }

    @PostMapping()
//    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Todo> createTodo(@RequestBody Todo newTodo){
        todoList.add(newTodo);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTodo);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<?> getTodoById(@PathVariable long todoId){
        for(Todo todo: todoList){
           if (todo.getId() == todoId){
               return ResponseEntity.ok(todo);
           }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new NotFoundResponse(todoId));
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<?> deleteTodo(@PathVariable long todoId){
        for(Todo todo: todoList){
            if (todo.getId() == todoId){
                todoList.remove(todo);
                return ResponseEntity.ok(todoList);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new NotFoundResponse(todoId));
    }
/*
    @PatchMapping("/{todoId}")
    public ResponseEntity<?> patchTodo(@PathVariable long todoId,
                                       @RequestBody Map<String, Object> updates){
        for (Todo todo: todoList){
            if (todo.getId() == todoId){
                if (updates.containsKey("id")){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Illegal to update id...");
                }
                updates.forEach((key, value) -> {
                    switch (key){
                        case "title" -> todo.setTitle((String) value);
                        case "completed" -> todo.setCompleted((boolean) value);
                        case "userId" -> todo.setUserId((int) value);
                    }
                });
                return ResponseEntity.ok(todo);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new NotFoundResponse(todoId));
    }
*/
    //Another way to do above is by using query params
    @PatchMapping("/{todoId}")
    public ResponseEntity<?> patchTodos(@PathVariable long todoId,
                                        @RequestParam(required = false) Boolean isCompleted,
                                        @RequestParam(required = false) Integer userId,
                                        @RequestParam(required = false) String title){
        for(Todo todo: todoList){
            if(todo.getId() == todoId){
                if(isCompleted != null){
                    todo.setCompleted(isCompleted);
                }
                if(title != null){
                    todo.setTitle(title);
                }
                if(userId != null){
                    todo.setUserId(userId);
                }
                return ResponseEntity.ok(todo);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new NotFoundResponse(todoId));
    }
}

