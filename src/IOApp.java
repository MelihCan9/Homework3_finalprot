import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class IOApp {
        static String filename = "transactions.txt";
        static int limitRecord = 100;
        static Record[] records;
        static int lastIndex;                           //Do not change

        static class Record {
                String name;
                Integer price, number;
        }


        public static void main(String[] args) {
                initialProcess();                       // Do not change

                Scanner scanner = new Scanner(System.in);
                String transactions = "-------------------MENU-------------------\n" +
                        "-----------1-ADD RECORD--------------\n" +
                        "-----------2-REMOVE RECORD-----------\n" +
                        "-----------3-SEARCH RECORD-----------\n" +
                        "-----------4-LIST ALL----------\n" +
                        "-----------5-DELETE ALL---------\n" +
                        "-----------6-CLOSE---------------";
                while (true) {

                        System.out.println(transactions);
                        System.out.print("Please make a choice: ");
                        String choice = scanner.nextLine();

                        switch (choice) {
                                case "6" -> {
                                        System.out.println("Exiting the system...");
                                        return;
                                }

                                case "1" -> {
                                        System.out.print("Please enter Name: ");
                                        String name = scanner.nextLine();

                                        System.out.print("Please enter Price: ");
                                        int price = scanner.nextInt();

                                        System.out.print("Please enter Number: ");
                                        int number = scanner.nextInt();
                                        scanner.nextLine();

                                        addRecord(name, price, number);
                                        update();
                                        System.out.println("Record is added.");
                                }

                                case "2" -> {
                                        System.out.print("Please enter Name: ");
                                        String name = scanner.nextLine();

                                        if(removeRecord(name)){
                                                System.out.println("Record is removed.");
                                                lastIndex--;
                                                update();
                                        }
                                        else{
                                                System.out.println("Record is not available.");
                                        }
                                }
                                case "3" -> {
                                        System.out.print("Please enter Name: ");
                                        String name = scanner.nextLine();

                                        searchRecord(name);

                                }
                                case "4" -> listRecord();
                                case "5" -> {
                                        System.out.print("Are you sure? y/n ");
                                        String answer = scanner.nextLine();
                                        if (answer.equals("y")) {
                                                deleteall();
                                                update();
                                                System.out.println("All records are deleted.");
                                        } else if (answer.equals("n")) {
                                                System.out.println("Records not deleted.");
                                        } else {
                                                System.out.println("Invalid answer.");
                                        }
                                }
                                default -> System.out.println("Invalid transaction. Please try again...");
                        }
                }
        }


        private static void listRecord() {
                if(lastIndex == 0){
                        System.out.println("There are no records...");
                }

                for(int i = 0; i<lastIndex; i++){
                        System.out.println(records[i].name + "\t" + records[i].price + "\t" + records[i].number + "\n");
                }
        }


        private static void addRecord(String name, Integer price, Integer number) {
                Record record = new Record();
                record.name = name;
                record.price = price;
                record.number = number;

                records[lastIndex] = record;
                lastIndex++;
        }


        private static void update() {
                FileOutputStream fos = null;
                File file = new File("transactions.txt");

                try{
                        fos = new FileOutputStream(file);
                        for(int i = 0; i<lastIndex; i++){

                                String s = records[i].name + "\t" + records[i].price + "\t" + records[i].number + "\n";
                                byte []s_array = s.getBytes();

                                fos.write(s_array);
                        }
                }
                catch (FileNotFoundException ex){
                        System.err.println("FileNotFoundException error occurred.");
                }
                catch(IOException ex){
                        System.err.println("IOException error occurred.");
                }
                finally {
                        try{
                                if(fos != null){
                                        fos.close();
                                }
                        }
                        catch (IOException ex){
                                System.out.println("IOException error occurred.");
                        }
                }
        }


        private static boolean removeRecord(String name) {
                boolean control = false;
                int i;
                for(i = 0; i<lastIndex; i++){
                        if(records[i].name.equals(name)){
                                control = true;
                                break;
                        }
                }
                int j;
                for(j = i; j<lastIndex -1; j++){
                        records[j]=records[j+1];
                }
                records[j]=new Record();
                return control;
        }


        private static void searchRecord(String name) {
                boolean control = false;
                int i;
                for(i =0; i<lastIndex; i++){

                        if(records[i].name.equals(name)){
                                control = true;
                                break;
                        }
                }
                if(control){
                        System.out.println(records[i].name + "\t" + records[i].price + "\t" + records[i].number + "\n");

                }
                else{
                        System.out.println("Record is not available.");
                }
        }

        private static void deleteall() {
                records = new Record[limitRecord];
                lastIndex=0;
        }

        // initialProcess() method must not be changed.
        private static void initialProcess() {
                records = new Record[limitRecord];
                for (int i = 0; i < limitRecord; i++) {
                        records[i] = new Record();
                }
                try {
                        Reader reader = new InputStreamReader(new FileInputStream(filename), "Windows-1254");
                        BufferedReader br = new BufferedReader(reader);
                        String strLine;
                        int i = 0;
                        while ((strLine = br.readLine()) != null) {
                                StringTokenizer tokens = new StringTokenizer(strLine, "\t");
                                String[] t = new String[3];
                                int j = 0;
                                while (tokens.hasMoreTokens()) {
                                        t[j] = tokens.nextToken();
                                        j++;
                                }
                                records[i].name = t[0];
                                records[i].price = Integer.valueOf(t[1]);
                                records[i].number = Integer.valueOf(t[2]);
                                i++;
                        }
                        lastIndex = i;
                        reader.close();
                } catch (Exception e) {
                        System.err.println("Error: " + e.getMessage());
                }
        }
}