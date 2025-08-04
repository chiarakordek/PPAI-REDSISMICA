// Archivo completo, final y verificado para: frontend/js/pendientes.js

document.addEventListener('DOMContentLoaded', function() {
    const tablaBody = document.getElementById('tabla-pendientes-body');
    const API_URL = 'http://localhost:8080/api/revision-manual';

    const cargarEventosPendientes = async () => {
        if (tablaBody.dataset.loading === 'true') return;
        tablaBody.dataset.loading = 'true';

        tablaBody.innerHTML = '<tr><td colspan="5" style="text-align: center;">Cargando eventos...</td></tr>';

        try {
            const response = await fetch(`${API_URL}/eventos-pendientes`);
            if (!response.ok) throw new Error(`Error HTTP: ${response.status}`);
            
            const data = await response.json();
            tablaBody.innerHTML = ''; 
            
            if (data.length === 0) {
                tablaBody.innerHTML = '<tr><td colspan="5" style="text-align: center;">No hay eventos pendientes de revisión.</td></tr>';
            } else {
                data.forEach(evento => {
                    const fila = `
                        <tr>
                            <td>${evento.fechaHora || 'N/A'}</td>
                            <td>${evento.ubicacionEpicentro || 'N/A'}</td>
                            <td>${evento.ubicacionHipocentro || 'N/A'}</td>
                            <td>${evento.magnitud !== null ? evento.magnitud : 'N/A'}</td>
                            <td>
                                <!-- ¡ESTA ES LA LÍNEA MÁS IMPORTANTE! NOS ASEGURAMOS DE PASAR EL ORIGEN -->
                                <a href="detalleEvento.html?id=${evento.id}&origen=pendientes" class="btn-revisar">Revisar</a>
                            </td>
                        </tr>
                    `;
                    tablaBody.innerHTML += fila;
                });
            }
        } catch (error) {
            console.error('Error al cargar los eventos pendientes:', error);
            tablaBody.innerHTML = `<tr><td colspan="5" style="text-align: center;">Error al cargar datos.</td></tr>`;
        } finally {
            tablaBody.dataset.loading = 'false';
        }
    };

    cargarEventosPendientes();

    document.addEventListener('visibilitychange', () => {
        if (document.visibilityState === 'visible') {
            cargarEventosPendientes();
        }
    });
});