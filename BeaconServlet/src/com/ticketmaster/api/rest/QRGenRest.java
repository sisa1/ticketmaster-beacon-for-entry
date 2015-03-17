package com.ticketmaster.api.rest;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@Path("rest/QRGen")
public class QRGenRest {
	
	@GET
	@Produces("image/png")
	public Response getResponse(@QueryParam("UUID") @DefaultValue("N/A") String uuid,
			  					@QueryParam("Major")  @DefaultValue("-1") int major,
			  					@QueryParam("Minor")  @DefaultValue("-1") int minor) {
		
		String myCodeText = "{ \"UUID\": " + uuid + ", \"Major\":" + major + ", \"Minor\": " + minor + " }";
        int size = 125;
        String fileType = "png";
        byte[] imageData = null;
        Response response = null;
        try {
            Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix byteMatrix = qrCodeWriter.encode(myCodeText,BarcodeFormat.QR_CODE, size, size, hintMap);
            int CrunchifyWidth = byteMatrix.getWidth();
            BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth,
                    BufferedImage.TYPE_INT_RGB);
            image.createGraphics();
 
            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
            graphics.setColor(Color.BLACK);
 
            for (int i = 0; i < CrunchifyWidth; i++) {
                for (int j = 0; j < CrunchifyWidth; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	    ImageIO.write(image, fileType, baos);
    	    imageData = baos.toByteArray();
        } catch (WriterException e) {
            e.printStackTrace();
            response = Response.status(500).build();
        } catch (IOException e) {
            e.printStackTrace();
            response = Response.status(500).build();
        }
        response = Response.status(200).entity(imageData).build();
        return response;
	}	
}