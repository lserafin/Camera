package com.develogical.camera;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class CameraTest {

    final byte[] data = new byte[] {0,1};

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    Sensor sensor = context.mock(Sensor.class);
    MemoryCard memoryCard = context.mock(MemoryCard.class);
    Camera camera = new Camera(sensor,memoryCard);

    @Test
    public void switchingTheCameraOnPowersUpTheSensor() {
        context.checking(new Expectations() {{
            exactly(1).of(sensor).powerUp();
        }}
        );

        camera.powerOn();
    }
    @Test
    public void switchingTheCameraOffPowersOffTheSensor() {
        context.checking(new Expectations() {{
            ignoring(sensor).powerUp();
            exactly(1).of(sensor).powerDown();
        }});

        camera.powerOn();
        camera.powerOff();
    }

    @Test
    public void pressingTheShutterDoesNothingWhenPowerIsOff() {
        context.checking(new Expectations() {{
            never(sensor);
            never(memoryCard);
        }});


        camera.pressShutter();
    }

    @Test
    public void pressingTheShutterWithPowerOnCopiesData() {
        context.checking(new Expectations() {{
            ignoring(sensor).powerUp();
            exactly(1).of(sensor).readData();
            will(returnValue(data));
            exactly(1).of(memoryCard).write(data);
        }});

        camera.powerOn();
        camera.pressShutter();
    }
}
