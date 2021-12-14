package com.dawProject.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Component;

@Component
public class PdfService {
	
	public String PDF() throws IOException {
		
		PDDocument doc = null;
	       try
	       {
	           doc = new PDDocument();
	           PDPage page = new PDPage();
	           doc.addPage(page);
	           PDPageContentStream contentStream = new PDPageContentStream(doc, page);

	           PDFont pdfFont = PDType1Font.HELVETICA;
	           float fontSize = 25;
	           float leading = 1.5f * fontSize;

	           PDRectangle mediabox = page.getMediaBox();
	           float margin = 72;
	           float width = mediabox.getWidth() - 2*margin;
	           float startX = mediabox.getLowerLeftX() + margin;
	           float startY = mediabox.getUpperRightY() - margin;

	           String text = "PRUEBA I am trying to create a PDF file with a lot of text contents in the document. I am using PDFBox.An essay is, generally, a piece of writing that gives the author's own argument — but the definition is vague, overlapping with those of an article, a pamphlet, and a short story. Essays have traditionally been sub-classified as formal and informal. Formal essays are characterized by serious purpose, dignity, logical organization, length,whereas the informal essay is characterized by the personal element (self-revelation, individual tastes and experiences, confidential manner), humor, graceful style, rambling structure, unconventionality or novelty of theme.Lastly, one of the most attractive features of cats as housepets is their ease of care. Cats do not have to be walked. They get plenty of exercise in the house as they play, and they do their business in the litter box. Cleaning a litter box is a quick, painless procedure. Cats also take care of their own grooming. Bathing a cat is almost never necessary because under ordinary circumstances cats clean themselves. Cats are more particular about personal cleanliness than people are. In addition, cats can be left home alone for a few hours without fear. Unlike some pets, most cats will not destroy the furnishings when left alone. They are content to go about their usual activities until their owners return."; 
	           List<String> lines = new ArrayList<String>();
	           int lastSpace = -1;
	           while (text.length() > 0)
	           {
	               int spaceIndex = text.indexOf(' ', lastSpace + 1);
	               if (spaceIndex < 0)
	                   spaceIndex = text.length();
	               String subString = text.substring(0, spaceIndex);
	               float size = fontSize * pdfFont.getStringWidth(subString) / 1000;
	               System.out.printf("'%s' - %f of %f\n", subString, size, width);
	               if (size > width)
	               {
	                   if (lastSpace < 0)
	                       lastSpace = spaceIndex;
	                   subString = text.substring(0, lastSpace);
	                   lines.add(subString);
	                   text = text.substring(lastSpace).trim();
	                   System.out.printf("'%s' is line\n", subString);
	                   lastSpace = -1;
	               }
	               else if (spaceIndex == text.length())
	               {
	                   lines.add(text);
	                   System.out.printf("'%s' is line\n", text);
	                   text = "";
	               }
	               else
	               {
	                   lastSpace = spaceIndex;
	               }
	           }

	           contentStream.beginText();
	           contentStream.setFont(pdfFont, fontSize);
	           contentStream.newLineAtOffset(startX, startY);
	           float currentY=startY;
	           for (String line: lines)
	           {
	               currentY -=leading;

	               if(currentY<=margin)
	               {

	                   contentStream.endText(); 
	                   contentStream.close();
	                   PDPage new_Page = new PDPage();
	                   doc.addPage(new_Page);
	                   contentStream = new PDPageContentStream(doc, new_Page);
	                   contentStream.beginText();
	                   contentStream.setFont(pdfFont, fontSize);
	                   contentStream.newLineAtOffset(startX, startY);
	                   currentY=startY;
	               }
	               contentStream.showText(line);
	               contentStream.newLineAtOffset(0, -leading);
	           }
	           contentStream.endText(); 
	           contentStream.close();

	           
	           
	           doc.save("document.pdf");
	       }
	       finally
	       {
	           if (doc != null)
	           {
	               doc.close();
	           }
	       }		
		
		return "";
	}
	
	

}
