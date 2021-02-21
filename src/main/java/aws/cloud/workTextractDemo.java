package aws.cloud.work;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.io.InputStream;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.textract.AmazonTextract;
import com.amazonaws.services.textract.AmazonTextractClientBuilder;
import com.amazonaws.services.textract.model.DetectDocumentTextRequest;
import com.amazonaws.services.textract.model.DetectDocumentTextResult;
import com.amazonaws.services.textract.model.Document;
import com.amazonaws.util.IOUtils;

public class TextractDemo {

    static AmazonTextractClientBuilder clientBuilder = AmazonTextractClientBuilder.standard().withRegion(Regions.AP_SOUTH_1);

    public static void main(String[] args) throws IOException {
       
		clientBuilder.setCredentials(new AWSStaticCredentialsProvider(new
				BasicAWSCredentials("<ACCESS_KEY>", "<ACCESS_SECRET>")));

        String document="<IMAGE_NAME_WITH_PATH>";
		ByteBuffer imageBytes;

		try (InputStream inputStream = new FileInputStream(new File(document))) {
			imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
		}
		AmazonTextract client = clientBuilder.build();
		DetectDocumentTextRequest request = new DetectDocumentTextRequest()
				.withDocument(new Document()
						.withBytes(imageBytes));

		DetectDocumentTextResult result = client.detectDocumentText(request);
		System.out.println(result);
		
		result.getBlocks().forEach(block ->{
			if(block.getBlockType().equals("LINE"))
			System.out.println("text is "+ block.getText() + " confidence is "+ block.getConfidence());
		});
		
    }
}
