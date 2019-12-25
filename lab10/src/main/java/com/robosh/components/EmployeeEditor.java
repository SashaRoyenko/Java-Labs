package com.robosh.components;

import com.robosh.data.dto.EmployeeDto;
import com.robosh.data.entity.Employee;
import com.robosh.data.entity.Project;
import com.robosh.data.enums.Role;
import com.robosh.service.EmployeeService;
import com.robosh.service.ProjectService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class EmployeeEditor extends VerticalLayout implements KeyNotifier {

  private final EmployeeService employeeService;
  private final ProjectService projectService;

  private Employee employee;

  private TextField firstName = new TextField("", "First name");
  private TextField lastName = new TextField("", "Last name");
  private DatePicker birthDate = new DatePicker();
  private ComboBox<Role> role = new ComboBox<>();
  private List<ComboBox<Project>> project = new ArrayList<>();


  private Button save = new Button("Save");
  private Button cancel = new Button("Cancel");
  private Button delete = new Button("Delete");
  private HorizontalLayout buttons = new HorizontalLayout(save, cancel, delete);

  private Binder<Employee> binder = new Binder<>(Employee.class);

  @Setter
  private ChangeHandler changeHandler;

  public interface ChangeHandler {

    void onChange();
  }

  @Autowired
  public EmployeeEditor(EmployeeService employeeService,
      ProjectService projectService) {
    this.employeeService = employeeService;
    this.projectService = projectService;

    init();
  }

  private void init() {
    birthDate.setLocale(new Locale("uk", "UA"));
    birthDate.setClearButtonVisible(true);

    role.setItems(Arrays.asList(Role.values()));
    role.setPlaceholder("Employee role");

    for (ComboBox<Project> projectComboBox : project) {
      projectComboBox.setItemLabelGenerator(Project::getName);
      projectComboBox.setItems(this.projectService.findAll());
      projectComboBox.setPlaceholder("Employee project");
    }

    add(firstName, lastName, birthDate, role, buttons);
    for (ComboBox<Project> projectComboBox : project) {
       add(projectComboBox);
    }

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
    employeeService.create(employee);
    changeHandler.onChange();
  }

  private void delete() {
    employeeService.deleteById(employee.getId());
    changeHandler.onChange();
  }

  public void editEmployee(Employee emp) {
    if (emp == null) {
      setVisible(false);
      return;
    }

    if (emp.getId() != null) {
      this.employee = employeeService.findById(emp.getId());
    } else {
      this.employee = emp;
    }

    binder.setBean(this.employee);

    setVisible(true);

    lastName.focus();
  }
}
