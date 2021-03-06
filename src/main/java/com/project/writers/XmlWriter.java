package com.project.writers;

import com.project.decoders.ImageDecoder;
import com.project.models.InvoiceLine;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

public class XmlWriter implements IWriter {

    private Document document;

    @Override
    public void write(List<InvoiceLine> input, String headers, String destination) {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;

        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();

            document = documentBuilder.newDocument();
            Element rootElement = document.createElement("root");
            document.appendChild(rootElement);

            Map<String, List<InvoiceLine>> groupedLines = input.stream()
                    .collect(Collectors.groupingBy(InvoiceLine::getBuyer));

            LinkedList<String> headersList = splitHeaders(headers);
            headersList.remove("invoice_image");
            for (String key : groupedLines.keySet()) {
                Element buyerParent = document.createElement(key.replace(" ", ""));
                rootElement.appendChild(buyerParent);
                for (InvoiceLine line : groupedLines.get(key)) {
                    Element invoiceNode = document.createElement("invoice");
                    buyerParent.appendChild(invoiceNode);
                    Iterator<String> headersIterator = headersList.listIterator();
                    headersIterator.next();
                    addNode(invoiceNode, headersIterator.next(), line.getImageName());
                    addNode(invoiceNode, headersIterator.next(), line.getInvoiceDueDate());
                    addNode(invoiceNode, headersIterator.next(), line.getInvoiceNumber());
                    addNode(invoiceNode, headersIterator.next(), line.getInvoiceAmount());
                    addNode(invoiceNode, headersIterator.next(), line.getInvoiceCurrency());
                    addNode(invoiceNode, headersIterator.next(), line.getInvoiceStatus());
                    addNode(invoiceNode, headersIterator.next(), line.getSupplier());
                }
            }
            try (FileOutputStream outputStream = new FileOutputStream(destination + "output.xml")) {
                writeXmlFile(outputStream);
            }
            extractImages(input, destination);
        } catch (ParserConfigurationException | TransformerException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void writeXmlFile(OutputStream output) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(output);

        transformer.transform(source, result);
    }

    private void extractImages(List<InvoiceLine> invoiceLines, String destination) {
        for (InvoiceLine line : invoiceLines) {
            if (line.getInvoiceImage() != null && !line.getInvoiceImage().isBlank()) {
                ImageDecoder.decode(line.getInvoiceImage(), destination, line.getImageName());
            }
        }
    }

    private LinkedList<String> splitHeaders(String headers) {
        List<String> splitHeaders = Arrays.asList(headers.split(","));
        return new LinkedList<>(splitHeaders);
    }

    private void addNode(Element parent, String nodeName, String nodeValue) {
        Element newElement = document.createElement(nodeName);
        newElement.setTextContent(nodeValue);
        parent.appendChild(newElement);
    }
}
