package com.info.country.service.dto.country;

import lombok.Data;

@Data
public class Name {
    private String common;
    private String official;
    private NativeName nativeName;

}
