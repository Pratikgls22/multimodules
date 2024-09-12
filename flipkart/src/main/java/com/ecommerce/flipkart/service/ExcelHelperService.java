package com.ecommerce.flipkart.service;

import com.ecommerce.flipkart.entity.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ExcelHelperService {
    public static boolean isValidExcelFile(MultipartFile file){
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" );
    }
    public static List<ProductEntity> getCustomersDataFromExcel(InputStream inputStream){
        List<ProductEntity> productEntityList = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("customers");
            int rowIndex =0;
            for (Row row : sheet){
                if (rowIndex ==0){
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;

                ProductEntity productEntity = new ProductEntity();
                CategoryEntity categoryEntity = new CategoryEntity();
                CategoryEntity subCategoryEntity = new CategoryEntity();
                ModelEntity modelEntity = new ModelEntity();
                RamEntity ramEntity = new RamEntity();
                ColorEntity colorEntity = new ColorEntity();
                InternalStorageEntity storage = new InternalStorageEntity();
                PriceEntity priceEntity = new PriceEntity();

                while (cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    switch (cellIndex){
                        case 0 -> productEntity.setProductId((int) cell.getNumericCellValue());
                        case 1 -> categoryEntity.setCategoryName(cell.getStringCellValue());
                        case 2 -> subCategoryEntity.setCategoryName(cell.getStringCellValue());
                        case 3 -> modelEntity.setModelName(cell.getStringCellValue());
                        case 4 -> ramEntity.setRamSize(cell.getStringCellValue());
                        case 5 -> colorEntity.setColorName(cell.getStringCellValue());
                        case 6 -> storage.setStorageSize(cell.getStringCellValue());
                        case 7 -> priceEntity.setPrice((int) cell.getNumericCellValue());
                    }
                    cellIndex++;
                }
                productEntityList.add(productEntity);

            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return productEntityList;
    }

}
