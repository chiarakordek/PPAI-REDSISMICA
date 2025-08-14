document.addEventListener("DOMContentLoaded", () => {

    //Consigue el nombre de usuario desde el localStorage
    const sesionString = localStorage.getItem('sesion');

    const tablaBody = document.getElementById('tabla-pendientes-body');

    btnRegistrarResult = document.getElementById('btnRegistrarRes');

    const API_URL = 'http://localhost:8080/revision-manual';

    if (sesionString) {
      const sesionData = JSON.parse(sesionString); //Transforma el string en un objeto JSON
      document.getElementById('nombreUsuario').textContent = sesionData.usuario; //Agrega el nombre al mensaje de bienvenida

    } else {
    //Si no hay datos en el localStorage, redirige al usuario al login
      window.location.href = 'login.html';
    }


    const opcionRegistrarNuevaRevision = () => {
        abrir()
    }

    //MÉTODO ABRIR - MSG 2
    const abrir = () => {
        window.location.href = 'eventosPendientes.html';
    }


    //MÉTODO MostrarEventoSismicoParaSeleccion - MSG 16
    const mostrarEventoSismicoparaSeleccion= async () => {
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
        } catch (error) {
            console.error('Error al cargar los eventos pendientes:', error);
            tablaBody.innerHTML = `<tr><td colspan="5" style="text-align: center;">Error al cargar datos.</td></tr>`;
        } finally {
            tablaBody.dataset.loading = 'false';
        }
    };

    if (document.body.id === 'menuPrincipal') {
        btnRegistrarResult.addEventListener('click', opcionRegistrarNuevaRevision);
    }

});

