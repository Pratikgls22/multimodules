package com.ecommerce.demo.controller;

public class GFG {

    // Main driver method
    public static void main(String args[])
    {
        // Custom input string
        String str = "geekss@for@geekss";
        String[] arrOfStr = str.split("@", 3);

        for (String a : arrOfStr)
            System.out.println(a);
    }
}