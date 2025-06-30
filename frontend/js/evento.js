// Mostrar botones según origen
document.addEventListener('DOMContentLoaded', function() {
  const urlParams = new URLSearchParams(window.location.search);
  const origen = urlParams.get('origen');
  
  if (origen === 'pendientes') {
    document.getElementById('botones-pendientes').style.display = 'flex';
    document.getElementById('botones-registrados').style.display = 'none';
    document.querySelector('.btn-registrar').disabled = true; // Inicialmente deshabilitado
  } else if (origen === 'registrados') {
    document.getElementById('botones-pendientes').style.display = 'none';
    document.getElementById('botones-registrados').style.display = 'flex';
  }
});

// Habilitar edición (común para ambos flujos)
function habilitarEdicion() {
  const form = document.getElementById('formulario-edicion');
  form.style.display = 'block';
  
  // Habilitar el botón correspondiente según el origen
  const urlParams = new URLSearchParams(window.location.search);
  if (urlParams.get('origen') === 'pendientes') {
    document.querySelector('.btn-registrar').disabled = false;
  } else {
    document.querySelector('.btn-guardar').disabled = false;
  }
}

// Función específica para registro desde Pendientes
function registrarEvento() {
  if (confirm('¿Registrar este evento con los cambios realizados?')) {
    guardarCambios();
    cambiarEstado('Registrado');
    // Redirigir o actualizar interfaz
    alert('Evento registrado exitosamente');
  }
}

// Función para guardar cambios (común)
function guardarCambios() {
  // Obtener todos los valores editados
  const datos = {
    fecha: document.getElementById('edit-fecha').value,
    ubicacion: document.getElementById('edit-ubicacion').value,
    magnitud: document.getElementById('edit-magnitud').value,
    alcance: document.getElementById('edit-alcance').value,
    comentarios: document.getElementById('edit-comentarios').value
  };
  
  // Aquí iría la lógica AJAX para guardar en backend
  console.log('Datos guardados:', datos);
  
  // Cerrar formulario
  document.getElementById('formulario-edicion').style.display = 'none';
}

// Función para cambiar estado
function cambiarEstado(estado) {
  document.getElementById('estado-actual').textContent = estado;
  // Lógica adicional para cambio de estado...
}