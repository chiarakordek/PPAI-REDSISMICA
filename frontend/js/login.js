document.getElementById('login-form').addEventListener('submit', async function(e) {
    e.preventDefault();
    
    const usuario = document.getElementById('usuario').value.trim();
    const btn = document.querySelector('.btn');
    
    try {
        btn.disabled = true;
        btn.textContent = 'Ingresando...';

        const response = await fetch('http://localhost:8080/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ nombreUsuario: usuario })
        });

        const data = await response.json();
        
        if (!response.ok) {
            throw new Error(data.message || 'Error en la autenticación');
        }

        localStorage.setItem('sesion', JSON.stringify(data));
        window.location.href = 'menu.html';
        
    } catch (error) {
        console.error('Error completo:', error);
        alert(`Error: ${error.message}\n\n¿El backend está corriendo en http://localhost:8080?`);
    } finally {
        btn.disabled = false;
        btn.textContent = 'Entrar';
    }
});