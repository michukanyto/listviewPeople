package com.lasalle.listofpeople;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import model.Employee;
import model.Person;
import model.Student;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener,DialogInterface.OnClickListener {

    TextView textViewId;
    TextView textViewAge;
    TextView textViewJob;
    TextView textViewSalary;
    TextView textViewProgram;
    Button buttonStudent;
    Button buttonEmployee;
    Button buttonAll;
    ListView listViewPeople;
    ArrayList<Student> listStudents;
    ArrayList<Employee> listEmployees;
    ArrayList<Person> listPerson;
    ArrayAdapter<Person> personArrayAdapter;
    ArrayAdapter<Employee> employeeArrayAdapter;
    ArrayAdapter<Student> studentArrayAdapter;
    ListType listType;
    final String BLANKSPACE = "";
    int currentPosition;
    AlertDialog.Builder alertDialog;



    public enum ListType{
        STUDENT,
        EMPLOYEE,
        PERSON;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();

    }

    public void initialize() {
        textViewId = findViewById(R.id.textViewShowId);
        textViewAge = findViewById(R.id.textViewShowAge);
        textViewJob = findViewById(R.id.textViewShowJob);
        textViewSalary = findViewById(R.id.textViewShowSalary);
        textViewProgram = findViewById(R.id.textViewShowProgram);
        buttonStudent = findViewById(R.id.buttonStudent);
        buttonEmployee = findViewById(R.id.buttonEmployee);
        buttonAll = findViewById(R.id.buttonAll);
        buttonStudent.setOnClickListener(this);
        buttonEmployee.setOnClickListener(this);
        buttonAll.setOnClickListener(this);

        listViewPeople = findViewById(R.id.listViewPeople);
        listViewPeople.setOnItemClickListener(this);
        listViewPeople.setOnItemLongClickListener(this);

        listStudents = new ArrayList<>();
        listEmployees = new ArrayList<>();
        listPerson = new ArrayList<>();

        fillUpStudent();
        fillUpEmployee();


        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Delete");
        alertDialog.setMessage("Do you want to delete (Y/N) ");
        alertDialog.setPositiveButton("Yes",this);
        alertDialog.setNegativeButton("No",this);

    }


    @Override
    public void onClick(View v) {
        cleanTextViews();
        switch(v.getId()){
            case R.id.buttonStudent:
                studentArrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listStudents);
                listViewPeople.setAdapter(studentArrayAdapter);
                listType = ListType.STUDENT;
                studentArrayAdapter.notifyDataSetChanged();

                break;
            case R.id.buttonEmployee:
                employeeArrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listEmployees);
                listViewPeople.setAdapter(employeeArrayAdapter);
                employeeArrayAdapter.notifyDataSetChanged();
                listType = ListType.EMPLOYEE;

                break;
            case R.id.buttonAll:
                personArrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listPerson);
                Log.i("List Size ===> ",String.valueOf(listPerson.size()));
                listViewPeople.setAdapter(personArrayAdapter);
                listType = ListType.PERSON;
                personArrayAdapter.notifyDataSetChanged();

                break;
            default:
                break;
        }

    }


    public void fillUpStudent() {
        listStudents.add(new Student("Philip",22,"ad1234","Marketing"));
        listStudents.add(new Student("Stefan",24,"ad4353","Computers Science"));
        listStudents.add(new Student("Loise",20,"ad3322","Medicine"));
        listStudents.add(new Student("Carlos",19,"ad5632","Marketing"));
        listStudents.add(new Student("Emilie",28,"ad15555","Marketing"));
        listStudents.add(new Student("Nicole",35,"ad7865","Computer Science"));
        listPerson.addAll(listStudents);
        Log.i("List Size ===> ",String.valueOf(listPerson.size()));
    }


    public void fillUpEmployee() {
        listEmployees.add(new Employee("Andrew", 22, "tt1234", "Engineer", 100000));
        listEmployees.add(new Employee("Julian", 45, "tt4353", "Networking", 70000));
        listEmployees.add(new Employee("Brandon", 20, "tt3322", "Medicine", 150000));
        listEmployees.add(new Employee("Carlos", 19, "tt5632", "Engineer", 90000));
        listEmployees.add(new Employee("Camila", 28, "tt15555", "Engineer", 95000));
        listEmployees.add(new Employee("Jaime", 35, "tt7865", "Carpenter", 70000));
        listPerson.addAll(listEmployees);
        Log.i("List Size ===> ",String.valueOf(listPerson.size()));
    }

    public void cleanTextViews(){
        textViewId.setText(BLANKSPACE);
        textViewAge.setText(BLANKSPACE);
        textViewProgram.setText(BLANKSPACE);
        textViewId.setText(BLANKSPACE);
        textViewJob.setText(BLANKSPACE);
        textViewSalary.setText(BLANKSPACE);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("------------->", parent.getAdapter().getItem(position).getClass().toString());
        Log.i("------------->", String.valueOf(parent.getAdapter().getCount()));
        Student aStudent;
        Employee anEmployee;
        Person aPerson = (Person) parent.getAdapter().getItem(position);

        if (listType == ListType.PERSON) {

            if (aPerson instanceof Student) {
                aStudent = (Student)listPerson.get(position);
                printOutData(aStudent.getStudentId(), aStudent.getAge(),BLANKSPACE,BLANKSPACE,aStudent.getProgram());

            } else {
                anEmployee = (Employee)listPerson.get(position);
                printOutData(anEmployee.getEmpolyeeId(),anEmployee.getAge(),anEmployee.getJob(),String.valueOf(anEmployee.getSalary()),BLANKSPACE);
            }

        } else {

            if (aPerson instanceof Student) {
                aStudent = listStudents.get(position);
                printOutData(aStudent.getStudentId(), aStudent.getAge(),BLANKSPACE,BLANKSPACE,aStudent.getProgram());


            } else {
                anEmployee = listEmployees.get(position);
                printOutData(anEmployee.getEmpolyeeId(),anEmployee.getAge(),anEmployee.getJob(),String.valueOf(anEmployee.getSalary()),BLANKSPACE);
            }

        }

    }

    public void printOutData (String userId,int age,String job,String salary,String program){
        textViewId.setText(userId);
        textViewAge.setText(String.valueOf(age));
        textViewJob.setText(job);
        textViewSalary.setText(salary);
        textViewProgram.setText(program);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        currentPosition = position;
        AlertDialog dialogBox = alertDialog.create();
        dialogBox.show();
        Log.i("------------->", "in ItemLongClick");
        return false;

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which){
            case Dialog.BUTTON_POSITIVE:
                Log.i("------------->", "testing button inside pop up");
                deleteUserFromList();
                break;

            case Dialog.BUTTON_NEGATIVE:
                break;
        }

    }

    public void deleteUserFromList(){
        if (listType == ListType.STUDENT){
            listStudents.remove(currentPosition);
            studentArrayAdapter.notifyDataSetChanged();
        }else if(listType == ListType.EMPLOYEE){
            listEmployees.remove(currentPosition);
            employeeArrayAdapter.notifyDataSetChanged();
        }else if (listType == ListType.PERSON){
            listPerson.remove(currentPosition);
            personArrayAdapter.notifyDataSetChanged();
        }
    }

}

