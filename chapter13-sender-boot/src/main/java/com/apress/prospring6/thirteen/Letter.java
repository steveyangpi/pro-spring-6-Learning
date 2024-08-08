package com.apress.prospring6.thirteen;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
public class Letter implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String title;

    private String sender;

    private LocalDate sentOn;

    @Enumerated(EnumType.STRING)
    private Category category = Category.MISC;

    @NotEmpty
    private String content;
}
