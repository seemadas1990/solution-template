package com.tigerit.exam;


import static com.tigerit.exam.IO.*;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.Collections;

class Table{
    String name;
    Integer noOfColumns;
    Integer noOfRecords;
    String columnName[];
    Integer columnValues[][];
    Integer columnNoToMatch;
}

public class Solution implements Runnable {
    @Override
    public void run() {
        Integer noOfTestCases,noOfTables,noOfQueries;
        Integer i,j,r,c,r1,c1,k,l,m,mm,index,index2,columnNoToMatch1,columnNoToMatch2;
        String[] columnOrder,s, s1;
        String string,query,noOfColumnsAndRows,nameOfColumns,valuesOfRecords,columnHeading, firstTableName, secondTableName, columnName;
        Table[] table = new Table[11];
        Boolean allColumnPrintFlag;

        noOfTestCases = readLineAsInteger();

        for(i=1;i<=noOfTestCases;i++){
            string = "Test: "+i;
            printLine(string); 
            noOfTables = readLineAsInteger(); //no of tables

            for(j=0;j<noOfTables;j++){
                table[j] = new Table();
                table[j].name = readLine();
                noOfColumnsAndRows = readLine(); //number of columns and rows
                s = splitMethod(noOfColumnsAndRows);
                table[j].noOfColumns = parseInt(s[0]) ;
                table[j].noOfRecords = parseInt(s[1]);
                nameOfColumns = readLine();//name of columns
                s = splitMethod(nameOfColumns);
                table[j].columnName = new String[table[j].noOfColumns];
                for(mm=0;mm<s.length;mm++){
                    if(s[mm]!=null){
                        table[j].columnName[mm] = s[mm];
                    }
                }
                table[j].columnValues = new Integer[table[j].noOfRecords][table[j].noOfColumns];

                for(r=0;r<table[j].noOfRecords;r++){
                    valuesOfRecords = readLine();
                    s = splitMethod(valuesOfRecords);
                    for(c=0;c<table[j].noOfColumns;c++){
//                        printLine(s[c]);
                        table[j].columnValues[r][c] = parseInt(s[c]);
                    }
                }

            }
            noOfQueries = parseInt(readLine());
            for(k=0;k<noOfQueries;k++){
                columnHeading = "";
                allColumnPrintFlag = false;
                ArrayList<String> rowsOfTable = new ArrayList<>();
                firstTableName = "";
                secondTableName = "";
                Table firstTable = new Table();
                Table secondTable = new Table();
                
                for(l=0;l<5;l++){
                    query = readLine();
                    s = query.replaceAll("\\,", " ").replaceAll("\\=", " ").replaceAll("\\s+", " ").trim().split(" ");
                    
                    if(l==0){
                        if(s[1].equals("*")){
                            allColumnPrintFlag = true;
                        }
                        else{
                            allColumnPrintFlag = false;
                            for(m=0;m<s.length;m++){
                                
                                s1 = s[m].split("\\.");
                                if(s1.length == 2){
                                    
                                    if(columnHeading.equals("")){
                                        columnHeading = columnHeading + s1[1];
                                        
                                    }
                                    else{
                                        columnHeading = columnHeading + " ";
                                        columnHeading = columnHeading + s1[1];
                                    }
                                }

                            }
   
                        }
                    }
                    else if(l==1){
                        firstTableName = s[1];
                     
                        for(m=0;m<noOfTables;m++){

                            if(table[m].name.equals(firstTableName)){
                                
                                firstTable = table[m];
                                break;

                            }
                        }
                      
                        
                    }
                    else if(l==2){
                        secondTableName = s[1];

                        for(m=0;m<noOfTables;m++){

                            if(table[m].name.equals(secondTableName)){

                                secondTable = table[m];
                                break;                         

                            }
                        }
                             
   
                    }
                    else if(l==3){
                        
                        s1 = s[1].split("\\.");
                        columnName = s1[1];
     
                        for(m=0;m<noOfTables;m++){
                            
                            index = findIndex(table[m].columnName,columnName);
                            if(index!=-1){
                                table[m].columnNoToMatch = index;
                                break;

                            }
                        }
                        
                        s1 = s[2].split("\\.");
                        columnName = s1[1];
                        
                        for(m=0;m<noOfTables;m++){
                            index = findIndex(table[m].columnName,columnName);
                            if(index!=-1){
                                table[m].columnNoToMatch = index;
                                break;

                            }
                        }
                        

                    }
                    else if(l==4){
                        
                        if(allColumnPrintFlag != true){
                            printLine(columnHeading);
                        }
                        
                        else{
                            columnHeading = "";
                            for(mm=0;mm<firstTable.columnName.length;mm++){
                                if(firstTable.columnName[mm]!=null){
                                    if(columnHeading.equals("")){
                                        columnHeading = columnHeading + firstTable.columnName[mm];
                                    }
                                    else{
                                        columnHeading = columnHeading + " " + firstTable.columnName[mm];
                                    }   
                                }
                            }
                            for(mm=0;mm<secondTable.columnName.length;mm++){
                                if(secondTable.columnName[mm]!=null){
                                    columnHeading = columnHeading + " " + secondTable.columnName[mm];
                                }
                            }
                            printLine(columnHeading);
                            
                        }                        

                       
                        columnNoToMatch1 = firstTable.columnNoToMatch;
                        columnNoToMatch2 = secondTable.columnNoToMatch;
                        
                        if(allColumnPrintFlag!=true){
                            columnOrder = splitMethod(columnHeading);
                            for(r=0;r<firstTable.noOfRecords;r++){
                  
                                for(r1=0;r1<secondTable.noOfRecords;r1++){
                                    string = "";
                                    if(firstTable.columnValues[r][columnNoToMatch1].equals(secondTable.columnValues[r1][columnNoToMatch2])){
                                       
                                        for(c1=0;c1<columnOrder.length;c1++){
                                            if(columnOrder[c1]!=null){
                                                index = findIndex(firstTable.columnName,columnOrder[c1]);
                                                index2 = findIndex(secondTable.columnName,columnOrder[c1]);
                                                if(index==-1 && index2==-1){

                                                }
                                                else{
                                                    
                                                    if(index!=-1){
                                                        if(string.equals("")){
                                                            string = string + firstTable.columnValues[r][index];
                                                        }
                                                        else{
                                                           string = string + " " + firstTable.columnValues[r][index];
                                                        }
                                                    }
                                                    else{
                                                        if(string.equals("")){
                                                            string = string + secondTable.columnValues[r1][index2];
                                                        }
                                                        else{
                                                           string = string + " " + secondTable.columnValues[r1][index2];
                                                        }
                                                    }
                                                }
                                            }
                                          
                                        }

                                        rowsOfTable.add(string);


                                    }
                                }
                            }
                        }
                        else{
                            for(r=0;r<firstTable.noOfRecords;r++){
                  
                                for(r1=0;r1<secondTable.noOfRecords;r1++){
                                    string = "";
                                    if(firstTable.columnValues[r][columnNoToMatch1].equals(secondTable.columnValues[r1][columnNoToMatch2])){

                                        for(c1=0;c1<firstTable.noOfColumns;c1++){

                                            if(string.equals("")){
                                                string = string + firstTable.columnValues[r][c1];
                                            }
                                            else{
                                               string = string + " " + firstTable.columnValues[r][c1];
                                            }
                                        }
                                        for(c1=0;c1<secondTable.noOfColumns;c1++){

                                            string = string + " " + secondTable.columnValues[r1][c1];

                                        }

                                        rowsOfTable.add(string);


                                    }
                                }
                            }
                        }
                        
                        Collections.sort(rowsOfTable);
                        for(String counter: rowsOfTable){
                            printLine(counter);
                        }
                        
                    }
                    
                }
                //readLine();
                System.out.println();
            }
          

        }
        
    }
    public String[] splitMethod(String string){
        
        String[] splitString;
        splitString = string.replaceAll("\\s+", " ").trim().split(" ");
        return splitString;
    }
    
    public int findIndex(String[] stringArray, String str){
        
        int findIndx = -1;
        int loop;
        for(loop=0;loop<stringArray.length;loop++){
            if(stringArray[loop].equals(str)){
                findIndx = loop;
                break;
            }
        }
        //printLine(findIndx);
        return findIndx;
    }
    
   
}
