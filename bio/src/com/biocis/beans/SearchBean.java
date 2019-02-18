package com.biocis.beans;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import com.sbs.espace.DataTypeValidator;
import com.sbs.espace.*;
import java.util.Collection;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class SearchBean implements java.io.Serializable {

    static public final String ORGANISM = "organism";
    static public final String SEARCH_NAME = "searchName";
    static public final String WIDTH = "width";
    static public final String NO_OF_SITES = "noOfSites";
    static public final String OVERLAP_SITES = "overlapSites";
    static public final String MIN = "min";
    static public final String MAX = "max";
    static public final String CONDITION = "condition";
    static public final String ORDER_OF_SITES = "orderOfSites";
    static public final String USE_CACHE = "useCache";
    static public final String WIDTH_TO_INCLUDE = "widthToInclude";
    static public final String GENE_ORIENTATION = "geneOrientation";
    static public final String GENE_NAMES = "geneNames";
    static public final String ANY_GENE = "anyGene";
    static public final String FILE_NAME = "fileName";

    static public final String TAG = "tag";
    static public final String TAG_NAME = "tagName";
    static public final String TAG_PATTERH = "tagPattern";
    static public final String TAG_OVERLAP = "tagOverlap";

    private List tags;
    private String name;
    private int width =300;
    private int noOfSites =3;
    private String overlapSites;
    private int min;
    private int max;
    private String condition;
    private String orderOfSites;
    private boolean useCache =true;
    private int widthToInclude =5000;
    private String geneOrientation;
    private String geneNames;
    private boolean anyGene;
    private String fileName;
    public String organism;

    public SearchBean() {
        tags = new ArrayList();
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setOrganism(String orgName) {
        this.organism = orgName;
    }

    public String getOrganism() {
        return this.organism;
    }

    public void clearTags() {
        this.tags.clear();
    }

    public void addTag(Map tag) {
        this.tags.add(tag);
    }

    public void setTags(Collection c) {
        this.tags = new ArrayList(c);
    }

    public List getTags() {
        return this.tags;
    }

    public void setGeneOrientation(String orientation) {
        this.geneOrientation = orientation;
    }

    public String getGeneOrientation() {
        return this.geneOrientation;
    }

    public void setGeneNames(String geneNames) {
        this.geneNames = geneNames;
    }

    public String getGeneNames() {
        return this.geneNames;
    }

    public void setAnyGene(boolean anyGene) {
        this.anyGene = anyGene;
    }

    public boolean getAnyGene() {
        return this.anyGene;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setOrderOfSites(String orderOfSites) {
        this.orderOfSites = orderOfSites;
    }

    public String getOrderOfSites() {
        return this.orderOfSites;
    }

    public void setUseCache(boolean useCache) {
        this.useCache = useCache;
    }

    public boolean getUseCache() {
        return this.useCache;
    }

    public int getWidthToInclude() {
        return this.widthToInclude;
    }

    public void setWidthToInclude(int width) {
        this.widthToInclude = width;
    }



    public void setName(String name) {
        this.name =name;
    }

    public String getName() {
        return this.name;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getNoOfSites() {
        return this.noOfSites;
    }

    public void setNoOfSites(int noOfSites) {
        this.noOfSites = noOfSites;
    }

    public String getOverlapSites() {
        return this.overlapSites;
    }

    public void setOverlapSites(String overlapSites) {
        this.overlapSites = overlapSites;
    }

    public int getMin() {
        return this.min;
    }

    public void setMin(int min) {
        this.min =min;
    }

    public int getMax() {
        return this.max;
    }

    public void setMax(int max) {
        this.max =max;
    }

//    static public class Tag implements java.io.Serializable {
//        String tag;
//        String name;
//        String pattern;
//        boolean overlap;
//
//        public Tag(Map info) {
//            tag = (String)info.get("tag");
//            name = (String) info.get("name");
//            pattern = (String) info.get("pattern");
//            String bool = (String)info.get("overlap");
//            this.overlap = DataTypeValidator.getBoolean(bool);
//        }
//
//        public Tag(String tag, String name, String pattern, boolean overlap) {
//            this.tag = tag;
//            this.name = name;
//            this.pattern = pattern;
//            this.overlap = overlap;
//
//        }
//    }

}