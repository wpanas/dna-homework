package com.github.wpanas;

import com.structurizr.Workspace;
import com.structurizr.view.Shape;

import static com.structurizr.model.Location.External;
import static com.structurizr.model.Location.Internal;
import static com.structurizr.view.PaperSize.A5_Landscape;

class Pizzeria {
    private static final String MOBILE_APP = "Mobile app";
    private static final String DATABASE = "Database";
    public static final String SPRING_MVC_REST_CONTROLLER = "Spring MVC Rest Controller";

    static Workspace create() {
        var workspace = new Workspace("Pizza Restaurant", "Model of a pizzeria software system.");
        var model = workspace.getModel();
        var views = workspace.getViews();

        var customer = model.addPerson(External, "Customer", "Pizzeria customer");

        var pizzeriaOrderingSystem = model.addSoftwareSystem(Internal, "Pizzeria ordering system", "Allows users to order pizza");
        customer.uses(pizzeriaOrderingSystem, "Uses");

        var mobileApp = pizzeriaOrderingSystem.addContainer("Mobile app", "Mobile app used for ordering pizza", "Android");
        mobileApp.addTags(MOBILE_APP);

        customer.uses(mobileApp, "Uses");

        var apiApp = pizzeriaOrderingSystem.addContainer("API", "API for ordering pizza", "Java and Spring MVC");

        var database = pizzeriaOrderingSystem.addContainer("Database", "Store user orders", "Relation Database Schema");
        database.addTags(DATABASE);

        var usersController = apiApp.addComponent("Users Controller", "Allows users to sign in to pizza ordering system", SPRING_MVC_REST_CONTROLLER);
        var ordersController = apiApp.addComponent("Orders Controller", "Allows users to order pizza", SPRING_MVC_REST_CONTROLLER);

        ordersController.uses(usersController, "Validates if user is already singed");

        var notificationSystem = model.addSoftwareSystem("Notification System", "Notifies user about order status");
        ordersController.uses(notificationSystem, "Sends notification using", "Android Push");

        apiApp.getComponents()
                .stream()
                .filter(c -> SPRING_MVC_REST_CONTROLLER.equals(c.getTechnology()))
                .forEach(c -> mobileApp.uses(c, "Uses", "JSON/HTTP"));

        usersController.uses(database, "Stores data about users accounts");
        ordersController.uses(database, "Stores data about users orders");

        model.addImplicitRelationships();

        var systemLandscapeView = views.createSystemLandscapeView("SystemLandscape", "The system landscape view for the Pizza ordering application");
        systemLandscapeView.addAllElements();
        systemLandscapeView.setPaperSize(A5_Landscape);

        var systemContextView = views.createSystemContextView(pizzeriaOrderingSystem, "SystemContext", "The system context diagram for the Pizza ordering application");
        systemContextView.setEnterpriseBoundaryVisible(false);
        systemContextView.addNearestNeighbours(pizzeriaOrderingSystem);
        systemContextView.setPaperSize(A5_Landscape);

        var containerView = views.createContainerView(pizzeriaOrderingSystem, "Containers", "The container diagram for the Pizza ordering application");
        containerView.add(customer);
        containerView.addAllContainers();
        containerView.setPaperSize(A5_Landscape);

        var componentView = views.createComponentView(apiApp, "Components", "The component diagram for API application");
        componentView.add(mobileApp);
        componentView.add(apiApp);
        componentView.add(database);
        componentView.addAllComponents();
        componentView.setPaperSize(A5_Landscape);

        var styles = views.getConfiguration().getStyles();

        styles.addElementStyle(MOBILE_APP).shape(Shape.MobileDeviceLandscape);
        styles.addElementStyle(DATABASE).shape(Shape.Cylinder);

        return workspace;
    }
}
