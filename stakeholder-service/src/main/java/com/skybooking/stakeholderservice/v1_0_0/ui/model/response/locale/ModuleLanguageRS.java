package com.skybooking.stakeholderservice.v1_0_0.ui.model.response.locale;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ModuleLanguageRS {

    private Map<String, List<Map<String, String>>> root = new TreeMap<>();

    public Map<String, List<Map<String, String>>> getRoot() {
        return root;
    }

    public void setRoot(Map<String, List<Map<String, String>>> root) {
        this.root = root;
    }

}
