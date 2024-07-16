package com.apress.prospring6.six.boot.records;

import java.time.LocalDate;

public record Album(Long id,
                    Long singerId,
                    String title,
                    LocalDate releaseDate) {
}
