document.addEventListener('DOMContentLoaded', function() {
    const tablaBody = document.getElementById('tabla-pendientes-body');
    const API_URL = 'http://localhost:8080/revision-manual';

    const cargarEventosPendientes = async () => {
        if (tablaBody.dataset.loading === 'true') return;
        tablaBody.dataset.loading = 'true';

        tablaBody.innerHTML = '<tr><td colspan="5" style="text-align: center;">Cargando eventos...</td></tr>';

        try {
            const response = await fetch(`${API_URL}/iniciar`);
            if (!response.ok) throw new Error(`Error HTTP: ${response.status}`);
            
            const data = await response.json();
            tablaBody.innerHTML = ''; 
            
            if (data.length === 0) {
                tablaBody.innerHTML = '<tr><td colspan="5" style="text-align: center;">No hay eventos pendientes de revisión.</td></tr>';
            } else {
                console.log('Entrando al forEach...'); 
                data.forEach(evento => {
                    console.log('ID del evento:', evento.id);           
                    console.log('¿ID es null?:', evento.id === null);   
                    console.log('¿ID es undefined?:', evento.id === undefined); 
    
                const fila = `
                    <tr>
                        <td>${evento.fechaHoraOcurrencia || 'N/A'}</td>
                        <td>${evento.latitudEpicentro && evento.longitudEpicentro ? 
                            `${evento.latitudEpicentro}, ${evento.longitudEpicentro}` : 'N/A'}</td>
                        <td>${evento.latitudHipocentro && evento.longitudHipocentro ? 
                            `${evento.latitudHipocentro}, ${evento.longitudHipocentro}` : 'N/A'}</td>
                        <td>${evento.valorMagnitud !== null ? evento.valorMagnitud : 'N/A'}</td>
                        <td>
                        <a href="detalleEvento.html?id=${evento.id}&origen=pendientes" class="btn-revisar">Revisar</a>                        </td>
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