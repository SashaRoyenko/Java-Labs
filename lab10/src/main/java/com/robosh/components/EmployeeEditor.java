package com.robosh.components;

import com.robosh.data.dto.EmployeeDto;
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
import java.util.Arrays;
import java.util.Locale;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class EmployeeEditor extends VerticalLayout implements KeyNotifier {

  private final EmployeeService employeeService;
  private final ProjectService projectService;

  private EmployeeDto employeeDto;

  private TextField firstName = new TextField("", "First name");
  private TextField lastName = new TextField("", "Last name");
  private DatePicker birthDate = new DatePicker();
  private ComboBox<Role> role = new ComboBox<>();
  private ComboBox<Project> project = new ComboBox<>();


  private Button save = new Button("Save");
  private Button cancel = new Button("Cancel");
  private Button delete = new Button("Delete");
  private HorizontalLayout buttons = new HorizontalLayout(save, cancel, delete);

  private Binder<EmployeeDto> binder = new Binder<>(EmployeeDto.class);

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

    project.setItemLabelGenerator(Project::getName);
    project.setItems(this.projectService.findAll());
    project.setPlaceholder("Employee project");

    add(firstName, lastName, birthDate, role, project, buttons);

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
    employeeService.create(employeeDto);
    changeHandler.onChange();
  }

  private void delete() {
    employeeService.deleteById(employeeDto.getId());
    changeHandler.onChange();
  }

  public void editEmployee(EmployeeDto emp) {
    if (emp == null) {
      setVisible(false);
      return;
    }

    if (emp.getId() != null) {
      this.employeeDto = employeeService.findById(emp.getId());
    } else {
      this.employeeDto = emp;
    }

    binder.setBean(this.employeeDto);

    setVisible(true);

    lastName.focus();
  }
}
