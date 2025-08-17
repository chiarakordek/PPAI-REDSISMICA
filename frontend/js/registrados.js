document.addEventListener('DOMContentLoaded', function() {
    const tablaBody = document.getElementById('tabla-registrados-body');

    
    const API_URL = 'http://localhost:8080/revision-manual/eventos-sismicos'; //End-point del controlador

    const cargarEventosRegistrados = async () => {
        tablaBody.innerHTML = '<tr><td colspan="5" style="text-align: center;">Cargando eventos...</td></tr>';
        
        console.log("Cargando eventos registrados...");
        

        try {
            const response = await fetch(API_URL);
            if (!response.ok) {
                // Si la respuesta no es OK, ahora el error de CORS se habrá ido,
                // pero podría haber otros errores (ej. 404 si la URL estuviera mal).
                throw new Error(`Error HTTP: ${response.status}`);
            }
            
            const data = await response.json(); 
            tablaBody.innerHTML = ''; 
            
            //Valida si no hay eventos registrados aún
            if (data.length === 0) {
                tablaBody.innerHTML = '<tr><td colspan="5" style="text-align: center;">No hay eventos registrados.</td></tr>';

            } else {
                //Si hay eventos registrados, completa las filas de la tabla con los datos de cada uno
                data.forEach(evento => {  
                    const fila = `
                        <tr>
                            <td>${evento.fechaHoraOcurrencia || 'N/A'}</td>

                            <td>${evento.latitudEpicentro && evento.latitudEpicentro ? 
                                `${evento.latitudEpicentro}, ${evento.latitudEpicentro}` : 'N/A'}</td>

                            <td>${evento.latitudHipocentro && evento.longitudHipocentro ? 
                                `${evento.latitudHipocentro}, ${evento.longitudHipocentro}`:'N/A'}</td>

                            <td>${evento.valorMagnitud !== null ? evento.valorMagnitud : 'N/A'}</td>
                            
                            <td>
                                <a href="detalleEvento.html?id=${evento.id}&origen=registrados" class="btn-editar">Editar</a>
                            </td>
                        </tr>
                    `; //La columna de editar deriva a la página del detalle del elemento de la fila
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