package com.bside.bside_311.service;

import com.bside.bside_311.dto.UploadImageResultDto;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.hypersistence.tsid.TSID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@Component
public class ImageStorage {
  private final Cloudinary cloudinary;

  public ImageStorage(
      @Value("${cloudinary.url}") String cloudinaryUrl
  ) {
    cloudinary = new Cloudinary(cloudinaryUrl);
    cloudinary.config.secure = true;
  }

  public UploadImageResultDto save(byte[] bytes) {
    String id = TSID.Factory.getTsid().toString();

    Map options = ObjectUtils.asMap(
        "public_id", "test/" + id
    );
    try {
      Map result = cloudinary.uploader().upload(bytes, options);
      return UploadImageResultDto.of(result.get("secure_url").toString(),
          result.get("public_id").toString());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  //  https://console.cloudinary.com/pm/c-e722349e52038395e3e661e2cf4c78/developer-dashboard
  //  https://github.com/cloudinary/cloudinary_java/blob/master/samples/photo_album_gae/src/main/java/cloudinary/models/PhotoUpload.java#L6
  //  https://cloudinary.com/documentation/image_upload_api_reference#upload_examples
  //  https://cloudinary.com/documentation/java_asset_administration#upload_api_example_delete_a_single_asset
  //  https://cloudinary.com/documentation/image_upload_api_reference#destroy
  public void delete(String publicId) {
    try {
      Map result =
          cloudinary.uploader().destroy(publicId, ObjectUtils.asMap("resource_type", "image"));
      if (!result.containsKey("result") || !result.get("result").equals("ok")) {
        throw new RuntimeException("이미지 삭제 실패" + result.toString());
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  public void saveInLocalServer(byte[] bytes) {
    String id = TSID.Factory.getTsid().toString();

    File file = new File("data/" + id + ".jpg");

    try (OutputStream outputStream = new FileOutputStream(file)) {
      outputStream.write(bytes);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}