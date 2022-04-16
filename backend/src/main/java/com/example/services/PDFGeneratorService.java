package com.example.services;


import com.example.models.Specialization;
import com.example.models.Student;
import com.example.payload.requests.PdfDTO;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class PDFGeneratorService implements IDocumentGenerator{

    public void export(HttpServletResponse response, PdfDTO dto) throws IOException {

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
        Paragraph paragraph = new Paragraph("Contract", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);


        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);
        Student student = dto.getStudent();
        Specialization specialization = dto.getSpecialization();
        Integer semester = dto.getSemester();
        Paragraph paragraph2 = new Paragraph("Student " + student.getAccount().getFirstName() + " " + student.getAccount().getLastName() + " is enrolling into " + specialization.getName() + " for semester " + semester , fontParagraph);
        paragraph2.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(paragraph);
        document.add(paragraph2);
        document.close();
    }

}
