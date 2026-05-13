package com.stock_tracker.stock_tracker_ost.service;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.stock_tracker.stock_tracker_ost.exceptions.SaleNotFoundException;
import com.stock_tracker.stock_tracker_ost.model.CompanyConfig;
import com.stock_tracker.stock_tracker_ost.model.Contractor;
import com.stock_tracker.stock_tracker_ost.model.Sale;
import com.stock_tracker.stock_tracker_ost.model.SaleItem;
import com.stock_tracker.stock_tracker_ost.repository.CompanyConfigRepository;
import com.stock_tracker.stock_tracker_ost.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private static final DateTimeFormatter DATE_ONLY_FMT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private final SaleRepository saleRepository;
    private final CompanyConfigRepository companyConfigRepository;

    public PdfService(SaleRepository saleRepository, CompanyConfigRepository companyConfigRepository) {
        this.saleRepository = saleRepository;
        this.companyConfigRepository = companyConfigRepository;
    }

    private PdfFont loadFont() {
        try {
            return PdfFontFactory.createFont("C:/Windows/Fonts/arial.ttf",
                    PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
        } catch (Exception e) {
            try {
                return PdfFontFactory.createFont(StandardFonts.HELVETICA);
            } catch (Exception ex) {
                throw new RuntimeException("Blad ladowania czcionki", ex);
            }
        }
    }

    public byte[] generateReceipt(Long saleId) {
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new SaleNotFoundException(saleId));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (Document document = new Document(new PdfDocument(new PdfWriter(baos)), PageSize.A5)) {
            PdfFont font = loadFont();

            document.add(new Paragraph("PARAGON FISKALNY")
                    .setFont(font).setFontSize(16).setBold()
                    .setTextAlignment(TextAlignment.CENTER));

            companyConfigRepository.findById(1L).ifPresent(company -> {
                if (company.getName() != null)
                    document.add(new Paragraph(company.getName())
                            .setFont(font).setFontSize(11).setTextAlignment(TextAlignment.CENTER));
                if (company.getAddress() != null && company.getCity() != null)
                    document.add(new Paragraph(company.getAddress() + ", " + company.getCity())
                            .setFont(font).setFontSize(9).setTextAlignment(TextAlignment.CENTER));
                if (company.getNip() != null)
                    document.add(new Paragraph("NIP: " + company.getNip())
                            .setFont(font).setFontSize(9).setTextAlignment(TextAlignment.CENTER));
            });

            document.add(new Paragraph(" ").setFont(font).setFontSize(6));

            String receiptNr = "PR/" + sale.getId() + "/" + sale.getDate().getYear();
            document.add(new Paragraph("Nr: " + receiptNr)
                    .setFont(font).setFontSize(9));
            document.add(new Paragraph("Data: " + sale.getDate().format(DATE_FMT))
                    .setFont(font).setFontSize(9));
            document.add(new Paragraph("Kasjer: " + sale.getUser().getUsername())
                    .setFont(font).setFontSize(9));

            document.add(new Paragraph(" ").setFont(font).setFontSize(6));

            Table table = new Table(UnitValue.createPercentArray(new float[]{4, 1, 2, 2}))
                    .useAllAvailableWidth();

            addHeaderCell(table, "Nazwa towaru", font);
            addHeaderCell(table, "Ilosc", font);
            addHeaderCell(table, "Cena jedn.", font);
            addHeaderCell(table, "Wartosc", font);

            for (SaleItem item : sale.getItems()) {
                BigDecimal subtotal = item.getPriceAtSale()
                        .multiply(BigDecimal.valueOf(item.getQuantity()));
                table.addCell(new Cell().add(new Paragraph(item.getProduct().getName()).setFont(font).setFontSize(9)));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getQuantity())).setFont(font).setFontSize(9)));
                table.addCell(new Cell().add(new Paragraph(formatMoney(item.getPriceAtSale())).setFont(font).setFontSize(9)));
                table.addCell(new Cell().add(new Paragraph(formatMoney(subtotal)).setFont(font).setFontSize(9)));
            }

            document.add(table);
            document.add(new Paragraph(" ").setFont(font).setFontSize(6));
            document.add(new Paragraph("RAZEM: " + formatMoney(sale.getTotalAmount()))
                    .setFont(font).setFontSize(14).setBold()
                    .setTextAlignment(TextAlignment.RIGHT));

            document.add(new Paragraph(" ").setFont(font).setFontSize(10));
            document.add(new Paragraph("Dziekujemy za zakupy!")
                    .setFont(font).setFontSize(9).setTextAlignment(TextAlignment.CENTER));
        } catch (Exception e) {
            throw new RuntimeException("Blad generowania paragonu", e);
        }
        return baos.toByteArray();
    }

    public byte[] generateInvoice(Long saleId) {
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new SaleNotFoundException(saleId));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (Document document = new Document(new PdfDocument(new PdfWriter(baos)), PageSize.A4)) {
            PdfFont font = loadFont();

            document.add(new Paragraph("FAKTURA VAT")
                    .setFont(font).setFontSize(22).setBold()
                    .setTextAlignment(TextAlignment.CENTER));

            String invoiceNr = "FV/" + sale.getId() + "/" + sale.getDate().getYear();
            document.add(new Paragraph("Nr: " + invoiceNr)
                    .setFont(font).setFontSize(12).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Data wystawienia: " + sale.getDate().format(DATE_ONLY_FMT))
                    .setFont(font).setFontSize(10).setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph(" ").setFont(font).setFontSize(8));

            Table partiesTable = new Table(UnitValue.createPercentArray(new float[]{1, 1}))
                    .useAllAvailableWidth();

            Cell sellerCell = new Cell().setPadding(8);
            sellerCell.add(new Paragraph("SPRZEDAWCA:").setFont(font).setBold().setFontSize(10));
            CompanyConfig company = companyConfigRepository.findById(1L).orElse(null);
            if (company != null && company.getName() != null) {
                sellerCell.add(line(company.getName(), font, 10));
                if (company.getNip() != null)
                    sellerCell.add(line("NIP: " + company.getNip(), font, 9));
                if (company.getAddress() != null)
                    sellerCell.add(line(company.getAddress(), font, 9));
                if (company.getCity() != null)
                    sellerCell.add(line(nvl(company.getPostalCode(), "") + " " + company.getCity(), font, 9));
                if (company.getPhone() != null)
                    sellerCell.add(line("Tel: " + company.getPhone(), font, 9));
                if (company.getEmail() != null)
                    sellerCell.add(line("Email: " + company.getEmail(), font, 9));
                if (company.getBankAccount() != null)
                    sellerCell.add(line("Nr konta: " + company.getBankAccount(), font, 9));
            } else {
                sellerCell.add(line("Brak danych firmy - uzupelnij /api/company", font, 9));
            }
            partiesTable.addCell(sellerCell);

            Cell buyerCell = new Cell().setPadding(8);
            buyerCell.add(new Paragraph("NABYWCA:").setFont(font).setBold().setFontSize(10));
            Contractor contractor = sale.getContractor();
            if (contractor != null) {
                buyerCell.add(line(contractor.getName(), font, 10));
                if (contractor.getNip() != null)
                    buyerCell.add(line("NIP: " + contractor.getNip(), font, 9));
                if (contractor.getAddress() != null)
                    buyerCell.add(line(contractor.getAddress(), font, 9));
                if (contractor.getCity() != null)
                    buyerCell.add(line(nvl(contractor.getPostalCode(), "") + " " + contractor.getCity(), font, 9));
                if (contractor.getPhone() != null)
                    buyerCell.add(line("Tel: " + contractor.getPhone(), font, 9));
                if (contractor.getEmail() != null)
                    buyerCell.add(line("Email: " + contractor.getEmail(), font, 9));
            } else {
                buyerCell.add(line("Brak kontrahenta", font, 9));
                buyerCell.add(line("(ustaw contractorId przy tworzeniu sprzedazy)", font, 8));
            }
            partiesTable.addCell(buyerCell);
            document.add(partiesTable);

            document.add(new Paragraph(" ").setFont(font).setFontSize(8));

            Table itemsTable = new Table(UnitValue.createPercentArray(new float[]{4, 1, 2, 2}))
                    .useAllAvailableWidth();

            addHeaderCell(itemsTable, "Nazwa towaru", font);
            addHeaderCell(itemsTable, "Ilosc", font);
            addHeaderCell(itemsTable, "Cena netto", font);
            addHeaderCell(itemsTable, "Wartosc netto", font);

            for (SaleItem item : sale.getItems()) {
                BigDecimal subtotal = item.getPriceAtSale()
                        .multiply(BigDecimal.valueOf(item.getQuantity()));
                itemsTable.addCell(new Cell().add(new Paragraph(item.getProduct().getName()).setFont(font).setFontSize(10)));
                itemsTable.addCell(new Cell().add(new Paragraph(String.valueOf(item.getQuantity())).setFont(font).setFontSize(10)));
                itemsTable.addCell(new Cell().add(new Paragraph(formatMoney(item.getPriceAtSale())).setFont(font).setFontSize(10)));
                itemsTable.addCell(new Cell().add(new Paragraph(formatMoney(subtotal)).setFont(font).setFontSize(10)));
            }

            document.add(itemsTable);

            document.add(new Paragraph(" ").setFont(font).setFontSize(8));
            document.add(new Paragraph("RAZEM NETTO: " + formatMoney(sale.getTotalAmount()))
                    .setFont(font).setFontSize(13).setBold()
                    .setTextAlignment(TextAlignment.RIGHT));

            document.add(new Paragraph(" ").setFont(font).setFontSize(20));

            Table signaturesTable = new Table(UnitValue.createPercentArray(new float[]{1, 1}))
                    .useAllAvailableWidth();
            Cell sig1 = new Cell().setBorder(Border.NO_BORDER)
                    .add(new Paragraph("...................................\nPodpis sprzedawcy")
                            .setFont(font).setFontSize(9).setTextAlignment(TextAlignment.CENTER));
            Cell sig2 = new Cell().setBorder(Border.NO_BORDER)
                    .add(new Paragraph("...................................\nPodpis nabywcy")
                            .setFont(font).setFontSize(9).setTextAlignment(TextAlignment.CENTER));
            signaturesTable.addCell(sig1);
            signaturesTable.addCell(sig2);
            document.add(signaturesTable);

            document.add(line("Wystawil: " + sale.getUser().getUsername(), font, 8));
        } catch (Exception e) {
            throw new RuntimeException("Blad generowania faktury", e);
        }
        return baos.toByteArray();
    }

    private void addHeaderCell(Table table, String text, PdfFont font) {
        table.addHeaderCell(new Cell()
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .add(new Paragraph(text).setFont(font).setFontSize(10).setBold()));
    }

    private Paragraph line(String text, PdfFont font, float size) {
        return new Paragraph(text).setFont(font).setFontSize(size).setMargin(1);
    }

    private String formatMoney(BigDecimal value) {
        return String.format("%.2f PLN", value);
    }

    private String nvl(String value, String fallback) {
        return value != null ? value : fallback;
    }
}
