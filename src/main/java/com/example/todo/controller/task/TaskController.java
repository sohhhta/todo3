package com.example.todo.controller.task;

import com.example.todo.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tasks")
public class TaskController{

    private final TaskService taskService;

    @GetMapping
    /// 削除機能のために記述した内容
    public String list(TaskSearchForm searchForm,
                       @RequestParam(name = "mode", required = false) String mode,
                       @AuthenticationPrincipal UserDetails user,
                       Model model){
        var taskList = taskService.find(searchForm.toEntity(user.getUsername()))
                .stream()
                .map(TaskDTO::toDTO)
                .toList();
        model.addAttribute("taskList", taskList);
        model.addAttribute("searchDTO", searchForm.toDTO());
        /// 受け取った mode の値（"DELETE" または null）を、画面に渡す。
        model.addAttribute("mode", mode);
        return "tasks/list";
    }

    @GetMapping("/login")
    public String loginRedirect() {
        return "redirect:/login";
    }

    @GetMapping("/{id}")
    public String showDetail(@PathVariable("id") long taskId, Model model) {
        /// taskId--->TaskEntity
        var taskDTO = taskService.findById(taskId)
                .map(TaskDTO::toDTO)
                .orElseThrow(TaskNotFoundException::new);
        model.addAttribute("task", taskDTO);
        return "tasks/detail";
    }

    //GET/tasks/creationForm
    @GetMapping("/creationForm")
    public String showCreationForm(@ModelAttribute TaskForm form, Model model){
        model.addAttribute("mode", "CREATE");
        return "tasks/form";
    }

    // POST /tasks
    @PostMapping
    public String create(@Validated TaskForm form, BindingResult bindingResult, @AuthenticationPrincipal UserDetails user, Model model){
        if (bindingResult.hasErrors()){
            return showCreationForm(form, model);
        }
        // ユーザー情報のチェックを追加
        if (user == null || user.getUsername() == null || user.getUsername().isEmpty()) {
            return "redirect:/login";
        }
        taskService.create(form.toEntity(user.getUsername()));
        return "redirect:/tasks";
    }

    // GET/tasks/{tasksId}/editForm
    @GetMapping("/{id}/editForm")
    public String showEditForm(@PathVariable("id") long id, Model model){
        var form = taskService.findById(id)
                .map(TaskForm::fromEntity)
                        .orElseThrow(TaskNotFoundException::new);
        model.addAttribute("taskForm", form);
        model.addAttribute("mode", "EDIT");
        return "tasks/form";
    }

    @PutMapping("{id}") //PUT /tasks/{id}
    public String update(
            @PathVariable("id") long id,
            @Validated @ModelAttribute TaskForm form,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails user,
            Model model
    ){
        if (bindingResult.hasErrors()){
            model.addAttribute("mode", "EDIT");
            return "/tasks/form";
        }

        var entity = form.toEntity(id, user.getUsername());
        taskService.update(entity);
        return "redirect:/tasks/{id}";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") long id){
        return "redirect:/tasks";
    }

    /// 削除機能のために追加した内容
    @PostMapping("/deleteList")
    public String deleteList(@RequestParam(name = "deleteIds", required = false) List<Long> deleteIds) {
        if (deleteIds != null) {
            taskService.deleteAll(deleteIds);
        }
        return "redirect:/tasks";
    }
}