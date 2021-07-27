package com.example.knexample.Notes;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class Date {

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Temporal(TemporalType.TIMESTAMP)
    private Date datetimeOfBirth;

    @Temporal(TemporalType.TIME)
    private Date timeOfBirth;
}
