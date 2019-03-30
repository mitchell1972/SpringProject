package springbootdemo;

import org.springframework.stereotype.Repository;

import springbootdemo.Employee;
import springbootdemo.Employees;

@Repository
public class EmployeeDOA
{
    private static Employees list = new Employees();

    static
    {
        list.getEmployeeList().add(new Employee(1, "Lokesh", "Gupta", "howtodoinjava@gmail.com"));
//        list.getEmployeeList().add(new Employee(2, "Alex", "Kolenchiskey", "abc@gmail.com"));
//        list.getEmployeeList().add(new Employee(3, "David", "Kameron", "titanic@gmail.com"));
//        list.getEmployeeList().add(new Employee(7, "Davido", "Kamerono", "titanic@gmailo.com"));

    }

    public Employees getAllEmployees()
    {
        return list;
    }

    public void addEmployee(Employee employee) {
        list.getEmployeeList().add(employee);
    }
}
