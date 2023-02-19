package com.nart.util.upload;

import org.apache.http.entity.ContentType;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: TypeDict
 *  TODO
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2023/01/04 17:40
 */
public class TypeDict {
    /**
     Commonly used files have the following file headers: (the first six digits prevail)
     JPEG (jpg), file header: FFD8FF
     PNG (png), file header: 89504E47
     GIF (gif), file header: 47494638
     TIFF (tif), file header: 49492A00
     Windows Bitmap (bmp), file header: 424D
     CAD (dwg), file header: 41433130
     Adobe Photoshop (psd), file header: 38425053
     Rich Text Format (rtf), file header: 7B5C727466
     XML (xml), file header: 3C3F786D6C
     HTML (html), header: 68746D6C3E
     Email [thorough only] (eml), file header: 44656C69766572792D646174653A
     Outlook Express (dbx), file header: CFAD12FEC5FD746F
     Outlook (pst), file header: 2142444E
     MS Word/Excel (xls.or.doc), file header: D0CF11E0
     MS Access (mdb), file header: 5374616E64617264204A
     WordPerfect (wpd), file header: FF575043
     Postscript (eps.or.ps), file header: 252150532D41646F6265
     Adobe Acrobat (pdf), file header: 255044462D312E
     Quicken (qdf), file header: AC9EBD8F
     Windows Password (pwl), header: E3828596
     ZIP Archive (zip), header: 504B0304
     RAR Archive (rar), header: 52617221
     Wave (wav), file header: 57415645
     AVI (avi), file header: 41564920
     Real Audio (ram), file header: 2E7261FD
     Real Media (rm), File Header: 2E524D46
     MPEG (mpg), file header: 000001BA
     MPEG (mpg), file header: 000001B3
     Quicktime (mov), file header: 6D6F6F76
     Windows Media (asf), file header: 3026B2758E66CF11
     MIDI (mid), file header: 4D546864
     */
    public static ContentType checkType(String fileType) {
        switch (fileType) {
            case "FFD8FF": return ContentType.IMAGE_JPEG;
            case "89504E": return ContentType.IMAGE_PNG;
            case "474946": return ContentType.IMAGE_GIF;
            default: return null;
        }
    }
}