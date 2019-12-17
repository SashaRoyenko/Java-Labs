package com.robosh.view;

import com.robosh.components.EmployeeEditor;
import com.robosh.data.dto.EmployeeDto;
import com.robosh.service.EmployeeService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route
public class EmployeeView extends VerticalLayout {

  private final EmployeeService employeeService;
  private Grid<EmployeeDto> employeeGrid;
  private final EmployeeEditor employeeEditor;

  private final TextField filter = new TextField();
  private final Button addNewButton = new Button("New employee", VaadinIcon.PLUS.create());
  private final HorizontalLayout toolbar = new HorizontalLayout(filter, addNewButton);

  public EmployeeView(EmployeeService employeeService,
      EmployeeEditor employeeEditor) {
    this.employeeService = employeeService;
    this.employeeEditor = employeeEditor;
    this.employeeGrid = new Grid<>();

    init();
  }

  private void init() {
    filter.setPlaceholder("Type to filter");
    filter.setValueChangeMode(ValueChangeMode.EAGER);
    filter.addValueChangeListener(field -> fillList(field.getValue()));

    employeeGrid.addColumn(EmployeeDto::getId).setHeader("Id").setSortable(true);
    employeeGrid.addColumn(EmployeeDto::getFirstName).setHeader("First name").setSortable(true);
    employeeGrid.addColumn(EmployeeDto::getLastName).setHeader("Second name").setSortable(true);
    employeeGrid.addColumn(EmployeeDto::getBirthDate).setHeader("Birth date").setSortable(true);
    employeeGrid.addColumn(EmployeeDto::getRole).setHeader("Role").setSortable(true);
    employeeGrid.addColumn(e -> e.getProject().getName()).setHeader("Project").setSortable(true);
    add(toolbar, this.employeeEditor, employeeGrid);

    employeeGrid
        .asSingleSelect()
        .addValueChangeListener(e -> this.employeeEditor.editEmployee(e.getValue()));

    addNewButton.addClickListener(e -> this.employeeEditor.editEmployee(new EmployeeDto()));

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
