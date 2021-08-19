package com.example.todolist.integrationtest;


import com.example.todolist.model.Todo;
import com.example.todolist.repository.TodoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoIntegration {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TodoRepository todoRepository;

    @AfterEach
    void cleanUp() {
        todoRepository.deleteAll();
    }

    @BeforeEach
    void cleanUpBefore() {
        todoRepository.deleteAll();
    }


    @Test
    void should_return_all_todos_when_call_getAllTodos() throws Exception {
        //given
        final Todo todo = new Todo(1, "Get 8 hours of sleep.", false);
        final Todo savedTodo = todoRepository.save(todo);
        //when
        //then
        int id = savedTodo.getId();
        mockMvc.perform(MockMvcRequestBuilders.get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].text").value("Get 8 hours of sleep."))
                .andExpect(jsonPath("$[0].done").value(false));
    }

    @Test
    void should_add_todo_when_call_addTodo() throws Exception {
        //given
        String todo = "{\n" +
                "\"id\":1,\n" +
                "\"text\":\"Wash the dishes.\",\n" +
                "\"done\":false\n" +
                "}";
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/todos")
                .contentType(MediaType.APPLICATION_JSON).content(todo))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.text").value("Wash the dishes."))
                .andExpect(jsonPath("$.done").value(false));

    }

    @Test
    void should_update_todo_when_call_updateTodo() throws Exception {
        final Todo todo = new Todo(1, "Get 8 hours of sleep.", false);
        Todo savedTodo = todoRepository.save(todo);
        String newTodoInfo = "{\n" +
                "\"done\":true\n" +
                "}";
        //when
        //then
        int id = savedTodo.getId();
        mockMvc.perform(MockMvcRequestBuilders.put("/todos/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newTodoInfo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.text").value("Get 8 hours of sleep."))
                .andExpect(jsonPath("$.done").value(true));


    }

    @Test
    void should_delete_todo_when_call_deleteTodo() throws Exception {
        //given
        final Todo todo = new Todo(1, "Get 8 hours of sleep.", false);
        final Todo savedTodo = todoRepository.save(todo);
        //when
        //then
        int id = savedTodo.getId();
        mockMvc.perform(MockMvcRequestBuilders.delete("/todos/{id}",id))
                .andExpect(status().isOk());
    }


}
