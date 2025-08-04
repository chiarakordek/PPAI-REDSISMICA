document.addEventListener('DOMContentLoaded', function() {
    const tablaBody = document.getElementById('tabla-registrados-body');

    //La URL coincide con el endpoint del controller
    const API_URL = 'http://localhost:8080/api/revision-manual/eventos-todos'; 

    const cargarEventosRegistrados = async () => {
        tablaBody.innerHTML = '<tr><td colspan="5" style="text-align: center;">Cargando eventos...</td></tr>';

        try {
            const response = await fetch(API_URL);
            if (!response.ok) {
                // Si la respuesta no es OK, ahora el error de CORS se habrá ido,
                // pero podría haber otros errores (ej. 404 si la URL estuviera mal).
                throw new Error(`Error HTTP: ${response.status}`);
            }
            
            const data = await response.json();
            tablaBody.innerHTML = ''; 
            
            if (data.length === 0) {
                tablaBody.innerHTML = '<tr><td colspan="5" style="text-align: center;">No hay eventos registrados.</td></tr>';
            } else {
                data.forEach(evento => {
                    const fila = `
                        <tr>
                            <td>${evento.fechaHora || 'N/A'}</td>
                            <td>${evento.ubicacionEpicentro || 'N/A'}</td>
                            <td>${evento.ubicacionHipocentro || 'N/A'}</td>
                            <td>${evento.magnitud !== null ? evento.magnitud : 'N/A'}</td>
                            <td>
                                <a href="detalleEvento.html?id=${evento.id}&origen=registrados" class="btn-editar">Editar</a>
                            </td>
                        </tr>
                    `;
                    tablaBody.innerHTML += fila;
                });
            }
        } catch (error) {
            console.error('Error al cargar los eventos registrados:', error);
            tablaBody.innerHTML = `<tr><td colspan="5" style="text-align: center;">Error al cargar datos. Verifique la consola.</td></tr>`;
        }
    };

    cargarEventosRegistrados();
});