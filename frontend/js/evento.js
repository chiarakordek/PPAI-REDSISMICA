// --- Contenido completo y corregido para js/evento.js ---

document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    const eventoId = urlParams.get('id');
    const origen = urlParams.get('origen');

    const tituloEvento = document.querySelector('.evento-header h2');
    // NOTA: Asegúrate de que tu HTML tenga estos IDs
    const spanFecha = document.getElementById('info-fecha');
    const spanUbicacion = document.getElementById('info-ubicacion');
    const spanMagnitud = document.getElementById('info-magnitud');
    const spanAlcance = document.getElementById('info-alcance');
    const estadoActualSpan = document.getElementById('estado-actual');
    
    // Asumimos que la API está en el puerto 8080 del backend
    const API_URL = 'http://localhost:8080/api/revision-manual';

    const cargarDetalleEvento = async () => {
        if (!eventoId) {
            tituloEvento.textContent = "Error: No se especificó un ID de evento.";
            return;
        }

        try {
            const response = await fetch(`${API_URL}/detalles-evento/${eventoId}`);

            if (!response.ok) {
                throw new Error(`Error al cargar el evento: ${response.status}`);
            }

            const evento = await response.json();

            // --- CORRECCIÓN PRINCIPAL AQUÍ ---
            // Leemos los datos directamente del DTO que nos envía el backend.
            // Ya no necesitamos 'new Date()' ni formatear nada.

            tituloEvento.textContent = `Evento #${eventoId}`; // Usamos el ID de la URL que es más seguro
            spanFecha.textContent = evento.fechaHora; // ANTES: new Date(evento.fechaHoraOcurrencia)...
            spanUbicacion.textContent = evento.ubicacion; // ANTES: `${evento.latitudEpicentro}°S...`
            spanMagnitud.textContent = evento.magnitud;
            spanAlcance.textContent = evento.alcance; // ANTES: evento.alcanceSismo ? evento.alcanceSismo.nombre : 'No definido'
            estadoActualSpan.textContent = evento.estado; // ANTES: evento.estadoActual ? evento.estadoActual.nombreEstado : 'No definido'
            
            // --- CÓDIGO DE EDICIÓN (opcional, por ahora lo comentamos para evitar errores) ---
            /* 
            const editFecha = document.getElementById('edit-fecha');
            const editUbicacion = document.getElementById('edit-ubicacion');
            const editMagnitud = document.getElementById('edit-magnitud');
            const editAlcance = document.getElementById('edit-alcance');
            
            // Rellenar el formulario de edición (si existe)
            if(editFecha) {
                // Para rellenar el formulario necesitaríamos la fecha en formato ISO.
                // Por ahora, lo dejamos simple.
                // editFecha.value = new Date().toISOString().slice(0, 16); // Valor de ejemplo
                editUbicacion.value = evento.ubicacion;
                editMagnitud.value = evento.magnitud;
                editAlcance.value = evento.alcance;
            }
            */

        } catch (error) {
            console.error('Error al cargar los datos del evento.', error);
            tituloEvento.textContent = "Error al cargar los datos del evento.";
            // Para ver más detalles del error en la propia página:
            spanFecha.textContent = error.message;
        }
    };

    const mostrarBotonesCorrectos = () => {
        // ... (Tu lógica de botones está bien, no la tocamos)
        const botonesPendientes = document.getElementById('botones-pendientes');
        const botonesRegistrados = document.getElementById('botones-registrados');

        if (botonesPendientes && botonesRegistrados) {
            if (origen === 'pendientes') {
                botonesPendientes.style.display = 'flex';
                botonesRegistrados.style.display = 'none';
                const btnRegistrar = document.querySelector('#botones-pendientes .btn-registrar');
                if (btnRegistrar) btnRegistrar.disabled = true;
            } else if (origen === 'registrados') {
                botonesPendientes.style.display = 'none';
                botonesRegistrados.style.display = 'flex';
                const btnGuardar = document.querySelector('#botones-registrados .btn-guardar');
                if (btnGuardar) btnGuardar.disabled = true;
            } else {
                botonesPendientes.style.display = 'none';
                botonesRegistrados.style.display = 'none';
            }
        }
    };

    cargarDetalleEvento();
    mostrarBotonesCorrectos();
});

// --- FUNCIONES GLOBALES (están bien, no las tocamos) ---
// ... tu código de habilitarEdicion, registrarEvento, etc. se queda igual ...
function habilitarEdicion() {
    const form = document.getElementById('formulario-edicion');
    if (form) form.style.display = 'block';

    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.get('origen') === 'pendientes') {
        const btn = document.querySelector('#botones-pendientes .btn-registrar');
        if (btn) btn.disabled = false;
    } else {
        const btn = document.querySelector('#botones-registrados .btn-guardar');
        if (btn) btn.disabled = false;
    }
}
