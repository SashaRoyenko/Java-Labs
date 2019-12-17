package com.robosh.view;

import com.robosh.components.ProjectEditor;
import com.robosh.data.entity.Project;
import com.robosh.service.ProjectService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route
public class ProjectView extends VerticalLayout {

  private final ProjectService projectService;
  private final Grid<Project> projectGrid;

  private final ProjectEditor projectEditor;

  private final TextField filter = new TextField();

  private final Button addNewButton = new Button("New project", VaadinIcon.PLUS.create());
  private final HorizontalLayout toolbar = new HorizontalLayout(filter, addNewButton);

  @Autowired
  public ProjectView(ProjectService projectService, ProjectEditor projectEditor) {
    this.projectService = projectService;
    this.projectGrid = new Grid<>(Project.class);
    this.projectEditor = projectEditor;

    init();
  }

  private void init() {
    filter.setPlaceholder("Type to filter");
    filter.setValueChangeMode(ValueChangeMode.EAGER);
    filter.addValueChangeListener(field -> fillList(field.getValue()));

    add(toolbar, this.projectEditor, projectGrid);

    projectGrid
        .asSingleSelect()
        .addValueChangeListener(e -> this.projectEditor.editProject(e.getValue()));

    addNewButton.addClickListener(e -> this.projectEditor.editProject(new Project()));

    this.projectEditor.setChangeHandler(() -> {
      this.projectEditor.setVisible(false);
      fillList(filter.getValue());
    });

    fillList("");
  }

  private void fillList(String name) {
    if (name.isEmpty()) {
      projectGrid.setItems(this.projectService.findAll());
    } else {
      projectGrid.setItems(this.projectService.findByName(name));
    }
  }
}
