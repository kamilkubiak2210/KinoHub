package pl.kamilkubiak2210.app;

import pl.kamilkubiak2210.controller.ApplicationController;

public class KinoHub {
    public static void main(String[] args) {
        ApplicationController applicationController = new ApplicationController();
        applicationController.mainLoop();
    }
}