package Banca;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ControllerFisc {
    @FXML
    private TextField cnpTextField;

    @FXML
    private Label contLabel;

    @FXML
    private Button check;
    @FXML
    private Button changeCNP;
    @FXML
    private Button conturiMonitorizate;
    @FXML
    private Button incepeMonitorizare;
    @FXML
    private Button incetareMonitorizare;

    @FXML
    private TextArea modificareSold;

    @FXML
    public void initialize() throws FileNotFoundException, IOException {
        // CNP: numeric only
        cnpTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    cnpTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        // Verifica modificari sold clienti monitorizati
        FileInputStream file = new FileInputStream(
                new File("D:/SCHOOL/testareSoftware/testare sofware/src/Banca/Informatii.xlsx"));
        Workbook workbook = new XSSFWorkbook(file);
        DataFormatter formatter = new DataFormatter();
        Sheet sheet = workbook.getSheetAt(1);
        Row row;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            String CNP = formatter.formatCellValue(row.getCell(0));
            String soldEuro = formatter.formatCellValue(row.getCell(1));
            String soldRon = formatter.formatCellValue(row.getCell(2));
            String lichidat = formatter.formatCellValue(row.getCell(3));

            if (soldEuro.equals("YES")) {
                modificareSold.appendText("Contul " + CNP + " a modificat sold EURO.\n");
            } 
            
            if (soldRon.equals("YES")) {
                modificareSold.appendText("Contul " + CNP + " a modificat sold RON.\n");
            }
            
            if (lichidat.equals("YES")) {
                modificareSold.appendText("Contul " + CNP + " a fost lichidat.\n");
            }

            sheet.shiftRows(row.getRowNum() + 1, sheet.getLastRowNum() + 1, -1);
            i--;

            FileOutputStream outputStream = new FileOutputStream(
                    new File("D:/SCHOOL/testareSoftware/testare sofware/src/Banca/Informatii.xlsx"));
            workbook.write(outputStream);
            outputStream.close();
        }
        workbook.close();
        file.close();
    }

    public void checkButton(ActionEvent event) throws FileNotFoundException, IOException {
        if (cnpTextField.getLength() == 13) {
            FileInputStream file = new FileInputStream(
                    new File("D:/SCHOOL/testareSoftware/testare sofware/src/Banca/Informatii.xlsx"));
            Workbook workbook = new XSSFWorkbook(file);
            DataFormatter formatter = new DataFormatter();
            Sheet sheet = workbook.getSheetAt(0);
            Row row;
            String cnpValue = "/" + cnpTextField.getText() + "/";

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                row = sheet.getRow(i);
                String CNP = formatter.formatCellValue(row.getCell(0));

                if (CNP.equals(cnpValue)) {
                    incepeMonitorizare.setDisable(false);
                    incetareMonitorizare.setDisable(false);
                    cnpTextField.setEditable(false);
                    changeCNP.setDisable(false);
                    check.setDisable(true);
                    contLabel.setText("Contul este inregistrat.");

                    break;
                } else {
                    incepeMonitorizare.setDisable(true);
                    incetareMonitorizare.setDisable(true);
                    cnpTextField.setEditable(true);
                    changeCNP.setDisable(true);
                    check.setDisable(false);
                    contLabel.setText("Contul nu este inregistrat.");
                }
            }

            workbook.close();
            file.close();
        } else {
            contLabel.setText("CNP-ul trebuie sa contina 13 caractere!");
        }
    }

    public void incepeMonitorizareButton(ActionEvent event) throws FileNotFoundException, IOException {
        FileInputStream file = new FileInputStream(
                new File("D:/SCHOOL/testareSoftware/testare sofware/src/Banca/Informatii.xlsx"));
        Workbook workbook = new XSSFWorkbook(file);
        DataFormatter formatter = new DataFormatter();
        Sheet sheet = workbook.getSheetAt(0);
        Row row;
        String cnpValue = "/" + cnpTextField.getText() + "/";

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            String CNP = formatter.formatCellValue(row.getCell(0));
            String monitorizatString = formatter.formatCellValue(row.getCell(3));

            if (CNP.equals(cnpValue) && monitorizatString.equals("NO")) {
                Cell cell = row.getCell(3);
                cell.setCellValue("YES");

                FileOutputStream outputStream = new FileOutputStream(
                        new File("D:/SCHOOL/testareSoftware/testare sofware/src/Banca/Informatii.xlsx"));
                workbook.write(outputStream);
                outputStream.close();

                contLabel.setText("Contul este acum monitorizat.");

                break;
            } else {
                contLabel.setText("Contul este deja monitorizat.");
            }
        }

        workbook.close();
        file.close();
    }

    public void incetareMonitorizareButton(ActionEvent event) throws FileNotFoundException, IOException {
        FileInputStream file = new FileInputStream(
                new File("D:/SCHOOL/testareSoftware/testare sofware/src/Banca/Informatii.xlsx"));
        Workbook workbook = new XSSFWorkbook(file);
        DataFormatter formatter = new DataFormatter();
        Sheet sheet = workbook.getSheetAt(0);
        Row row;
        String cnpValue = "/" + cnpTextField.getText() + "/";

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            String CNP = formatter.formatCellValue(row.getCell(0));
            String monitorizatString = formatter.formatCellValue(row.getCell(3));

            if (CNP.equals(cnpValue) && monitorizatString.equals("YES")) {
                Cell cell = row.getCell(3);
                cell.setCellValue("NO");

                FileOutputStream outputStream = new FileOutputStream(
                        new File("D:/SCHOOL/testareSoftware/testare sofware/src/Banca/Informatii.xlsx"));
                workbook.write(outputStream);
                outputStream.close();

                contLabel.setText("Contul nu mai este monitorizat.");

                break;
            } else {
                contLabel.setText("Contul nu este monitorizat.");
            }
        }

        workbook.close();
        file.close();
    }

    public void conturiMonitorizateButton(ActionEvent event) throws FileNotFoundException, IOException {
        modificareSold.setText("");
        FileInputStream file = new FileInputStream(
                new File("D:/SCHOOL/testareSoftware/testare sofware/src/Banca/Informatii.xlsx"));
        Workbook workbook = new XSSFWorkbook(file);
        DataFormatter formatter = new DataFormatter();
        Sheet sheet = workbook.getSheetAt(0);
        Row row;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            String monitorizatString = formatter.formatCellValue(row.getCell(3));

            if (monitorizatString.equals("YES")) {
                String CNP = formatter.formatCellValue(row.getCell(0));

                modificareSold.appendText("Contul " + CNP + " este monitorizat!\n");
            }
        }

        workbook.close();
        file.close();
    }

    public void changeCNPButton(ActionEvent event) {
        cnpTextField.setText("");
        cnpTextField.setEditable(true);
        incepeMonitorizare.setDisable(true);
        incetareMonitorizare.setDisable(true);
        check.setDisable(false);
        changeCNP.setDisable(true);
    }
}
