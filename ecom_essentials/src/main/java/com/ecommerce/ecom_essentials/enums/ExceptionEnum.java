package com.ecommerce.ecom_essentials.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionEnum {

  UNAUTHORIZED("Unauthorized", "UNAUTHORIZED"),
  SOMETHING_WENT_WRONG("Something went wrong", "SOMETHING_WENT_WRONG"),
  USER_NAME_NOT_FOUND("UserName not found", "USER_NAME_NOT_FOUND"),
  USER_NOT_FOUND("User not found", "USER_NOT_FOUND"),
  USER_ID_NOT_FOUND("User Id not found", "USER_ID_NOT_FOUND"),
  USER_DETAILS_NOT_FOUND("User details  not found", "USER_DETAILS_NOT_FOUND"),
  USER_ROLE_MAPPING_NOT_FOUND("User Role Mapping Not Found","USER_ROLE_MAPPING_NOT_FOUND"),
  USER_ROLE_NOT_FOUND("User role not found", "USER_ROLE_NOT_FOUND"),
  INCORRECT_USERNAME_OR_PASSWORD("Incorrect username or password", "INCORRECT_USERNAME_OR_PASSWORD"),
  INVALID_CREDENTIALS("Invalid credentials", "INVALID_CREDENTIALS"),
  INVALID_PASSWORD("Invalid password", "INVALID_PASSWORD"),
  USER_EXISTS("User already exists", "USER_EXISTS"),
  USER_ALREADY_EXIST_WITH_THIS_EMAIL("User already exist with this email", "USER_ALREADY_EXIST_WITH_THIS_EMAIL"),
  INVALID_TOKEN("Invalid token", "INVALID_TOKEN"),
  ROLE_NOT_FOUND("Role not found", "ROLE_NOT_FOUND"),
  KEY_ALREADY_EXIST("Key already existS", "KEY_ALREADY_EXIST"),
  KEY_NOT_EXIST("Key not existS", "KEY_NOT_EXIST"),
  DEVICE_NAME_ALREADY_EXIST("Device Name already existS", "DEVICE_NAME_ALREADY_EXIST"),
  ROLE_ALREADY_EXIST("Role already exists", "ROLE_EXISTS"),
  MODEL_NAME_ALREADY_EXIST("Model Name already existS", "MODEL_NAME_ALREADY_EXIST"),
  IMAGE_ALREADY_EXIST("Image already existS", "IMAGE_ALREADY_EXIST"),
  IMAGE_NOT_FOUND("Image not found", "IMAGE_NOT_FOUND"),
  BATTERY_CAPACITY_ALREADY_EXIST("Battery Capacity already existS", "BATTERY_CAPACITY_ALREADY_EXIST"),
  COLOR_ALREADY_EXIST("Color already exists", "COLOR_ALREADY_EXIST"),
  INTERNAL_STORAGE_ALREADY_EXIST("Internal Storage already exists", "INTERNAL_STORAGE_ALREADY_EXIST"),
  RAM_STORAGE_ALREADY_EXIST("RAM Storage already exists", "RAM_STORAGE_ALREADY_EXIST"),
  ERROR_WHILE_GENERATING_SECERET_KEY("Error While generating Key","ERROR_WHILE_GENERATING_SECERET_KEY"),
  RESPONSE_RECEIVED_NULL("Received null response from the API", "RESPONSE_NULL_WHILE_MAKE_POST_REQUEST"),
  DRAFT_NOT_PENDING("Draft not Pending", "DRAFT_NOT_PENDING"),
  BRAND_NOT_FOUND("Brand not Found", "BRAND_NOT_FOUND"),
  MODEL_NAME_NOT_FOUND("Model Name not Found", "MODEL_NAME_NOT_FOUND"),
  BRAND_HAS_NO_DATA("Brand has not Data", "BRAND_HAS_NO_DATA"),
  MODEL_HAS_NO_DATA("Model has not Data", "MODEL_HAS_NO_DATA"),
  COLOR_NOT_FOUND("Color not Found", "COLOR_NOT_FOUND"),
  RAM_NOT_FOUND("Ram Storage not Found", "RAM_NOT_FOUND"),
  INTERNAL_STORAGE_NOT_FOUND("Internal Storage not Found", "INTERNAL_STORAGE_NOT_FOUND"),
  CAMERA_NOT_FOUND("Camera not Found", "CAMERA_NOT_FOUND"),
  BATTERY_NOT_FOUND("Battery not Found", "BATTERY_NOT_FOUND"),
  OPERATING_SYSTEM_NOT_FOUND("Operating System not Found", "OPERATING_SYSTEM_NOT_FOUND"),
  PRODUCT_ID_NOT_FOUND("Product Id not Found", "PRODUCT_ID_NOT_FOUND"),
  PRODUCT_DRAFT_ID_NOT_FOUND("Product Id not Found", "PRODUCT_DRAFT_ID_NOT_FOUND"),
  VENDOR_ID_NOT_FOUND("Vendor Id not found", "VENDOR_ID_NOT_FOUND"),
  CATEGORY_NOT_FOUND("Category Not Found","CATEGORY_NOT_FOUND");
  private final String value;
  private final String message;
}
