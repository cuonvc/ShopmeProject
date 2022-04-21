package com.shopme.shopmebackend.admin.user.exporter;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.shopme.shopmecommon.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class UserPdfExporter extends AbstractExporter {

    public void export(List<User> userList, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "application/pdf", ".pdf");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.GRAY);
        Paragraph paragraph = new Paragraph("List of users", font);
        paragraph.setAlignment(paragraph.ALIGN_CENTER);
        document.add(paragraph);

        PdfPTable table = new PdfPTable(6); //6column
        table.setWidthPercentage(100f);
        table.setSpacingBefore(50);
        table.setWidths(new float[] {1.0f, 4.5f, 3.0f, 3.0f, 3.0f, 2.0f});

        wirteTableHeader(table);
        writeTableData(table, userList);

        document.add(table);

        document.close();

    }

    private void writeTableData(PdfPTable table, List<User> userList) {
        for (User user : userList) {
            table.addCell(String.valueOf(user.getId()));
            table.addCell(user.getEmail());
            table.addCell(user.getFirstName());
            table.addCell(user.getLastName());
            table.addCell(user.getRoles().toString());
            table.addCell(String.valueOf(user.getEnabled()));
        }
    }

    private void wirteTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.lightGray);
        cell.setPadding(5);   //5pixel

        cell.setPhrase(new Phrase("ID"));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Email"));
        table.addCell(cell);
        cell.setPhrase(new Phrase("First Name"));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Last Name"));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Roles"));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Enabled"));
        table.addCell(cell);



    }
}
