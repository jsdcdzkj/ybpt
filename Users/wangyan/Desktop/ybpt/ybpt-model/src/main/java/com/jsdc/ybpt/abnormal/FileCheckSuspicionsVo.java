package com.jsdc.ybpt.abnormal;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


/**
*稽核疑点
* Author wzn
* Date 2022/11/15 14:53
*/
@Data

public class FileCheckSuspicionsVo {

   private  MultipartFile file;
   private  String  fileMd5;

}
