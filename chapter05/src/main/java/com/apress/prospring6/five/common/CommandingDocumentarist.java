package com.apress.prospring6.five.common;

import com.apress.prospring6.five.annotated.NewDocumentarist;
import org.springframework.stereotype.Component;

@Component("commandingDocumentarist")
public class CommandingDocumentarist  extends NewDocumentarist {

    @Override
    public void execute() {
        guitarist.sing();
        Guitar guitar = new Guitar();
        guitar.setBrand("Gibson");
        guitarist.sing(guitar);
        guitarist.sing(new Guitar());
        guitarist.talk();
    }
}
