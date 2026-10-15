package co.edu.uniquindio;

import java.time.LocalDate;

public class DevPlus {
    private static final int CAPACIDAD = 100;
    private static final int PROYECTOS_CLIENTE_FRECUENTE = 3;
    private static final double DESCUENTO_FRECUENTE = 0.10;

    private String nombreComercial;
    private String nit;
    private String direccion;
    private String telefono;
    private String paginaWeb;

    private Cliente[] clientes;
    private int cantidadClientes;
    private Desarrollador[] desarrolladores;
    private int cantidadDesarrolladores;
    private Proyecto[] proyectos;
    private int cantidadProyectos;
    private ServicioAdicional[] servicios;
    private int cantidadServicios;

    public DevPlus(String nombreComercial, String nit, String direccion,
                   String telefono, String paginaWeb) {
        this.nombreComercial = nombreComercial;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.paginaWeb = paginaWeb;
        this.clientes = new Cliente[CAPACIDAD];
        this.desarrolladores = new Desarrollador[CAPACIDAD];
        this.proyectos = new Proyecto[CAPACIDAD];
        this.servicios = new ServicioAdicional[CAPACIDAD];
    }

   //-------------------------------------------------------------------------------------------
    public String getNombreComercial() { return nombreComercial; }
    public void setNombreComercial(String nombreComercial) { this.nombreComercial = nombreComercial; }
    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getPaginaWeb() { return paginaWeb; }
    public void setPaginaWeb(String paginaWeb) { this.paginaWeb = paginaWeb; }

    //---------------------------------------------------------------------------------------------
    public Cliente buscarCliente(String identificacion) {
        for (int i = 0; i < cantidadClientes; i++) {
            if (clientes[i].getIdentificacion().equals(identificacion)) {
                return clientes[i];
            }
        }
        return null;
    }

    public String registrarCliente(Cliente c) {
        if (cantidadClientes == CAPACIDAD) {
            return "No hay espacio para más clientes.";
        }
        if (buscarCliente(c.getIdentificacion()) != null) {
            return "Ya existe un cliente con esa identificación.";
        }
        clientes[cantidadClientes] = c;
        cantidadClientes++;
        return "Cliente registrado correctamente.";
    }

    public String listarClientes() {
        if (cantidadClientes == 0) return "No hay clientes registrados.";
        String texto = "";
        for (int i = 0; i < cantidadClientes; i++) {
            texto += clientes[i] + "\n";
        }
        return texto;
    }

    // --------------------------------------------------------------------------------------------
    public int contarProyectosCliente(Cliente c) {
        int contador = 0;
        for (int i = 0; i < cantidadProyectos; i++) {
            if (proyectos[i].getCliente() == c
                    && proyectos[i].getEstado() != EstadoProyecto.CANCELADO) {
                contador++;
            }
        }
        return contador;
    }

    public boolean esClienteFrecuente(Cliente c) {
        return contarProyectosCliente(c) >= PROYECTOS_CLIENTE_FRECUENTE;
    }

    // -----------------------------------------------------------------------------------------
    public Desarrollador buscarDesarrollador(int codigo) {
        for (int i = 0; i < cantidadDesarrolladores; i++) {
            if (desarrolladores[i].getCodigo() == codigo) {
                return desarrolladores[i];
            }
        }
        return null;
    }

    public String registrarDesarrollador(Desarrollador d) {
        if (cantidadDesarrolladores == CAPACIDAD) {
            return "No hay espacio para más desarrolladores.";
        }
        if (buscarDesarrollador(d.getCodigo()) != null) {
            return "Ya existe un desarrollador con ese código.";
        }
        desarrolladores[cantidadDesarrolladores] = d;
        cantidadDesarrolladores++;
        return "Desarrollador registrado correctamente.";
    }

    public String listarDesarrolladores() {
        if (cantidadDesarrolladores == 0) return "No hay desarrolladores registrados.";
        String texto = "";
        for (int i = 0; i < cantidadDesarrolladores; i++) {
            texto += desarrolladores[i] + "\n";
        }
        return texto;
    }

    // --------------------------------------------------------------------------------------------
    public boolean desarrolladorDisponible(Desarrollador d, LocalDate ini, LocalDate fin) {
        if (d.getDisponibilidad() == EstadoDesarrollador.EN_CAPACITACION) {
            return false;
        }
        int proyectosCruzados = 0;
        for (int i = 0; i < cantidadProyectos; i++) {
            Proyecto p = proyectos[i];
            if (p.estaActivo() && p.contieneDesarrollador(d) && p.seCruzaCon(ini, fin)) {
                proyectosCruzados++;
            }
        }
        return proyectosCruzados < d.getMaxProyectos();
    }

    // --------------------------------------------------------------------------------------------
    public void actualizarEstadoDesarrollador(Desarrollador d) {
        if (d.getDisponibilidad() == EstadoDesarrollador.EN_CAPACITACION) {
            return;
        }
        int activos = 0;
        for (int i = 0; i < cantidadProyectos; i++) {
            if (proyectos[i].estaActivo() && proyectos[i].contieneDesarrollador(d)) {
                activos++;
            }
        }
        if (activos == 0) {
            d.setDisponibilidad(EstadoDesarrollador.DISPONIBLE);
        } else if (activos < d.getMaxProyectos()) {
            d.setDisponibilidad(EstadoDesarrollador.ASIGNADO);
        } else {
            d.setDisponibilidad(EstadoDesarrollador.OCUPADO);
        }
    }

    // --------------------------------------------------------------------------------------------
    public ServicioAdicional buscarServicio(int codigo) {
        for (int i = 0; i < cantidadServicios; i++) {
            if (servicios[i].getCodigo() == codigo) {
                return servicios[i];
            }
        }
        return null;
    }

    public String registrarServicio(ServicioAdicional s) {
        if (cantidadServicios == CAPACIDAD) {
            return "No hay espacio para más servicios.";
        }
        if (buscarServicio(s.getCodigo()) != null) {
            return "Ya existe un servicio con ese código.";
        }
        servicios[cantidadServicios] = s;
        cantidadServicios++;
        return "Servicio registrado correctamente.";
    }

    public String listarServicios() {
        if (cantidadServicios == 0) return "No hay servicios registrados.";
        String texto = "";
        for (int i = 0; i < cantidadServicios; i++) {
            texto += servicios[i] + "\n";
        }
        return texto;
    }

    // -------------------------------------------------------------------------------------------
    public Proyecto buscarProyecto(int codigo) {
        for (int i = 0; i < cantidadProyectos; i++) {
            if (proyectos[i].getCodigo() == codigo) {
                return proyectos[i];
            }
        }
        return null;
    }

    public String registrarProyecto(Proyecto p, Cliente c) {
        if (cantidadProyectos == CAPACIDAD) {
            return "No hay espacio para más proyectos.";
        }
        if (buscarProyecto(p.getCodigo()) != null) {
            return "Ya existe un proyecto con ese código.";
        }
        if (p.getEntrega().isBefore(p.getFechaIni())) {
            return "La fecha de entrega no puede ser anterior a la de inicio.";
        }
        if (p.getFechaIni().isBefore(p.getFechaSoli())) {
            return "La fecha de inicio no puede ser anterior a la de solicitud.";
        }
        p.setCliente(c);
        proyectos[cantidadProyectos] = p;
        cantidadProyectos++;
        return "Proyecto registrado en estado PENDIENTE.";
    }

    public String listarProyectos() {
        if (cantidadProyectos == 0) return "No hay proyectos registrados.";
        String texto = "";
        for (int i = 0; i < cantidadProyectos; i++) {
            texto += proyectos[i] + "\n";
        }
        return texto;
    }

    // --------------------------------------------------------------------------------------------
    public String agregarDesarrolladorAProyecto(Proyecto p, Desarrollador d) {
        if (p.getEstado() != EstadoProyecto.PENDIENTE) {
            return "Solo se pueden agregar desarrolladores a proyectos PENDIENTES.";
        }
        if (p.contieneDesarrollador(d)) {
            return "Ese desarrollador ya está en el proyecto.";
        }
        if (!desarrolladorDisponible(d, p.getFechaIni(), p.getEntrega())) {
            return "El desarrollador no está disponible en las fechas del proyecto.";
        }
        if (!p.agregarDesarrollador(d)) {
            return "El proyecto ya alcanzó el máximo de desarrolladores.";
        }
        calcularValorProyecto(p);
        return "Desarrollador agregado. Valor actual: $" + p.getValorTotal();
    }

    // --------------------------------------------------------------------------------------------
    public String agregarServicioAProyecto(Proyecto p, ServicioAdicional s) {
        if (p.getEstado() == EstadoProyecto.FINALIZADO || p.getEstado() == EstadoProyecto.CANCELADO) {
            return "No se pueden agregar servicios a un proyecto finalizado o cancelado.";
        }
        if (!p.agregarServicio(s)) {
            return "No se pudo agregar (servicio no disponible o proyecto lleno).";
        }
        calcularValorProyecto(p);
        return "Servicio agregado. Valor actual: $" + p.getValorTotal();
    }

    public double calcularValorProyecto(Proyecto p) {
        boolean frecuente = esClienteFrecuente(p.getCliente());
        return p.calcularValorTotal(frecuente, DESCUENTO_FRECUENTE);
    }

    // -------------------------------------------------------------------------------------------
    public String confirmarProyecto(Proyecto p) {
        if (p.getEstado() != EstadoProyecto.PENDIENTE) {
            return "Solo se puede confirmar un proyecto PENDIENTE.";
        }
        if (p.getCantidadDesarrolladores() == 0) {
            return "El proyecto debe tener al menos un desarrollador.";
        }
        Desarrollador[] devs = p.getDesarrolladores();
        for (int i = 0; i < p.getCantidadDesarrolladores(); i++) {
            if (!desarrolladorDisponible(devs[i], p.getFechaIni(), p.getEntrega())) {
                return "El desarrollador #" + devs[i].getCodigo() + " ya no está disponible.";
            }
        }
        p.setEstado(EstadoProyecto.CONFIRMADO);
        calcularValorProyecto(p);
        actualizarDesarrolladoresDelProyecto(p);
        return "Proyecto confirmado. Valor total: $" + p.getValorTotal();
    }

    // --------------------------------------------------------------------------------------------
    public String cambiarEstadoProyecto(Proyecto p, EstadoProyecto nuevo) {
        EstadoProyecto actual = p.getEstado();

        if (nuevo == EstadoProyecto.CONFIRMADO) {
            return confirmarProyecto(p);
        }
        boolean valido = (actual == EstadoProyecto.CONFIRMADO && nuevo == EstadoProyecto.EN_CURSO)
                || (actual == EstadoProyecto.EN_CURSO && nuevo == EstadoProyecto.FINALIZADO)
                || (nuevo == EstadoProyecto.CANCELADO
                && (actual == EstadoProyecto.PENDIENTE
                || actual == EstadoProyecto.CONFIRMADO
                || actual == EstadoProyecto.EN_CURSO));
        if (!valido) {
            return "Cambio no permitido: de " + actual + " a " + nuevo + ".";
        }
        p.setEstado(nuevo);
        actualizarDesarrolladoresDelProyecto(p);
        return "El proyecto ahora está " + nuevo + ".";
    }

    private void actualizarDesarrolladoresDelProyecto(Proyecto p) {
        Desarrollador[] devs = p.getDesarrolladores();
        for (int i = 0; i < p.getCantidadDesarrolladores(); i++) {
            actualizarEstadoDesarrollador(devs[i]);
        }
    }
    // ------------------------------------------------------------------------------------------
    public static boolean esNumeroPerfecto(long numero) {
        if (numero < 2) {
            return false;
        }
        long suma = 1;
        for (long i = 2; i * i <= numero; i++) {
            if (numero % i == 0) {
                suma += i;
                if (i != numero / i) {
                    suma += numero / i;
                }
            }
        }
        return suma == numero;
    }

    //------------------------------------------------------------------------------------------
    public String consultarClientePorTelefono(String telefonoBuscado) {
        for (int i = 0; i < cantidadClientes; i++) {
            if (clientes[i].getTelefono().equals(telefonoBuscado)) {
                String soloDigitos = telefonoBuscado.replaceAll("[^0-9]", "");
                boolean perfecto = false;
                if (!soloDigitos.isEmpty()) {
                    perfecto = esNumeroPerfecto(Long.parseLong(soloDigitos));
                }
                return clientes[i] + "\n\nEl teléfono " + telefonoBuscado
                        + (perfecto ? " SÍ es un número perfecto." : " NO es un número perfecto.");
            }
        }
        return "No se encontró ningún cliente con ese teléfono.";
    }

    // ------------------------------------------------------------------------------------------
    public double calcularIngresosPorFecha(LocalDate fecha) {
        double total = 0;
        for (int i = 0; i < cantidadProyectos; i++) {
            if (proyectos[i].getFechaSoli().equals(fecha)) {
                total += proyectos[i].getValorTotal();
            }
        }
        return total;
    }
}
