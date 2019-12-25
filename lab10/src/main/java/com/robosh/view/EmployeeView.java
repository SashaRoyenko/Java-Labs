package com.robosh.view;

import com.robosh.components.EmployeeEditor;
import com.robosh.data.entity.Employee;
import com.robosh.data.entity.Project;
import com.robosh.service.EmployeeService;
import com.robosh.service.ProjectService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route
public class EmployeeView extends VerticalLayout {

  private final EmployeeService employeeService;
  private ProjectService projectService;
  private Grid<Employee> employeeGrid;
  private final EmployeeEditor employeeEditor;

  private final TextField filter = new TextField();
  private final Button addNewButton = new Button("New employee", VaadinIcon.PLUS.create());
  private final HorizontalLayout toolbar = new HorizontalLayout(filter, addNewButton);


  @Autowired
  public EmployeeView(EmployeeService employeeService,
      ProjectService projectService, EmployeeEditor employeeEditor) {
    this.employeeService = employeeService;
    this.projectService = projectService;
    this.employeeEditor = employeeEditor;
    this.employeeGrid = new Grid<>();
    init();
  }

  private void init() {
    filter.setPlaceholder("Type to filter");
    filter.setValueChangeMode(ValueChangeMode.EAGER);
    filter.addValueChangeListener(field -> fillList(field.getValue()));

    employeeGrid.addColumn(Employee::getId).setHeader("Id").setSortable(true);
    employeeGrid.addColumn(Employee::getFirstName).setHeader("First name").setSortable(true);
    employeeGrid.addColumn(Employee::getLastName).setHeader("Second name").setSortable(true);
    employeeGrid.addColumn(Employee::getBirthDate).setHeader("Birth date").setSortable(true);
    employeeGrid.addColumn(Employee::getRole).setHeader("Role").setSortable(true);
    employeeGrid.addComponentColumn(e -> {
      ComboBox<Project> project = new ComboBox<>();
      project.setItemLabelGenerator(Project::getName);
      project.setItems(e.getProject());
      project.setValue(e.getProject().isEmpty() ? new Project("Bench") : e.getProject().get(0));
      return project;
    }).setHeader("Project").setSortable(false);

//    add(employeeGrid);
    add(toolbar, this.employeeEditor, employeeGrid);

    employeeGrid
        .asSingleSelect()
        .addValueChangeListener(e -> this.employeeEditor.editEmployee(e.getValue()));

    addNewButton.addClickListener(e -> this.employeeEditor.editEmployee(new Employee()));

    this.employeeEditor.setChangeHandler(() -> {
      this.employeeEditor.setVisible(false);
      fillList(filter.getValue());
    });

    fillList("");
  }

  private void fillList(String name) {
    if (name.isEmpty()) {
      employeeGrid.setItems(this.employeeService.findAll());
    } else {
      employeeGrid.setItems(this.employeeService.findByName(name));
    }
  }
}
