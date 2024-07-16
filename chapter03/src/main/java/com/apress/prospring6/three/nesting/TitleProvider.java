package com.apress.prospring6.three.nesting;

import org.apache.commons.lang3.StringUtils;

public class TitleProvider {

    private String title = "Gravity";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static TitleProvider instance(final String title) {
        var childProvider = new TitleProvider();
        if (StringUtils.isNotBlank(title)) {
            childProvider.setTitle(title);
        }
        return childProvider;
    }
}
