package com.springboot.h2.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Uczen {
    private int numer;
    private boolean JestNieobecny;
    private boolean JestNieprzygotowany;
}

