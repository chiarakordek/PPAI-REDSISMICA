// Archivo completo para: frontend/js/registrados.js

document.addEventListener('DOMContentLoaded', () => {
    const tablaBody = document.getElementById('tabla-registrados-body');
    const API_URL = 'http://localhost:8080/api/revision-manual';

    const cargarEventosRegistrados = async () => {
        tablaBody.innerHTML = '<tr><td colspan="5">Cargando eventos...</td></tr>';

        try {
            const response = await fetch(`${API_URL}/eventos-registrados`);
            if (!response.ok) throw new Error(`Error del servidor: ${response.status}`);
            
            const eventos = await response.json();
            tablaBody.innerHTML = ''; 

            if (eventos.length === 0) {
                tablaBody.innerHTML = `<tr><td colspan="5">No hay eventos registrados.</td></tr>`;
                return;
            }
            
            eventos.forEach(evento => {
                const fila = document.createElement('tr');
                // --- FILA ACTUALIZADA CON 5 CELDAS ---
                fila.innerHTML = `
                    <td>${evento.fechaHora}</td>
                    <td>${evento.ubicacion}</td>
                    <td>${evento.ubicacionHipocentro}</td>
                    <td>${evento.magnitud}</td>
                    <td>
                        <a href="detalleEvento.html?id=${evento.id}&origen=registrados" class="btn-editar">Editar</a>
                    </td>
                `;
                tablaBody.appendChild(fila);
});

        } catch (error) {
            console.error('Error al cargar eventos registrados:', error);
            tablaBody.innerHTML = `<tr><td colspan="5">Error al cargar los datos. Verifique la consola.</td></tr>`;
        }
    };

    if (tablaBody) {
        cargarEventosRegistrados();
    }
});