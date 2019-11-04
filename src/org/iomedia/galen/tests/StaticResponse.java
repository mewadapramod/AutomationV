package org.iomedia.galen.tests;

import org.iomedia.framework.Driver;
import org.iomedia.galen.common.uploadStaticResponse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class StaticResponse extends Driver {
	
	private uploadStaticResponse s3client;
	
	@BeforeMethod(alwaysRun=true)
	public void init(){
		s3client = new uploadStaticResponse(driverFactory, Dictionary, Environment, Reporter, Assert, SoftAssert, sTestDetails);
	}
	
	@Test
	public void uploadS3File(){
		String localPath = System.getProperty("user.dir") + "/APIRequest/response1.json";
		s3client.uploadStaticResponseFile(localPath, "vv-csstour", "amgr-static-api/staticResponse/data/response.json");
	}
}
