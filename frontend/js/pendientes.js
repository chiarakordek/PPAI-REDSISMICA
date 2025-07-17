// Contenido completo y actualizado para js/pendientes.js

document.addEventListener('DOMContentLoaded', () => {
    const tablaBody = document.getElementById('tabla-pendientes-body'); 
    const API_URL = 'http://localhost:8080/api/revision-manual';

    const cargarEventosPendientes = async () => {
        try {
            const response = await fetch(`${API_URL}/eventos-pendientes`);
            
            if (!response.ok) {
                throw new Error('No se pudieron cargar los eventos pendientes.');
            }
            const eventos = await response.json();

            tablaBody.innerHTML = '';

            if (eventos.length === 0) {
                // Ajustamos el colspan a 5 porque ahora hay 5 columnas
                tablaBody.innerHTML = `<tr><td colspan="5">No hay eventos pendientes.</td></tr>`;
                return;
            }
            
            eventos.forEach(evento => {
                const fila = document.createElement('tr');
                
                // Formateamos los datos del objeto de la entidad
                const fechaFormateada = new Date(evento.fechaHoraOcurrencia).toLocaleString('es-ES', { 
                    day: '2-digit', month: '2-digit', year: 'numeric', 
                    hour: '2-digit', minute: '2-digit', second: '2-digit' 
                });
                
                const ubicacionEpicentro = `${evento.latitudEpicentro}°S, ${evento.longitudEpicentro}°W`;

                // --- AQUÍ ESTÁ LA LÓGICA AÑADIDA ---
                // Verificamos si los datos del hipocentro existen antes de mostrarlos
                let ubicacionHipocentro = 'No disponible';
                if (evento.latitudHipocentro != null && evento.longitudHipocentro != null) {
                    ubicacionHipocentro = `${evento.latitudHipocentro}°S, ${evento.longitudHipocentro}°W`;
                }

                // Construimos la fila con la nueva celda para el hipocentro
                fila.innerHTML = `
                    <td>${fechaFormateada}</td>
                    <td>${ubicacionEpicentro}</td>
                    <td>${ubicacionHipocentro}</td>
                    <td>${evento.magnitud}</td>
                    <td>
                        <a href="detalleEvento.html?id=${evento.id}&origen=pendientes" class="btn-revisar">Revisar</a>
                    </td>
                `;
                tablaBody.appendChild(fila);
            });

        } catch (error) {
            console.error('Error:', error);
            // Ajustamos el colspan a 5
            tablaBody.innerHTML = `<tr><td colspan="5">Error al cargar datos.</td></tr>`;
        }
    };

    cargarEventosPendientes();
});