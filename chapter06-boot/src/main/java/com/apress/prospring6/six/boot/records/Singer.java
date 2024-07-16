package com.apress.prospring6.six.boot.records;


import java.sql.Date;
import java.util.List;

public record Singer(Long id,
                     String firstName,
                     String lastName,
                     Date birthDate,
                     List<Album> albums) {
}
