package com.example.knexample.Notes;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;



public class ENUM {

    @Enumerated(EnumType.STRING)
    private ENUMType status;

    @Enumerated(EnumType.ORDINAL)
    private ENUMType statusAsINT;
}
