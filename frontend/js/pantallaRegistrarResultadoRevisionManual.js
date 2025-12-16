document.addEventListener("DOMContentLoaded", () => {

    // Consigue el nombre de usuario desde el localStorage
    const sesionString = localStorage.getItem('sesion');
    const nombreUsuario = document.getElementById('nombreUsuario');

    if (sesionString) {
        const sesionData = JSON.parse(sesionString);
        if (nombreUsuario) {
            nombreUsuario.textContent = sesionData.usuario;
        }
    } else {
        // Si no hay sesión, redirige al login
        window.location.href = 'login.html';
        return; // Evita que el resto del script se ejecute
    }
    
    const tablaBody = document.getElementById('tabla-pendientes-body');

    //MÉTODO ABRIR 
    async function abrir(){
        
        const API_URL = 'http://localhost:8081/revision-manual'; //End-point del controlador
        tablaBody.innerHTML = '<tr><td colspan="5" style="text-align: center;">Cargando eventos...</td></tr>';

        try {   

            console.log("Cargando eventos pendientes...");
            
            const response = await fetch(`${API_URL}/iniciar`);

            console.log("Respuesta del servidor:", response);
            

            if (!response.ok) throw new Error(`Error HTTP: ${response.status}`);    

            const data = await response.json();

            tablaBody.innerHTML = ''; 

            mostrarEventoSismicoparaSeleccion(data);

        } catch (error) {
            console.error('Error al cargar los eventos pendientes:', error);
            tablaBody.innerHTML = `<tr><td colspan="5" style="text-align: center;">Error al cargar datos.</td></tr>`;
        } finally {
            tablaBody.dataset.loading = 'false';
        }
    }


    //MÉTODO MostrarEventoSismicoParaSeleccion - 
    function mostrarEventoSismicoparaSeleccion(data) {
        if (tablaBody.dataset.loading === 'true') return;
        tablaBody.dataset.loading = 'true';

        if (data.length === 0) {

            tablaBody.innerHTML = '<tr><td colspan="5" style="text-align: center;">No hay eventos pendientes de revisión.</td></tr>';

        } else {
            data.forEach(evento => {
                const fila = `
                    <tr>
                        <td>${evento.fechaHoraOcurrencia || 'N/A'}</td>

                        <td>${evento.latitudEpicentro && evento.longitudEpicentro ? 
                            `${evento.latitudEpicentro}, ${evento.longitudEpicentro}` : 'N/A'}</td>

                        <td>${evento.latitudHipocentro && evento.longitudEpicentro ?
                                `${evento.latitudHipocentro}, ${evento.longitudHipocentro}` : 'N/A'}</td>

                        <td>${evento.valorMagnitud !== null ? evento.valorMagnitud : 'N/A'}</td>

                        <td>
                            <a href="detalleEvento.html?id=${evento.id}&origen=pendientes" class="btn-revisar">Revisar</a>
                        </td>
                    </tr>
                `;
                tablaBody.innerHTML += fila;
            });
        }
    }


    abrir();
});
