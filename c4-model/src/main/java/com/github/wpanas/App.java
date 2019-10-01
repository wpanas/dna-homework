package com.github.wpanas;

import com.structurizr.api.StructurizrClient;
import com.structurizr.api.StructurizrClientException;

public class App {
    public static void main(String[] args) throws StructurizrClientException {
        var workspace = Pizzeria.create();
        var apiKey = System.getProperty("apiKey");
        var apiSecret = System.getProperty("apiSecret");
        var workspaceId = Long.parseLong(System.getProperty("workspaceId", "1"));

        var client = new StructurizrClient(apiKey, apiSecret);
        client.putWorkspace(workspaceId, workspace);
    }
}
