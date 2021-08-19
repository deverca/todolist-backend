package com.example.todolist.service;

import com.example.todolist.model.Todo;
import com.example.todolist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();

    }

    public Todo getTodoById(Integer id) {

        return todoRepository.findById(id).orElseThrow(null);
    }


    public Todo addTodo(Todo todo) {
        todoRepository.save(todo);
        return todo;
    }

    public Todo removeTodo(Integer id) {
        Todo itemToDelete = getTodoById(id);
        if (!ObjectUtils.isEmpty(itemToDelete)) {
            todoRepository.delete(itemToDelete);

            return itemToDelete;
        }
        return null;

    }

    public Todo updateTodo(Integer id, Todo newTodoInfo) {
        Todo itemToUpdate = getTodoById(id);
        if (!ObjectUtils.isEmpty(itemToUpdate)) {
            return todoRepository.save(updateTodoInformation(itemToUpdate, newTodoInfo));
        }
        return null;
    }

    private Todo updateTodoInformation(Todo itemToUpdate, Todo newTodoInfo) {
        if (newTodoInfo.getText() != null) {
            itemToUpdate.setText(newTodoInfo.getText());
        }

        if (newTodoInfo.getDone() != null) {
            itemToUpdate.setDone(newTodoInfo.getDone());
        }
        return itemToUpdate;
    }


}
