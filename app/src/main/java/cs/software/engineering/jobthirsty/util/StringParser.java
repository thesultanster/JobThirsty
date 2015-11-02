package cs.software.engineering.jobthirsty.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by timka on 11/1/2015.
 */
public class StringParser {

    //Result Variables
    private ArrayList<String> parsed = new ArrayList<>();
    private ArrayList<ArrayList<String>> concated = new ArrayList<>();


    // CONSTRUCTORS [START] ------------------------------------------------------------------------
    public StringParser(ArrayList<ArrayList<String>> toConcat) {
        for(int i = 0; i < toConcat.size(); ++i) {
            ArrayList<String> row = toConcat.get(i);

            //concat each field with delim between
            String concatenated = "";
            for(int j = 0; j < row.size(); ++j) {
                concatenated += row.get(j);
                if(j+1 < row.size())
                    concatenated += "!##!";
            }

            //add to list
            parsed.add(concatenated);
        }
    }

    //lazy method of allowing two different constructors with same generics
    // any argument for trash would be fine
    public StringParser(ArrayList<String> toSplit, boolean trash) {
        for(int i = 0; i < toSplit.size(); ++i) {
            //directly convert array to arrayList
            String line = toSplit.get(i);
            String[] tmp = line.split("!##!");
            ArrayList<String> parsedRow = new ArrayList<>(Arrays.asList(tmp));
            concated.add(parsedRow);
        }
    }
    // [END] ---------------------------------------------------------------------------------------



    // UTILITY FUNCTIONS [START] -------------------------------------------------------------------
    public ArrayList<String> getParsed(){ return parsed; }
    public ArrayList<ArrayList<String>> getConcated(){ return concated; }
    // [END] ---------------------------------------------------------------------------------------
}
