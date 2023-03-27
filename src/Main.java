import model.Request;
import model.RequestType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class Main {


    public static void main(String[] args) throws IOException{
        System.out.println("Welcome to FYPMS! Please wait a few seconds for initialization");\
        System.out.println("Loading Supervisor List.....");
        File facultyFile = new File("src\\faculty_list.xlsx");
        FileInputStream fis = new FileInputStream(facultyFile);
        
    }

}