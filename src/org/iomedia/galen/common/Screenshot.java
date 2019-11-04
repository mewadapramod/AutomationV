package org.iomedia.galen.common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.iomedia.common.BaseUtil;
import org.iomedia.framework.OSValidator;
import org.openqa.selenium.NotFoundException;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class Screenshot {
	BaseUtil base;

	public Screenshot(BaseUtil base) {
		this.base = base;
	}
	
	public void deleteDirectoryIfExists(String clientName) throws Exception {
        String path1=  System.getProperty("user.dir") + OSValidator.delimiter + "CompareScreenshots" + OSValidator.delimiter + clientName.trim();
        File path = new File(path1);
        if (path.exists()) {
            System.out.println("delete the directory");
            FileUtils.deleteDirectory(path);
        }
    }
	
	public void deleteDirectoryIfExists() throws Exception {
        String path1=  System.getProperty("user.dir") + OSValidator.delimiter + "POFile";
        File path = new File(path1);
        if (path.exists()) {
            System.out.println("delete the directory");
            FileUtils.deleteDirectory(path);
        }
    }
	
	public File createPOFileFolder() throws Exception {
		File file = new File("POFile");
		if(!file.exists()) {
	        if (file.mkdir()) {
	            System.out.println("Directory is created!");
	        } else {           
	            System.out.println("Failed to create directory!");
	        }
		}
		return file;
	}
	
	
	public File createFolder() throws Exception {
		File file = new File("CompareScreenshots");
		if(!file.exists()) {
	        if (file.mkdir()) {
	            System.out.println("Directory is created!");
	        } else {           
	            System.out.println("Failed to create directory!");
	        }
		}
		return file;
	}
	
	public File siteFoldercreation(File parent, String clientName) {
//		parent.mkdirs();
//	    if (!parent.exists() || !parent.isDirectory()) {
//	     System.out.println("Parent Directory is missing");
//	    }
	    File subFile = new File(parent, clientName);
	    subFile.mkdir();
	    if (!subFile.exists() || !subFile.isDirectory()) {
	    	System.out.println("Failed to create Subdirectory");
	    }
	    return subFile;
	}

	public String getFilepath(boolean deleteExistingDirectory) throws Exception {
		String clientName = "";
		if(!base.Environment.get("APP_URL").trim().equalsIgnoreCase("")) {
			String appurl = base.Environment.get("APP_URL").trim();
			if(appurl.trim().endsWith("/"))
				appurl = appurl.trim().substring(0, appurl.trim().length() - 1);
			String clientId = appurl.substring(appurl.lastIndexOf("/") + 1).trim().toUpperCase();
			clientName = clientId;
		} else {
			clientName = base.Environment.get("x-client").trim();
		}
		clientName = clientName.trim().toLowerCase();
		if(deleteExistingDirectory)
			deleteDirectoryIfExists(clientName);
		File file = siteFoldercreation(createFolder(), clientName);
		return file.getAbsolutePath();	
	}
	
	public String getPOFilepath(boolean deleteExistingDirectory) throws Exception {
		if(deleteExistingDirectory)
			deleteDirectoryIfExists();
		File file = createPOFileFolder();
		return file.getAbsolutePath();	
	}
	
//	public void saveImage(String imageUrl, String destinationFile) throws IOException {
//		URL url = new URL(imageUrl);
//		InputStream is = url.openStream();
//		OutputStream os = new FileOutputStream(destinationFile);
//
//		byte[] b = new byte[2048];
//		int length;
//
//		while ((length = is.read(b)) != -1) {
//			os.write(b, 0, length);
//		}
//
//		is.close();
//		os.close();
//	}

	
	public String decodeQRCode(File qrCodeimage) throws IOException, com.google.zxing.NotFoundException {
        BufferedImage bufferedImage = ImageIO.read(qrCodeimage);
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            System.out.println("There is no QR code in the image");
            return null;
        }
    }
}