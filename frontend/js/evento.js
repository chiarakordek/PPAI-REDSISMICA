document.addEventListener('DOMContentLoaded', async () => {
    const API_URL = 'http://localhost:8081/revision-manual';
    const params = new URLSearchParams(window.location.search);
    const eventoId = params.get('id');
    const origen = params.get('origen');
        // ← AGREGA ESTAS LÍNEAS PARA DEBUGGEAR
    console.log('URL completa:', window.location.href);
    console.log('Query string:', window.location.search);
    console.log('Parámetros parseados:', Array.from(params.entries()));
    console.log('eventoId obtenido:', eventoId);
    console.log('origen obtenido:', origen);

    if (!eventoId) {
        document.body.innerHTML = '<h1>Error: No se especificó un ID de evento.</h1>';
        return;
    }

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

            const mostrarDatosEventoSelec = async (id) => {
            try {
                const response = await fetch(`${API_URL}/detalles-evento?id=${id}`);
                if (!response.ok) throw new Error(`El servidor respondió con error ${response.status}.`);
                
                const detalles = await response.json();
                console.log('Detalles recibidos:', detalles); // ← AGREGA ESTA LÍNEA
        
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

    const setupActionButtons = () => {
        // Seleccionamos todos los botones
        const btnModificar = document.querySelector('.btn-editar');
        const btnVerMapa = document.querySelector('.btn-ver-mapa');
        const btnConfirmar = document.querySelector('.btn-confirmar');
        const btnDerivar = document.querySelector('.btn-derivar');
        const btnRechazar = document.querySelector('.btn-rechazar');
        
        // =========== INICIO DE LA MODIFICACIÓN DE TEXTOS ===========
        
        if (btnModificar) {
            btnModificar.addEventListener('click', () => {
                if (confirm("¿Desea modificar los datos del evento sísmico?")) {
                    // Texto ajustado
                    alert('Funcionalidad para modificar datos se implementará en un futuro.');
                }
            });
        }
        
        if (btnVerMapa) {
            btnVerMapa.addEventListener('click', () => {
                if (confirm("¿Desea visualizar el mapa del evento sísmico?")) {
                    // Texto ajustado
                    alert('Funcionalidad para ver el mapa se implementará en un futuro.');
                }
            });
        }

        if (btnConfirmar) {
            btnConfirmar.addEventListener('click', async () => {
                if (confirm("¿Está seguro de que desea CONFIRMAR este evento?")) {
                    window.removeEventListener('beforeunload', handleBeforeUnload);

                    try {
                        const exito = await cambiarEstado(eventoId, "ConfirmadoPorPersonal");
                        if (exito) {
                            const nuevaUrl = `detalleEvento.html?id=${eventoId}&origen=registrados`;
                            history.replaceState({ path: nuevaUrl }, '', nuevaUrl);
                            
                            const accionesContainer = document.getElementById('acciones-container');
                            if (accionesContainer) {
                                accionesContainer.innerHTML = `
                                    <div class="accion-completada">
                                        <p>El evento ha sido marcado como "Confirmado por personal".</p>
                                        <a href="eventosPendientes.html" class="btn-accion btn-volver">Volver a la lista</a>
                                    </div>
                                `;
                            }
                            const estadoActualEl = document.getElementById('estado-actual');
                            if(estadoActualEl) estadoActualEl.textContent = `Estado: ConfirmadoPorPersonal`;
                        } else {
                            throw new Error("El servidor no pudo procesar la solicitud.");
                        }
                    } catch(error) {
                        alert(`Ocurrió un error al guardar el cambio: ${error.message}. Por favor, recargue la página e intente de nuevo.`);
                        window.addEventListener('beforeunload', handleBeforeUnload);
                    }
                }
            });
        }

        if (btnDerivar) {
            btnDerivar.addEventListener('click', async () => {
                if (confirm("¿Está seguro de que desea DERIVAR este evento a experto?")) {
                    window.removeEventListener('beforeunload', handleBeforeUnload);

                    try {
                        const exito = await cambiarEstado(eventoId, "DerivadoAExperto");
                        if (exito) {
                            const nuevaUrl = `detalleEvento.html?id=${eventoId}&origen=registrados`;
                            history.replaceState({ path: nuevaUrl }, '', nuevaUrl);
                            
                            const accionesContainer = document.getElementById('acciones-container');
                            if (accionesContainer) {
                                accionesContainer.innerHTML = `
                                    <div class="accion-completada">
                                        <p>El evento ha sido marcado como "Derivado a Experto".</p>
                                        <a href="eventosPendientes.html" class="btn-accion btn-volver">Volver a la lista</a>
                                    </div>
                                `;
                            }
                            const estadoActualEl = document.getElementById('estado-actual');
                            if(estadoActualEl) estadoActualEl.textContent = `Estado: DerivadoAExperto`;
                        } else {
                            throw new Error("El servidor no pudo procesar la solicitud.");
                        }
                    } catch(error) {
                        alert(`Ocurrió un error al guardar el cambio: ${error.message}. Por favor, recargue la página e intente de nuevo.`);
                        window.addEventListener('beforeunload', handleBeforeUnload);
                    }
                }
            });
        }
        // =========== FIN DE LA MODIFICACIÓN DE TEXTOS ===========

        // Lógica para el botón RECHAZAR 
        const tomarSeleccion = async () => {
            if (confirm("¿Está seguro de que desea RECHAZAR este evento?")) {
                window.removeEventListener('beforeunload', handleBeforeUnload);

                try {
                    const exito = await cambiarEstado(eventoId, "Rechazado");
                    if (exito) {
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

        if (btnRechazar) {
            btnRechazar.addEventListener('click', tomarSeleccion);
        }
    };

    if (origen === 'pendientes') {
        window.addEventListener('beforeunload', handleBeforeUnload);
        const bloqueoExitoso = await cambiarEstado(eventoId, 'BloqueadoEnRevision');
        if (bloqueoExitoso) {
            await mostrarDatosEventoSelec(eventoId);
            const botonesDecision = document.getElementById('botones-decision');
            if (botonesDecision) {
                botonesDecision.style.display = 'flex';
            }
            setupActionButtons();
        }
    } else {
        await mostrarDatosEventoSelec (eventoId);
    }
});