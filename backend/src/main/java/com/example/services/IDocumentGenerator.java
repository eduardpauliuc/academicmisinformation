package com.example.services;

import com.example.payload.requests.PdfDTO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface IDocumentGenerator {
    void export(HttpServletResponse response, PdfDTO dto) throws IOException;
}
