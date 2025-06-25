package sat.simulator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.part.ViewPart;

import sat.simulator.controller.SatelliteController;
import sat.simulator.model.Satellite;

import java.util.stream.Collectors;

public class View extends ViewPart {

    public static final String ID = "com.example.satellite.ui.view";

    private Text statusText;
    private SatelliteController controller;
    private Canvas canvas;

    @Override
    public void createPartControl(Composite parent) {
        controller = new SatelliteController();
        controller.initializeSatellites(5);

        parent.setLayout(new GridLayout(2, false));

        // Boton para simular tick
        Button simulateBtn = new Button(parent, SWT.PUSH);
        simulateBtn.setText("Simular Tick");
        simulateBtn.addListener(SWT.Selection, e -> {
            controller.simulateTick();
            updateStatus();
        });

        // Campo para enviar comandos
        Composite commandPanel = new Composite(parent, SWT.NONE);
        commandPanel.setLayout(new GridLayout(3, false));
        commandPanel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
        //Campo para id del satelite
        Text satelliteIdText = new Text(commandPanel, SWT.BORDER);
        satelliteIdText.setMessage("ID del satélite");
        satelliteIdText.setLayoutData(new GridData(200, SWT.DEFAULT));
        //Campo para comandos en un desplegable
        Combo commandCombo = new Combo(commandPanel, SWT.DROP_DOWN | SWT.READ_ONLY);
        commandCombo.setItems(new String[] { "REBOOT", "SAFE_MODE", "SIMULATE_SIGNAL_LOSS" });
        commandCombo.select(0);
        commandCombo.setLayoutData(new GridData(200, SWT.DEFAULT));
        //Boton enviar comando seleccionado
        Button sendCommandBtn = new Button(commandPanel, SWT.PUSH);
        sendCommandBtn.setText("Enviar Comando");
        sendCommandBtn.addListener(SWT.Selection, e -> {
            String id = satelliteIdText.getText().trim();
            String cmd = commandCombo.getText().trim().toUpperCase();
            controller.sendCommand(id, cmd);
            updateStatus();
        });

        // Texto para mostrar estado de los satelites
        statusText = new Text(parent, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        statusText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
        statusText.setEditable(false);

        // Canvas para visualizacion de satelites
        canvas = new Canvas(parent, SWT.BORDER);
        canvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
        canvas.addPaintListener(e -> drawSatellites(e.gc));

        updateStatus();
    }
    
    //Actualizar texto
    private void updateStatus() {
        String output = controller.getAllSatellites().stream()
            .map(Satellite::toString)
            .collect(Collectors.joining("\n\n"));

        statusText.setText(output);
        canvas.redraw(); 
    }
    
    //Mapa 2d para mostrar los satelites segun estdo y coordenadas
    private void drawSatellites(GC gc) {
        int width = canvas.getSize().x;
        int height = canvas.getSize().y;

        
        gc.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
        gc.fillRectangle(0, 0, width, height);

        for (Satellite sat : controller.getAllSatellites()) {
            // Convertir lat/lon a coordenadas del canvas
            int x = (int) ((sat.getLongitude() + 180) / 360.0 * width);
            int y = (int) ((90 - sat.getLatitude()) / 180.0 * height);

            Color color = switch (sat.getStatus()) {
                case ACTIVE -> Display.getCurrent().getSystemColor(SWT.COLOR_GREEN);
                case SAFE_MODE -> Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW);
                case LOST_SIGNAL -> Display.getCurrent().getSystemColor(SWT.COLOR_RED);
                case LOW_POWER -> Display.getCurrent().getSystemColor(SWT.COLOR_DARK_YELLOW);
                case OFFLINE -> Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);
            };

            gc.setBackground(color);
            gc.fillOval(x - 5, y - 5, 10, 10);
        }
    }

    @Override
    public void setFocus() {
        statusText.setFocus();
    }
}
