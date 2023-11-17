package com.bside.bside_311.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attach extends BaseEntity {
  @Id
  @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
  @Column(name = "attach_no")
  private Long id;

  private String attachUrl;

  private String publicId;

  private String refTable;

  private Long refNo;

  private String originFilename;
  private String saveFileName;
  private String saveLocation;
  private String description;

  @Column(name = "attach_type")
  @Enumerated(EnumType.STRING)
  private AttachType attachType;

  public static Attach of(MultipartFile image, Long resourceNo, AttachType attachType) {
    String refTable = null;
    switch (attachType) {
      case PROFILE -> {
        refTable = "user";
      }
      case POST -> {
        refTable = "post";
      }
      case ALCOHOL -> {
        refTable = "alcohol";
      }
    }
    return Attach.builder().refTable(refTable)
                 .refNo(resourceNo)
                 .originFilename(image.getOriginalFilename())
                 .saveFileName("")
                 .saveLocation("")
                 .description("")
                 .attachType(attachType)
                 .build();
  }

  public void setCloudnaryInfo(String attachUrl, String publicId) {
    this.attachUrl = attachUrl;
    this.publicId = publicId;
  }
}
