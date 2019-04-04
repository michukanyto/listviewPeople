package model;

import android.content.Context;
import android.content.res.AssetManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class FilePeopleManagment {

    public static ArrayList<Person> readFile(Context context, String fileName){
        ArrayList<Person> listOfPerson = new ArrayList<>();

        AssetManager assetManager = context.getResources().getAssets();

        try {
            InputStreamReader isr = new InputStreamReader(assetManager.open(fileName));

            BufferedReader br = new BufferedReader(isr);

            String oneLine = br.readLine();

            while (oneLine != null){
                StringTokenizer st = new StringTokenizer(oneLine,",");
                String type = st.nextToken();
                if (type.equals("S") || type.equals("s")){
                    listOfPerson.add(new Student(st.nextToken(),Integer.parseInt(st.nextToken()),st.nextToken(),st.nextToken()));
                }else if (type.equals("E") || type.equals("e")){
                    listOfPerson.add(new Employee(st.nextToken(), Integer.parseInt(st.nextToken()), st.nextToken(), st.nextToken(), Integer.parseInt(st.nextToken())));
                }

                oneLine = br.readLine();
            }
            br.close();
            isr.close();

        } catch (IOException e) {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        return listOfPerson;

    }
}
