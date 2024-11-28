package com.ecommerce.ecom_essentials;

import com.ecommerce.ecom_essentials.entities.CameraEntity;

import java.util.ArrayList;
import java.util.List;


public class Test {
  

    public static void main(String [] arg){

        String data = "128GB 12GB RAM, 256GB 12GB RAM, 512GB 16GB RAM";
        String mainCamera =  "50 MP, f/1.8, (wide), PDAF\n8 MP, f/2.2, (ultrawide)\n2 MP, f/2.4, (macro)";
        String selfieCamera =  "50 MP, f/1.8, (wide)";
        String display = "6.6 inches, 104.9 cm2 (~76.7% screen-to-body ratio)";

        String ram  = "4MB RAM";


        //Split Data

//        String[] dis = display.split(",");
//        for (String disp : dis) {
//            disp = disp.trim();
//            if (disp.matches("\\d+ inches")) {
//                System.out.println("disp = " + disp);
//            }
//        }

        // Split by newline to separate each camera description

        // List to hold the resolution values
        List<String> mainResolutions = new ArrayList<>();
        List<String> selfieResolutions = new ArrayList<>();

        String[] cameraDetails = mainCamera.split("\n");
        // Loop through each line and extract the resolution
        for (String detail : cameraDetails) {
            // Split by commas and extract the first part (resolution)
            String[] parts = detail.split(",");
            String resolution = parts[0].trim(); // The first part should be the resolution
            mainResolutions.add(resolution);
        }

        String[] selfieCameraDetails = selfieCamera.split("\n");
        for (String detail : selfieCameraDetails){
            String[] parts = detail.split(",");
            String selfieResolution = parts[0].trim();
            selfieResolutions.add(selfieResolution);
        }

        System.out.println("mainResolutions = " + mainResolutions);
        System.out.println("selfieCameraDetails = " + selfieResolutions);

        CameraEntity cameraEntity = new CameraEntity();
        cameraEntity.setMainCamera(String.valueOf(mainResolutions));
        cameraEntity.setSelfieCamera(String.valueOf(selfieResolutions));
        System.out.println("cameraEntity.getMainCamera() = " + cameraEntity.getMainCamera());
        System.out.println("cameraEntity.getSelfieCamera() = " + cameraEntity.getSelfieCamera());
    }



//        String[] entries = data.split(", ");
//        List<String> dataList = Arrays.asList(entries);
//        System.out.println("dataList = " + dataList);
//
//        for (String entry : entries){
//            String[] parts = entry.split(" ");
//            if (parts.length >= 2){
//                System.out.println("parts[0] = " + parts[0]);
//                System.out.println("parts[1] = " + parts[1]);
//
//            }
//        }
    }

