package com.example.BookInfo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EbookDto {

    private Long Id;

    private String cover;

    private String title;

    private String pdf;

    private String audioLink;
}
