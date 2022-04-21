package com.shopme.shopmebackend.admin.user.exporter;

import com.shopme.shopmecommon.entity.User;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserExcelExporter extends AbstractExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public UserExcelExporter() {
        workbook = new XSSFWorkbook();
    }

    public void export(List<User> userList, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "application/octet-stream", ".xlsx");

        writeHeaderLine();
        writeDataLines(userList);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public void writeHeaderLine() {
        sheet = workbook.createSheet("Users");
        XSSFRow row = sheet.createRow(0);

        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);

        createCell(row, 0, "User ID", style);
        createCell(row, 1, "Email", style);
        createCell(row, 2, "First Name", style);
        createCell(row, 3, "Last Name", style);
        createCell(row, 4, "Roles", style);
        createCell(row, 5, "Enabled", style);

    }

    public void createCell(XSSFRow row, int indexColumn, Object value, CellStyle style) {
        XSSFCell cell = row.createCell(indexColumn);
        sheet.autoSizeColumn(indexColumn);

        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }

        cell.setCellStyle(style);
    }

    private void writeDataLines(List<User> userList) {
        int rowIndex = 1;

        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        style.setFont(font);

        for (User user : userList) {
            XSSFRow row = sheet.createRow(rowIndex++);
            int columnIndex = 0;

            createCell(row, columnIndex++, user.getId(), style);
            createCell(row, columnIndex++, user.getEmail(), style);
            createCell(row, columnIndex++, user.getFirstName(), style);
            createCell(row, columnIndex++, user.getLastName(), style);
            createCell(row, columnIndex++, user.getRoles().toString(), style);
            createCell(row, columnIndex++, user.getEnabled(), style);

        }
    }
}
