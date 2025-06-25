package sat.simulator.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sat.simulator.model.Satellite;

public class SatelliteController {

    private final List<Satellite> satellites = new ArrayList<>();
    private final Random random = new Random();

    // Inicializar satélites con posiciones aleatorias
    public void initializeSatellites(int count) {
        satellites.clear();
        for (int i = 0; i < count; i++) {
            double lat = -90 + 180 * random.nextDouble();
            double lon = -180 + 360 * random.nextDouble();
            satellites.add(new Satellite(lat, lon));
        }
    }

    public List<Satellite> getAllSatellites() {
        return satellites;
    }

    public Satellite getById(String id) {
        return satellites.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    //Mandar un comando al satelite con id de parametro
    public void sendCommand(String satelliteId, String command) {
        Satellite satellite = getById(satelliteId);
        if (satellite == null) {
        	System.out.println("ID not found: " + satelliteId);
            return;
        }

        switch (command.toUpperCase()) {
            case "REBOOT" -> satellite.reboot();
            case "SAFE_MODE" -> satellite.enterSafeMode();
            case "SIMULATE_SIGNAL_LOSS" -> satellite.simulateSignalLoss();
            default -> System.out.println("Unknown command: " + command);
        }
    }
    
    public void simulateTick() {
        for (Satellite s : satellites) {
            // Simular drenaje de batería y pequeño cambio de posición
            s.drainBattery(1.0 + 2.0 * random.nextDouble());
            s.updatePosition(random.nextDouble() - 0.5, random.nextDouble() - 0.5);

            // Aleatoriamente perder señal
            if (random.nextDouble() < 0.05) {
                s.simulateSignalLoss();
            }
        }
    }

    public void printStatus() {
        satellites.forEach(System.out::println);
    }
}
