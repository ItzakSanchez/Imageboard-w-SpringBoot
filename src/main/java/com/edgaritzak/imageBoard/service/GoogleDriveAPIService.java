package com.edgaritzak.imageBoard.service;


import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

@Service
public class GoogleDriveAPIService {

  @Value("${drive.folder.id}")
  private String FOLDER_ID;

  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
  // private static final String CREDENTIALS_JSON_PATH = new ClassPathResource("credentials.json").toString();
  private ClassPathResource CREDENTIALS_JSON_PATH = new ClassPathResource("credentials.json");
  // private static final String CREDENTIALS_JSON_PATH = Paths.get(System.getProperty("user.dir"), "credentials.json").toString();

  /*
   *  GET GOOGLE DRIVE INSTANCE
   */
  public Drive getDriveInstance() throws IOException, GeneralSecurityException, FileNotFoundException{
    NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

    InputStream credentialsStream = CREDENTIALS_JSON_PATH.getInputStream();
    // FileInputStream credentialsStream = new FileInputStream(CREDENTIALS_JSON_PATH);
    GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream).createScoped(Collections.singleton(DriveScopes.DRIVE));

    return new Drive(HTTP_TRANSPORT, JSON_FACTORY, new HttpCredentialsAdapter(credentials));
  }



  /*
   * UPDATE IMAGE TO DRIVE
   */
  public String uploadImage(MultipartFile image) throws IOException, GeneralSecurityException{

		if(!image.getContentType().startsWith("image/")) {
			throw new IOException("Only image files.");
		}

    try{
      Drive drive = getDriveInstance();

      File fileMetadata = new File();
      fileMetadata.setName(image.getOriginalFilename());
      fileMetadata.setParents(Collections.singletonList(FOLDER_ID));


      File uploadFile = drive.files()
              .create(fileMetadata, new InputStreamContent(image.getContentType(),new ByteArrayInputStream(image.getBytes())))
              .setFields("id")
              .execute();

      System.out.println("Drive upload success");
      return uploadFile.getId();

    } catch(IOException ex){
      System.out.println("Drive upload failed");
      throw new IOException( ex.getMessage());
    } catch (GeneralSecurityException ex){
      System.out.println("Drive upload failed");
      throw new GeneralSecurityException(ex.getMessage());
    }
  }
}
