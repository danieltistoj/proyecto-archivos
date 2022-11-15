/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.proyect2;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author gustavo
 */
public class PDF {
    
    private String name;
    private String version;
    private Long size;
    private Map<String, String> metadata;
    private int pages;
    private ArrayList<String> fonts;
    private int images;
    
    
    public PDF(){
        
    }
    
    public PDF(String name, String version, Long size, Map<String, String> metadata, int pages, ArrayList<String> fonts, int images) {
        this.name = name;
        this.version = version;
        this.size = size;
        this.metadata = metadata;
        this.pages = pages;
        this.fonts = fonts;
        this.images = images;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the size
     */
    public Long getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(Long size) {
        this.size = size;
    }

    /**
     * @return the metadata
     */
    public Map<String, String> getMetadata() {
        return metadata;
    }

    /**
     * @param metadata the metadata to set
     */
    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    /**
     * @return the pages
     */
    public int getPages() {
        return pages;
    }

    /**
     * @param pages the pages to set
     */
    public void setPages(int pages) {
        this.pages = pages;
    }

    /**
     * @return the fonts
     */
    public ArrayList<String> getFonts() {
        return fonts;
    }

    /**
     * @param fonts the fonts to set
     */
    public void setFonts(ArrayList<String> fonts) {
        this.fonts = fonts;
    }

    /**
     * @return the images
     */
    public int getImages() {
        return images;
    }

    /**
     * @param images the images to set
     */
    public void setImages(int images) {
        this.images = images;
    }
    
    
    
}
