// Archivo completo, final y con la solución A PRUEBA DE TODO para: frontend/js/evento.js

document.addEventListener('DOMContentLoaded', async () => {
    const API_URL = 'http://localhost:8080/revision-manual';
    const params = new URLSearchParams(window.location.search);
    const eventoId = params.get('id');
    const origen = params.get('origen');
        // ← AGREGA ESTAS LÍNEAS PARA DEBUGGEAR
    console.log('URL completa:', window.location.href);
    console.log('Query string:', window.location.search);
    console.log('Parámetros parseados:', Array.from(params.entries()));
    console.log('eventoId obtenido:', eventoId);
    console.log('origen obtenido:', origen);

    const cambiarEstado = async (id, nuevoEstado, showAlerts = true) => {
        try {
            const response = await fetch(`${API_URL}/eventos/${id}/cambiar-estado`, {
                method: 'PATCH',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ nuevoEstado: nuevoEstado })      
            });
            if (!response.ok) throw new Error(await response.text());
            return true;
        } catch (error) {
            if (showAlerts) alert(`Error al cambiar estado: ${error.message}`);
            return false;
        }
    };
    
    const tomarSeleccionEventoSismico = async (eventoId) => {
        if (!eventoId) {
        document.body.innerHTML = '<h1>Error: No se especificó un ID de evento.</h1>';
        return;
        }
         return await cambiarEstado(eventoId, 'BloqueadoEnRevision');
    }

    const mostrarDatosEventoSelec = async (id) => {
    try {
        const response = await fetch(`${API_URL}/detalles-evento?id=${id}`);
        if (!response.ok) throw new Error(`El servidor respondió con error ${response.status}.`);
        
        const detalles = await response.json();

        console.log('Detalles recibidos:', detalles); 

        document.getElementById('evento-id').textContent = `Evento #${id}`;
        document.getElementById('clasificacion').textContent = `Clasificación: ${detalles.clasificacion}`;
        document.getElementById('alcance').textContent = `Alcance: ${detalles.alcance}`;
        document.getElementById('origen').textContent = `Origen del evento: ${detalles.origen_evento}`;
        document.getElementById('estado-actual').textContent = `Estado: ${detalles.estado}`;
        } catch (error) {
            const card = document.querySelector('.detalle-evento-card');
            if(card) {
                card.innerHTML = `<h2 style="color: #ff8a8a;">Error al Cargar Datos</h2><p>${error.message}</p><a href="pendientes.html">Volver</a>`;
            }
        }
    };
    
    // Definimos la función que se ejecutará al salir de la página.
    const handleBeforeUnload = () => {
        cambiarEstado(eventoId, 'PendienteDeRevision', false);
    };

    //MSG 55: tomarOpcVerMapa() -> El Analista selecciona la opción para ver el mapa.
    const tomarOpcVerMapa = () => {
        if (confirm("¿Desea visualizar el mapa del evento sísmico?")) {
            alert('Funcionalidad para ver el mapa se implementará en un futuro.');
        }
    }

    // MSG 58: tomarOpcModificarDatos() -> El Analista selecciona la opción para modificar datos.
    const tomarOpcModificarDatos = () => {
        if (confirm("¿Desea modificar los datos del evento sísmico?")) {
            alert('Funcionalidad para modificar datos se implementará en un futuro.');
        }
    }

    const setupActionButtons = () => {
        // Seleccionamos todos los botones
        const btnModificar = document.querySelector('.btn-editar');
        const btnVerMapa = document.querySelector('.btn-ver-mapa');
        const btnConfirmar = document.querySelector('.btn-confirmar');
        const btnDerivar = document.querySelector('.btn-derivar');
        const btnRechazar = document.querySelector('.btn-rechazar');
        
        if (btnModificar) {
            btnModificar.addEventListener('click', tomarOpcModificarDatos);
        }
        
        if (btnVerMapa) {
            btnVerMapa.addEventListener('click', tomarOpcVerMapa);
        }

        if (btnConfirmar) {
            btnConfirmar.addEventListener('click', () => {
                if (confirm("¿Desea CONFIRMAR el evento sísmico?")) {
                     alert('Funcionalidad para confirmar el evento se implementará en un futuro.');
                }
            });
        }

        if (btnDerivar) {
            btnDerivar.addEventListener('click', () => {
                if (confirm("¿Desea DERIVAR el evento sísmico?")) {
                     // Texto ajustado
                     alert('Funcionalidad para derivar el evento se implementará en un futuro.');
                }
            });
        }

        if (btnRechazar) {
            btnRechazar.addEventListener('click', tomarSeleccion);
        }
    };

    // Lógica para el botón RECHAZAR 
        // MSG61: tomarSeleccion() -> El Analista selecciona el botón RECHAZAR.
        async function tomarSeleccion() {
            if (confirm("¿Está seguro de que desea RECHAZAR este evento?")) {
                window.removeEventListener('beforeunload', handleBeforeUnload);

                try {
                    const exito = await cambiarEstado(eventoId, "Rechazado");
                    if (exito) {
                        //Cambia de página para evitar que al recargar la página se cambie el estado a BloqueadoEnRevision
                        const nuevaUrl = `detalleEvento.html?id=${eventoId}&origen=registrados`;
                        history.replaceState({ path: nuevaUrl }, '', nuevaUrl);
                        
                        const accionesContainer = document.getElementById('acciones-container');
                        if (accionesContainer) {
                            accionesContainer.innerHTML = `
                                <div class="accion-completada">
                                    <p>El evento ha sido marcado como "Rechazado".</p>
                                    <a href="eventosPendientes.html" class="btn-accion btn-volver">Volver a la lista</a>
                                </div>
                            `;
                        }
                        const estadoActualEl = document.getElementById('estado-actual');
                        if(estadoActualEl) estadoActualEl.textContent = `Estado: Rechazado`;
                    } else {
                        throw new Error("El servidor no pudo procesar la solicitud.");
                    }
                } catch(error) {
                    alert(`Ocurrió un error al guardar el cambio: ${error.message}. Por favor, recargue la página e intente de nuevo.`);
                    window.addEventListener('beforeunload', handleBeforeUnload);
                }
            }
        };

    // MSG 60: mostrarOpcionesParaSeleccion() -> La Pantalla devuelve la lista al front-end.
    const mostrarOpcionesParaSeleccion = () => {
        const botonesDecision = document.getElementById('botones-decision');
        if (botonesDecision) {
            botonesDecision.style.display = 'flex';
        }
    };

    if (origen === 'pendientes') {
        window.addEventListener('beforeunload', handleBeforeUnload);
        const bloqueoExitoso = tomarSeleccionEventoSismico(eventoId);
        if (bloqueoExitoso) {
            await mostrarDatosEventoSelec(eventoId);
            mostrarOpcionesParaSeleccion();
            setupActionButtons();
        }
    } else {
        await mostrarDatosEventoSelec(eventoId);
    }
});