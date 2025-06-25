# 🛰️ Simulador de Control de Constelación de Satélites

Este proyecto es una aplicación de escritorio desarrollada en **Java** utilizando **Eclipse RCP (Rich Client Platform)**, que simula el control básico de una constelación de satélites. Permite visualizar sus posiciones, estados, niveles de batería y enviar comandos remotos en una interfaz visual e interactiva.

---

## 🎯 Objetivo del Proyecto

Simular un centro de control espacial básico con una interfaz intuitiva que permita:

- Visualizar satélites con coordenadas aleatorias y estado dinámico.
- Enviar comandos a cada satélite individualmente.
- Observar el comportamiento del sistema ante fallos simulados (pérdida de señal, batería baja).
- Usar buenas prácticas de ingeniería de software y diseño modular.

---

## 🧠 Funcionalidades implementadas

- 🌍 Inicialización de satélites con coordenadas aleatorias (latitud/longitud).
- ⚡ Simulación de drenaje de batería por tick.
- 📡 Posibilidad de pérdida de señal aleatoria.
- 📤 Envío de comandos a través de un menú desplegable:
  - `REBOOT` → reiniciar satélite.
  - `SAFE_MODE` → entrar en modo seguro.
  - `SIMULATE_SIGNAL_LOSS` → simular pérdida de señal.
- 🧭 Visualización en un **canvas interactivo** de los satélites:
  - Colores por estado (`ACTIVE`, `SAFE_MODE`, `LOST_SIGNAL`, `LOW_POWER`, `OFFLINE`).
- 📋 Área de texto con la lista de satélites y su información en tiempo real.

---

## 🛠️ Tecnologías utilizadas

| Componente         | Tecnología                          |
|--------------------|--------------------------------------|
| Lenguaje           | Java 17                              |
| Interfaz gráfica   | Eclipse RCP (SWT + JFace)            |
| Lógica del sistema | Clases Java modulares (MVC-like)     |
| IDE utilizado      | Eclipse IDE for RCP Developers       |


