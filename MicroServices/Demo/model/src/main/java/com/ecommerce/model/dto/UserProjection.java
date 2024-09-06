package com.ecommerce.model.dto;

public interface UserProjection {

    Long getId();
    String getUserName();
    String getUserEmail();
    String getRoleName();
    Boolean getIsActive();

}
