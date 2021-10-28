package Banca;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Controller {
    @FXML
    private TextField cnpTextField;
    @FXML
    private TextField modificareSold;

    @FXML
    private Label contLabel;

    @FXML
    private Button loginCont;
    @FXML
    private Button creareCont;
    @FXML
    private Button lichidareCont;
    @FXML
    private Button changeCNP;
    @FXML
    private Button depozitareCont;
    @FXML
    private Button retragereCont;
    @FXML
    private Button interogareCont;

    @FXML
    private TextArea informatiiCont;

    @FXML
    private ChoiceBox<String> moneda;

    @FXML
    public void initialize() {
        moneda.getItems().add("EURO");
        moneda.getItems().add("RON");
        moneda.setValue("EURO");

        // CNP: numeric only
        cnpTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    cnpTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        // Depozitare/Retragere: numeric only
        modificareSold.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    cnpTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    public void disableOptions() {
        lichidareCont.setDisable(true);
        changeCNP.setDisable(true);
        moneda.setDisable(true);
        depozitareCont.setDisable(true);
        retragereCont.setDisable(true);
        interogareCont.setDisable(true);
        modificareSold.setDisable(true);
        informatiiCont.setText("");
        modificareSold.setText("");
    }

    public void enableOptions() {
        lichidareCont.setDisable(false);
        changeCNP.setDisable(false);
        moneda.setDisable(false);
        depozitareCont.setDisable(false);
        retragereCont.setDisable(false);
        interogareCont.setDisable(false);
        modificareSold.setDisable(false);
    }

    public void loginContButton(ActionEvent event) throws FileNotFoundException, IOException {
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
                    cnpTextField.setEditable(false);
                    loginCont.setDisable(true);
                    creareCont.setDisable(true);
                    enableOptions();
                    contLabel.setText("Logged in!");

                    break;
                } else {
                    creareCont.setDisable(false);
                    contLabel.setText("Contul nu este inregistrat.");
                }
            }
            // System.out.println(workbook.getSheetAt(0).getRow(2).getCell(2));
            workbook.close();
            file.close();
        } else {
            cnpTextField.setEditable(true);
            loginCont.setDisable(false);
            contLabel.setText("CNP-ul trebuie sa contina 13 caractere!");
        }
    }

    public void creareContButton(ActionEvent event) throws FileNotFoundException, IOException {
        if (cnpTextField.getLength() == 13) {
            FileInputStream file = new FileInputStream(
                    new File("D:/SCHOOL/testareSoftware/testare sofware/src/Banca/Informatii.xlsx"));
            Workbook workbook = new XSSFWorkbook(file);
            DataFormatter formatter = new DataFormatter();
            Sheet sheet = workbook.getSheetAt(0);
            Row row;
            int lastRow = sheet.getLastRowNum();
            String cnpValue = "/" + cnpTextField.getText() + "/";

            for (int i = 1; i <= lastRow; i++) {
                row = sheet.getRow(i);
                String CNP = formatter.formatCellValue(row.getCell(0));

                if (CNP.equals(cnpValue)) {
                    contLabel.setText("Contul este deja inregistrat.");

                    break;
                } else {
                    String basicSold = "/1000/";
                    Row newRow = sheet.createRow(lastRow + 1);
                    Cell cell = newRow.createCell(0);
                    cell.setCellValue(cnpValue);
                    Cell cell1 = newRow.createCell(1);
                    cell1.setCellValue(basicSold);
                    Cell cell2 = newRow.createCell(2);
                    cell2.setCellValue(basicSold);
                    // Valoare monitorizare: YES/NO
                    Cell cell3 = newRow.createCell(3);
                    cell3.setCellValue("NO");

                    FileOutputStream outputStream = new FileOutputStream(
                            new File("D:/SCHOOL/testareSoftware/testare sofware/src/Banca/Informatii.xlsx"));
                    workbook.write(outputStream);
                    outputStream.close();

                    contLabel.setText("Contul a fost inregistrat.");
                }
            }
            workbook.close();
            file.close();
        } else {
            cnpTextField.setEditable(true);
            loginCont.setDisable(false);
            contLabel.setText("CNP-ul trebuie sa contina 13 caractere!");
        }
    }

    public void lichidareContButton(ActionEvent event) throws FileNotFoundException, IOException {
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
                String soldEuro = formatter.formatCellValue(row.getCell(1));
                String soldRon = formatter.formatCellValue(row.getCell(2));
                String monitorizat = formatter.formatCellValue(row.getCell(3));

                if (soldEuro.equals("/0/") && soldRon.equals("/0/")) {
                    if (monitorizat.equals("YES")) {
                        Sheet sheet2 = workbook.getSheetAt(1);
                        int lastRow2 = sheet2.getLastRowNum();
                        Row newRow2 = sheet2.createRow(lastRow2 + 1);

                        Cell cellCNP = newRow2.createCell(0);
                        cellCNP.setCellValue(cnpValue);
                        Cell cellLichidat = newRow2.createCell(3);
                        cellLichidat.setCellValue("YES");

                        FileOutputStream outputStream = new FileOutputStream(
                                new File("D:/SCHOOL/testareSoftware/testare sofware/src/Banca/Informatii.xlsx"));
                        workbook.write(outputStream);
                        outputStream.close();
                    }

                    sheet.shiftRows(row.getRowNum() + 1, sheet.getLastRowNum() + 1, -1);
                    i--;

                    FileOutputStream outputStream = new FileOutputStream(
                            new File("D:/SCHOOL/testareSoftware/testare sofware/src/Banca/Informatii.xlsx"));
                    workbook.write(outputStream);
                    outputStream.close();

                    contLabel.setText("Contul a fost sters!");
                    cnpTextField.setText("");
                    informatiiCont.setText("");
                    cnpTextField.setEditable(true);
                    loginCont.setDisable(false);
                    creareCont.setDisable(false);
                    disableOptions();

                    break;
                } else {
                    contLabel.setText("Ambele solduri trebuie sa fie 0.");
                }
            }
        }
        workbook.close();
        file.close();
    }

    public void changeCNPButton(ActionEvent event) {
        cnpTextField.setText("");
        informatiiCont.setText("");
        cnpTextField.setEditable(true);
        loginCont.setDisable(false);
        creareCont.setDisable(false);
        disableOptions();
    }

    public void depozitareContButton(ActionEvent event) throws FileNotFoundException, IOException {
        FileInputStream file = new FileInputStream(
                new File("D:/SCHOOL/testareSoftware/testare sofware/src/Banca/Informatii.xlsx"));
        Workbook workbook = new XSSFWorkbook(file);
        DataFormatter formatter = new DataFormatter();
        Sheet sheet = workbook.getSheetAt(0);
        Row row;
        String cnpValue = "/" + cnpTextField.getText() + "/";
        int sumaDepusa = Integer.valueOf(modificareSold.getText());
        modificareSold.setDisable(true);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            String CNP = formatter.formatCellValue(row.getCell(0));
            String monitorizat = formatter.formatCellValue(row.getCell(3));

            if (CNP.equals(cnpValue)) {
                if (moneda.getSelectionModel().getSelectedItem().equals("EURO")) {
                    String soldEuroString = removeFirstandLast(formatter.formatCellValue(row.getCell(1)));
                    int soldEuro = Integer.valueOf(soldEuroString);

                    String newSold = Integer.toString(soldEuro + sumaDepusa);
                    String writeNewSold = "/" + newSold + "/";

                    Cell cell = row.getCell(1);
                    cell.setCellValue(writeNewSold);

                    if (monitorizat.equals("YES")) {
                        Sheet sheet2 = workbook.getSheetAt(1);
                        int lastRow2 = sheet2.getLastRowNum();
                        Row newRow2 = sheet2.createRow(lastRow2 + 1);

                        Cell cellCNP = newRow2.createCell(0);
                        cellCNP.setCellValue(cnpValue);
                        Cell cellEURO = newRow2.createCell(1);
                        cellEURO.setCellValue("YES");
                    }

                } else {
                    String soldRonString = removeFirstandLast(formatter.formatCellValue(row.getCell(2)));
                    int soldRon = Integer.valueOf(soldRonString);

                    String newSold = Integer.toString(soldRon + sumaDepusa);
                    String writeNewSold = "/" + newSold + "/";

                    Cell cell = row.getCell(2);
                    cell.setCellValue(writeNewSold);

                    if (monitorizat.equals("YES")) {
                        Sheet sheet2 = workbook.getSheetAt(1);
                        int lastRow2 = sheet2.getLastRowNum();
                        Row newRow2 = sheet2.createRow(lastRow2 + 1);

                        Cell cellCNP = newRow2.createCell(0);
                        cellCNP.setCellValue(cnpValue);
                        Cell cellRON = newRow2.createCell(2);
                        cellRON.setCellValue("YES");
                    }
                }

                FileOutputStream outputStream = new FileOutputStream(
                        new File("D:/SCHOOL/testareSoftware/testare sofware/src/Banca/Informatii.xlsx"));
                workbook.write(outputStream);
                outputStream.close();

                contLabel.setText("Soldul a fost modificat.");

                break;
            }
        }
        workbook.close();
        file.close();
        modificareSold.setDisable(false);
        modificareSold.setText("");
    }

    public void retragereContButton(ActionEvent event) throws FileNotFoundException, IOException {
        FileInputStream file = new FileInputStream(
                new File("D:/SCHOOL/testareSoftware/testare sofware/src/Banca/Informatii.xlsx"));
        Workbook workbook = new XSSFWorkbook(file);
        DataFormatter formatter = new DataFormatter();
        Sheet sheet = workbook.getSheetAt(0);
        Row row;
        String cnpValue = "/" + cnpTextField.getText() + "/";
        int sumaRetrasa = Integer.valueOf(modificareSold.getText());
        modificareSold.setDisable(true);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            String CNP = formatter.formatCellValue(row.getCell(0));
            String monitorizat = formatter.formatCellValue(row.getCell(3));

            if (CNP.equals(cnpValue)) {
                if (moneda.getSelectionModel().getSelectedItem().equals("EURO")) {
                    String soldEuroString = removeFirstandLast(formatter.formatCellValue(row.getCell(1)));
                    int soldEuro = Integer.valueOf(soldEuroString);

                    if (soldEuro < sumaRetrasa) {
                        contLabel.setText("Nu puteti retrage aceasta suma!");
                        break;
                    } else {
                        String newSold = Integer.toString(soldEuro - sumaRetrasa);
                        String writeNewSold = "/" + newSold + "/";

                        Cell cell = row.getCell(1);
                        cell.setCellValue(writeNewSold);

                        if (monitorizat.equals("YES")) {
                            Sheet sheet2 = workbook.getSheetAt(1);
                            int lastRow2 = sheet2.getLastRowNum();
                            Row newRow2 = sheet2.createRow(lastRow2 + 1);

                            Cell cellCNP = newRow2.createCell(0);
                            cellCNP.setCellValue(cnpValue);
                            Cell cellEURO = newRow2.createCell(1);
                            cellEURO.setCellValue("YES");
                        }
                    }

                } else {
                    String soldRonString = removeFirstandLast(formatter.formatCellValue(row.getCell(2)));
                    int soldRon = Integer.valueOf(soldRonString);

                    if (soldRon < sumaRetrasa) {
                        contLabel.setText("Nu puteti retrage aceasta suma!");
                        break;
                    } else {
                        String newSold = Integer.toString(soldRon - sumaRetrasa);
                        String writeNewSold = "/" + newSold + "/";

                        Cell cell = row.getCell(2);
                        cell.setCellValue(writeNewSold);

                        if (monitorizat.equals("YES")) {
                            Sheet sheet2 = workbook.getSheetAt(1);
                            int lastRow2 = sheet2.getLastRowNum();
                            Row newRow2 = sheet2.createRow(lastRow2 + 1);

                            Cell cellCNP = newRow2.createCell(0);
                            cellCNP.setCellValue(cnpValue);
                            Cell cellRON = newRow2.createCell(2);
                            cellRON.setCellValue("YES");
                        }
                    }
                }

                FileOutputStream outputStream = new FileOutputStream(
                        new File("D:/SCHOOL/testareSoftware/testare sofware/src/Banca/Informatii.xlsx"));
                workbook.write(outputStream);
                outputStream.close();

                contLabel.setText("Soldul a fost modificat.");

                break;
            }
        }

        workbook.close();
        file.close();
        modificareSold.setDisable(false);
        modificareSold.setText("");
    }

    public void interogareCont(ActionEvent event) throws FileNotFoundException, IOException {
        informatiiCont.setText("");
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
                String euroString = formatter.formatCellValue(row.getCell(1));
                String ronString = formatter.formatCellValue(row.getCell(2));
                informatiiCont.appendText("------------------\n");
                informatiiCont.appendText("Sold EURO: " + euroString + "\n");
                informatiiCont.appendText("Sold RON: " + ronString + "\n");
                informatiiCont.appendText("------------------");
                break;
            }
        }
        workbook.close();
        file.close();
    }

    public static String removeFirstandLast(String str) {
        StringBuilder sb = new StringBuilder(str);
        // Removing the last character of a string
        sb.deleteCharAt(str.length() - 1);
        // Removing the first character of a string
        sb.deleteCharAt(0);

        return sb.toString();
    }
}
