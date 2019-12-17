package com.robosh.components;

import com.robosh.data.entity.Project;
import com.robosh.service.ProjectService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;

@SpringComponent
@UIScope
public class ProjectEditor extends VerticalLayout implements KeyNotifier {

  private final ProjectService projectService;

  private Project project;

  private TextField name = new TextField("", "Project name");

  private Button save = new Button("Save");
  private Button cancel = new Button("Cancel");
  private Button delete = new Button("Delete");
  private HorizontalLayout buttons = new HorizontalLayout(save, cancel, delete);

  private Binder<Project> binder = new Binder<>(Project.class);

  @Setter
  private EmployeeEditor.ChangeHandler changeHandler;

  public interface ChangeHandler {

    void onChange();
  }


  public ProjectEditor(ProjectService projectService) {
    this.projectService = projectService;

    init();
  }

  private void init() {
    add(name, buttons);
    binder.bindInstanceFields(this);

    setSpacing(true);

    save.getElement().getThemeList().add("primary");
    delete.getElement().getThemeList().add("error");

    addKeyPressListener(Key.ENTER, e -> save());

    save.addClickListener(e -> save());
    delete.addClickListener(e -> delete());
    cancel.addClickListener(e -> this.setVisible(false));
    setVisible(false);
  }

  private void save() {
    projectService.create(project);
    changeHandler.onChange();
  }

  private void delete() {
    projectService.deleteById(project.getId());
    changeHandler.onChange();
  }

  public void editProject(Project project) {
    if (project == null) {
      setVisible(false);
      return;
    }

    if (project.getId() != null) {
      this.project = projectService.findById(project.getId());
    } else {
      this.project = project;
    }

    binder.setBean(this.project);

    setVisible(true);

    name.focus();
  }
}
