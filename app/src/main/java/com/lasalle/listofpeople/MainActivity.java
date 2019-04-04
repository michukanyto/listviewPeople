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
import model.FilePeopleManagment;
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


        listPerson = FilePeopleManagment.readFile(this,"person");
        Log.i("# people --- ", String.valueOf(listPerson.size()));
        findPeople();



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
                Log.i("# students", String.valueOf(listStudents.size()));

                break;
            case R.id.buttonEmployee:
                employeeArrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listEmployees);
                listViewPeople.setAdapter(employeeArrayAdapter);
                employeeArrayAdapter.notifyDataSetChanged();
                listType = ListType.EMPLOYEE;
                Log.i("# Employees", String.valueOf(listEmployees.size()));

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

    private void findPeople() {
        Student aStudent;
        Employee anEmployee;
        for (int i = 0; i < listPerson.size(); i++){
            if(listPerson.get(i) instanceof Student){
                aStudent = (Student)listPerson.get(i);
                listStudents.add(aStudent);
            }else{
                anEmployee = (Employee) listPerson.get(i);
                listEmployees.add(anEmployee);
            }
        }
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
            if(currentPosition < listStudents.size()){
                listStudents.remove(currentPosition);
            }else{
                listEmployees.remove(currentPosition - listStudents.size());
            }
            personArrayAdapter.notifyDataSetChanged();
        }
    }

}

