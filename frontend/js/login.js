document.getElementById('login-form').addEventListener('submit', async function(e) {
    e.preventDefault();
    
    //Nombre de usuario ingresado por el empleado
    const usuario = document.getElementById('usuario').value.trim();
    //Botón de login
    const btn = document.querySelector('.btn');
    
    try {

        //Deshabilita el botón y pone mensaje de carga hasta obtener un resultado
        btn.disabled = true;
        btn.textContent = 'Ingresando...';

        //Autenticación del usuario --> Delegado al back (AuthController)
        const response = await fetch('http://localhost:8081/api/auth/login', {
            //Se comunica con la API y hace un POST con los datos del form
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ nombreUsuario: usuario })
        });

        //Obtiene la respuesta de la API (Mensaje, usuario, id de sesión y fecha de inicio de la sesión)
        const data = await response.json();
        
        if (!response.ok) { 
            throw new Error(data.message || 'Error en la autenticación');
        }

        //Guarda la sesión activa y los datos del usuario
        localStorage.setItem('sesion', JSON.stringify(data));

        //Redirige al usuario al menú
        window.location.href = 'menu.html';
        
    } catch (error) { //Manejo de errores
        console.error('Error completo:', error);
        alert(`Error: ${error.message}\n\n¿El backend está corriendo en http://localhost:8081?`);

    } finally {
        btn.disabled = false;
        btn.textContent = 'Entrar';
    }
});