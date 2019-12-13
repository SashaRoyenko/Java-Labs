package com.robosh.view;

import com.robosh.data.entity.Employee;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class EmployeeView extends VerticalLayout {
    private Grid<Employee> employeeGrid;
}
