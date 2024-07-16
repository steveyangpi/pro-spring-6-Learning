package com.apress.prospring6.three.alias;

import org.springframework.stereotype.Component;

@Component("johnMayer")
@Trophy(name = {"grammy", "platinum disk"})
public class Singer {
    private String lyric = "I used to crave the sight of you";

    public void sing() {
        System.out.println(lyric);
    }
}
