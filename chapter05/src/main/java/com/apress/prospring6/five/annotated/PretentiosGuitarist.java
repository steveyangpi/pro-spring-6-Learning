package com.apress.prospring6.five.annotated;

import com.apress.prospring6.five.common.Guitar;
import com.apress.prospring6.five.common.Singer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("agustin")
public class PretentiosGuitarist implements Singer {

    private static Logger LOGGER = LoggerFactory.getLogger(PretentiosGuitarist.class);

    public void sing(Guitar guitar){
        if(guitar.getBrand().equalsIgnoreCase("Musicman")){
            throw new IllegalArgumentException("Unacceptable guitar!");
        }
        LOGGER.info("play: " + guitar.play());
    }

    @Override
    public void sing() {
        LOGGER.info("sing: solo tu puedes calmar el hambre de ti");
    }
}
