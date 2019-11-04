package org.iomedia.galen.common;

import java.io.File;

import org.iomedia.common.BaseUtil;
import org.iomedia.framework.Reporting;
import org.iomedia.framework.WebDriverFactory;
import org.iomedia.framework.Driver.HashMapNew;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class uploadStaticResponse extends BaseUtil {

	public uploadStaticResponse(WebDriverFactory driverFactory, HashMapNew Dictionary, HashMapNew Environment, Reporting Reporter, org.iomedia.framework.Assert Assert, org.iomedia.framework.SoftAssert SoftAssert, ThreadLocal<HashMapNew> sTestDetails) {
		super(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
	}
	
	public void uploadStaticResponseFile(String localPath, String bucketName, String key){
		String uploadFileName= localPath;
		
		@SuppressWarnings("deprecation")
		AmazonS3 s3 = new AmazonS3Client(new BasicAWSCredentials(Environment.get("accessKey"), Environment.get("secretKey")));
    	Region usEast1 = Region.getRegion(Regions.US_EAST_1);
    	s3.setRegion(usEast1);

    	System.out.println("===========================================");
    	System.out.println("Getting Started with Amazon S3");
    	System.out.println("===========================================\n");

    	System.out.println("Uploading a response.json to S3 from a file");
    	File file = new File(uploadFileName);
		s3.putObject(new PutObjectRequest(bucketName, key, file));
		System.out.println("Uploading completed");
	}
}
