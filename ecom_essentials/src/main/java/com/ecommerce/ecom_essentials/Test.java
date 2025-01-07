package com.ecommerce.ecom_essentials;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Test {


    public static void main(String[] arg) {


        List<String> arrayList = new ArrayList<>();
        arrayList.add("Alice");
        arrayList.add(0,"Bob");
        System.out.println("ArrayList: " + arrayList);

        List<String> linkedList = new LinkedList<>();
        linkedList.add("Alice");
        linkedList.add(0, "Bob");
        linkedList.add("Charlie"); // Fast insertion
        System.out.println("LinkedList: " + linkedList);



//        String data = "128GB 12GB RAM, 256GB 12GB RAM, 512GB 16GB RAM";
//        String mainCamera = "50 MP, f/1.8, (wide), PDAF\n8 MP, f/2.2, (ultrawide)\n2 MP, f/2.4, (macro)";
//        String selfieCamera = "50 MP, f/1.8, (wide)";
//        String display = "6.6 inches, 104.9 cm2 (~76.7% screen-to-body ratio)";
//        String cameras = "50 MP 20 MP, 12 MP";
//        String ram = "6GB/4GB RAM";


        //Split Data

//        String[] dis = display.split(",");
//        for (String disp : dis) {
//            disp = disp.trim();
//            if (disp.matches("\\d+ inches")) {
//                System.out.println("disp = " + disp);
//            }
//        }


        // Split camera:
//        String[] camera = cameras.split(", ",2);
//        String main = camera[0].trim();
//        String selfie = camera[1].trim();
////        String selfie = cameraParts.length > 1 ? cameraParts[1].trim() : ""; // "12 MP", or empty if no second part
//        System.out.println("Main Camera: " + main);
//        System.out.println("Selfie Camera: " + selfie);


        // Split by newline to separate each camera description

//        String[] rams = ram.split("/");
//        System.out.println("rams = " + Arrays.stream(rams).toList());
//        for (String rame : rams) {
//            rame = rame.trim();
//            System.out.println("rame = " + rame);
//            if (rame.matches(".*?(\\d+)(GB|MB|TB).*")) {
//                // Extract the matched part using regex
//                Pattern pattern = Pattern.compile("(\\d+)(GB|MB|TB)");
//                Matcher matcher = pattern.matcher(rame);
//                if (matcher.find()) {
//                    System.out.println("Extracted RAM: " + matcher.group());
//                }
//            }
//        }

    }
}


//        // List to hold the resolution values
//        List<String> mainResolutions = new ArrayList<>();
//        List<String> selfieResolutions = new ArrayList<>();
//
//        String[] cameraDetails = mainCamera.split("\n");
//        // Loop through each line and extract the resolution
//        for (String detail : cameraDetails) {
//            // Split by commas and extract the first part (resolution)
//            String[] parts = detail.split(",");
//            String resolution = parts[0].trim(); // The first part should be the resolution
//            mainResolutions.add(resolution);
//        }
//
//        String[] selfieCameraDetails = selfieCamera.split("\n");
//        for (String detail : selfieCameraDetails){
//            String[] parts = detail.split(",");
//            String selfieResolution = parts[0].trim();
//            selfieResolutions.add(selfieResolution);
//        }
//
//        System.out.println("mainResolutions = " + mainResolutions);
//        System.out.println("selfieCameraDetails = " + selfieResolutions);
//
//        CameraEntity cameraEntity = new CameraEntity();
//        cameraEntity.setMainCamera(String.valueOf(mainResolutions));
//        cameraEntity.setSelfieCamera(String.valueOf(selfieResolutions));
//        System.out.println("cameraEntity.getMainCamera() = " + cameraEntity.getMainCamera());
//        System.out.println("cameraEntity.getSelfieCamera() = " + cameraEntity.getSelfieCamera());
//    }


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
//    }

