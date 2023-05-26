package com.example.bookshop.selenium.pages;

public enum NavigationLinkType {

    MAIN("//*[@id=\"navigate\"]/ul/li[1]/a"),
    GENRES("//*[@id=\"navigate\"]/ul/li[2]/a"),
    RECENT("//*[@id=\"navigate\"]/ul/li[3]/a"),
    POPULARS("//*[@id=\"navigate\"]/ul/li[4]/a"),
    AUTHORS("//*[@id=\"navigate\"]/ul/li[5]/a");

    String xPath;

    private NavigationLinkType(String aXPath) {
        xPath = aXPath;
    }

}
