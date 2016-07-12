package com.develogical.camera;

public class Camera {

    private Sensor sensor;
    private MemoryCard memoryCard;
    private boolean isSwitchedOn;

    public Camera(Sensor sensor,MemoryCard memoryCard) {
        this.sensor = sensor;
        this.memoryCard = memoryCard;
        this.isSwitchedOn = false;
    }

    public void pressShutter() {
        if(isSwitchedOn) {
            byte[] data = sensor.readData();
            memoryCard.write(data);
        }
    }

    public void powerOn() {
        sensor.powerUp();
        this.isSwitchedOn = true;
    }

    public void powerOff() {
        sensor.powerDown();
        this.isSwitchedOn = false;
    }
}

